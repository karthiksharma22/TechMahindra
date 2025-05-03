package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")  // 'order' is also a reserved word in SQL
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Product> products;

    private double totalAmount;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PLACED,
        PAID,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }
}
