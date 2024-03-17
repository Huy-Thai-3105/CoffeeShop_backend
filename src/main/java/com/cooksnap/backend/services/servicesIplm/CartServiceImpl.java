package com.cooksnap.backend.services.servicesIplm;

import com.cooksnap.backend.domains.dto.ErrorResponseDto;
import com.cooksnap.backend.domains.dto.SuccessResponse;
import com.cooksnap.backend.domains.dto.requests.CartItemsRequest;
import com.cooksnap.backend.domains.dto.responses.CartItemsResponse;
import com.cooksnap.backend.domains.entity.CartItems;
import com.cooksnap.backend.domains.entity.Carts;
import com.cooksnap.backend.domains.entity.User;
import com.cooksnap.backend.repositories.CartItemsRepository;
import com.cooksnap.backend.repositories.CartsRepository;
import com.cooksnap.backend.services.servicesInterface.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartsRepository cartsRepository;
    private final CartItemsRepository cartItemsRepository;
    @Override
    public ResponseEntity<?> addItemToCart(Principal connectedUser, CartItemsRequest request) {
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            Carts cart = cartsRepository.findCartsByUserId(user.getUserId());

            CartItems newItem = CartItems.builder()
                            .productId(request.getProductId())
                                    .amount(request.getAmount())
                                            .cartId(cart.getId())
                                                    .build();
            cartItemsRepository.save(newItem);
            return ResponseEntity.ok().body(new SuccessResponse("Add item success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.toString()));
        }
    }

    @Override
    public ResponseEntity<?> getItems(Principal connectedUser) {
        try {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            CartItemsResponse cartItemsResponse = cartItemsRepository.findCartItem(user.getUserId());
            return ResponseEntity.ok().body(cartItemsResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.toString()));
        }
    }


}
