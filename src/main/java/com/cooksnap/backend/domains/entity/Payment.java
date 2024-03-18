package com.cooksnap.backend.domains.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "credit_id")
    private Integer creditId;

    @Column(name = "is_done")
    private Boolean isDone;

    @Column(name = "pay_time")
    private Date payTime;
}
