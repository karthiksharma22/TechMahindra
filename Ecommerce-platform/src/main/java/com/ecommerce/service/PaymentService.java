package com.ecommerce.service;

import com.ecommerce.model.Order;
import com.ecommerce.model.Payment;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public Payment makePayment(double amount) {
        Order order = orderRepository.findAll().stream()
                .filter(o -> o.getStatus() == Order.Status.PLACED)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No order found for payment"));

        Payment payment = Payment.builder()
                .order(order)
                .amount(amount)
                .paymentDate(LocalDateTime.now())
                .transactionId(UUID.randomUUID().toString())
                .status(Payment.Status.SUCCESS)
                .build();

        order.setStatus(Order.Status.PAID);
        orderRepository.save(order);

        return paymentRepository.save(payment);
    }
}
