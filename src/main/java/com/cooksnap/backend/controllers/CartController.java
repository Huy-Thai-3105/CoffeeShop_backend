package com.cooksnap.backend.controllers;

import com.cooksnap.backend.domains.dto.requests.CartItemsRequest;
import com.cooksnap.backend.domains.dto.requests.EmailDetails;
import com.cooksnap.backend.domains.entity.CartItems;
import com.cooksnap.backend.repositories.CartsRepository;
import com.cooksnap.backend.services.servicesInterface.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add-item-to-cart")
    public ResponseEntity<?> addItem(Principal connectedUser, @RequestBody CartItemsRequest request){
        try {
            return cartService.addItemToCart(connectedUser,request);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Send email fail");
        }
    }

    @GetMapping("/get-items")
    public ResponseEntity<?> getAllItem(Principal connectedUser){
        try {
            return cartService.getItems(connectedUser);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Send email fail");
        }
    }

}
