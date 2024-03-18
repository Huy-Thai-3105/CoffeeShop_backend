package com.cooksnap.backend.repositories;

import com.cooksnap.backend.domains.dto.responses.CartItemsResponse;
import com.cooksnap.backend.domains.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
    @Query(value = """
        SELECT new com.cooksnap.backend.domains.dto.responses.CartItemsResponse(
            cartItem.productId,
            p.productName,
            p.price,
            p.thumbnail,
            p.quantity,
            p.productImage
        )
        FROM
        Product p
        INNER JOIN
        CartItems cartItem
        ON p.id = cartItem.productId
        INNER JOIN Carts cart
        ON cart.id = cartItem.cartId
        WHERE cart.userId = :userId
    """)
    CartItemsResponse findCartItem(Integer userId);

    CartItems deleteCartItemsByProductId(int productId);
}
