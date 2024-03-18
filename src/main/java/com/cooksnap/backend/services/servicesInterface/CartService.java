package com.cooksnap.backend.services.servicesInterface;

import com.cooksnap.backend.domains.dto.requests.CartItemsRequest;
import com.cooksnap.backend.domains.entity.CartItems;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface CartService {
    ResponseEntity<?> addItemToCart(Principal connectedUser, CartItemsRequest request);

    ResponseEntity<?> getItems(Principal connectedUser);

    ResponseEntity<?> deleteItem(Principal connectedUser, int itemId);
}
