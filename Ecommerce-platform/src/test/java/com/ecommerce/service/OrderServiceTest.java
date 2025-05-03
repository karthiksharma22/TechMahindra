package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.model.User.Role;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;

public class OrderServiceTest {

    @Mock private OrderRepository orepo;
    @Mock private CartRepository crepo;

    @InjectMocks
    private OrderService o;

    private Product p;
    private User u;
    private Cart ec;
    private Order so;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        p = new Product();
        p.setId(3433543L);
        p.setName("Lenova Laptop");
        p.setDescription("Brand:Lenova\nProcessor:I5\nSSD:500GB\nHDD:1TB\n");
        p.setPrice(50000);
        p.setStockQuantity(108);
        p.setImageUrl("https://example.com/image.png");

        u = new User();
        u.setId(86543L);
        u.setUsername("Bhanu");
        u.setPassword("bhanu@123");
        u.setEmail("bhanu@gmail.com");
        u.setRole(Role.ADMIN);

        ec = new Cart();
        ec.setId(1L);
        ec.setUser(u);
        ec.setProducts(new ArrayList<>(List.of(p)));
        ec.setTotalPrice(50000);

        so = Order.builder()
                .id(1L)
                .products(List.of(p))
                .totalAmount(50000)
                .orderDate(LocalDateTime.now())
                .status(Order.Status.PLACED)
                .build();
    }

    @Test
    public void testPlaceOrder() {
        when(crepo.findById(1L)).thenReturn(Optional.of(ec));
        when(orepo.save(any(Order.class))).thenReturn(so);

        Order order = o.placeOrder();

        assertNotNull(order);
        assertEquals(50000, order.getTotalAmount());
        assertEquals(Order.Status.PLACED, order.getStatus());
        assertEquals(1, order.getProducts().size());

        verify(crepo).save(ec);
        verify(orepo).save(any(Order.class));
    }

    @Test
    public void testGetOrderHistory() {
        when(orepo.findAll()).thenReturn(List.of(so));

        List<Order> res = o.getOrderHistory();

        assertNotNull(res);
        assertEquals(1, res.size());
        assertEquals(so, res.get(0));
        verify(orepo).findAll();
    }

    @Test
    public void testGetOrderById() {
        when(orepo.findById(1L)).thenReturn(Optional.of(so));

        Order result = o.getOrderById(1L);

        assertNotNull(result);
        assertEquals(so.getId(), result.getId());
        verify(orepo).findById(1L);
    }
}
