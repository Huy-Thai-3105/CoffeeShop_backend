package com.cooksnap.backend.repositories;

import com.cooksnap.backend.domains.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartsRepository extends JpaRepository<Carts,Integer> {
    Carts findCartsByUserId(int userId);
}
