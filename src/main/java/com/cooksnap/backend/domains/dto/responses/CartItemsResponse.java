package com.cooksnap.backend.domains.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemsResponse {
    private int productId ;
    private String productName;
    private int price;
    private String thumbnail;
    private int quantity;
    private String productImage;
}
