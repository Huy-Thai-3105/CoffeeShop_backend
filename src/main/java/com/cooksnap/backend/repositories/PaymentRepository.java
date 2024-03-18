package com.cooksnap.backend.repositories;

import com.cooksnap.backend.domains.entity.Payment;
import com.miragesql.miragesql.annotation.In;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
