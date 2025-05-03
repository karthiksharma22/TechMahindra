package com.ecommerce.dto;

import lombok.Data;

@Data
public class PaymentDTO {
    private double amount;
    private Long orderId;
}
