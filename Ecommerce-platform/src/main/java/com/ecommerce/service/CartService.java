package com.ecommerce.service;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public Cart addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findById(1L).orElse(new Cart());
        if (cart.getProducts() == null) {
            cart.setProducts(new ArrayList<>());
        }
        cart.getProducts().add(product);
        cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));
        return cartRepository.save(cart);
    }

    public Cart getCart() {
        return cartRepository.findById(1L).orElse(new Cart());
    }

    public void removeFromCart(Long productId) {
        Cart cart = getCart();
        cart.getProducts().removeIf(p -> p.getId().equals(productId));
        cartRepository.save(cart);
    }

    public void clearCart() {
        Cart cart = getCart();
        cart.getProducts().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }
}
