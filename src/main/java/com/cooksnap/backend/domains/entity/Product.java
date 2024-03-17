package com.cooksnap.backend.domains.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "detail")
    private String detail;

    @Column(name = "price")
    private Integer price;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "promotion_id")
    private Integer promotion_id;

    @Column(name = "product_image")
    private String productImage;
}
