package com.ecommerce.controller;

import com.ecommerce.model.Payment;
import com.ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> makePayment(@RequestParam double amount) {
        return ResponseEntity.ok(paymentService.makePayment(amount));
    }
}
