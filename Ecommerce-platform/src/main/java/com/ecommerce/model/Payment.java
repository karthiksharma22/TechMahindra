package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private LocalDateTime paymentDate;

    @OneToOne
    private Order order;

    private String transactionId;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        SUCCESS,
        FAILED,
        PENDING
    }
}
