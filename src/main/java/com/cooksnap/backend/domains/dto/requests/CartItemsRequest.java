package com.cooksnap.backend.domains.dto.requests;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemsRequest {
    private Integer productId;
    private Integer amount;
}
