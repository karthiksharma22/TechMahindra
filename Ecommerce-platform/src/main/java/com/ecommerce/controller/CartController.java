package com.ecommerce.controller;

import com.ecommerce.model.Cart;
import com.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addToCart(productId, quantity));
    }

    @GetMapping
    public ResponseEntity<Cart> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return ResponseEntity.ok("Product removed from cart");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("Cart cleared successfully");
    }
}
