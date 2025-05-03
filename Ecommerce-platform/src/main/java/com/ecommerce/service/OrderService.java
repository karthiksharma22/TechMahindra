package com.ecommerce.service;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public Order placeOrder() {
        Cart cart = cartRepository.findById(1L).orElseThrow(() -> new RuntimeException("Cart not found"));

        Order order = Order.builder()
                .products(cart.getProducts())
                .totalAmount(cart.getTotalPrice())
                .orderDate(LocalDateTime.now())
                .status(Order.Status.PLACED)
                .build();

        cart.getProducts().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);

        return orderRepository.save(order);
    }

    public List<Order> getOrderHistory() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
