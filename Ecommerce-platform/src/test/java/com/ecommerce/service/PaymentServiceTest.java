package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.model.Payment;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.model.User.Role;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.PaymentRepository;

public class PaymentServiceTest {
	@Mock private PaymentRepository prepo;
    @Mock private OrderRepository orepo;
    @InjectMocks
    private PaymentService pSer;
    private Product p;
    private User u;
    private User u1;
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
    public void TestMakePayment() {
    	when(orepo.findAll()).thenReturn(List.of(so));
    	when(orepo.save(any(Order.class))).thenReturn(so);
    	when(prepo.save(any(Payment.class))).thenAnswer(invocation->invocation.getArgument(0));
    	Payment res=pSer.makePayment(50000);
    	assertNotNull(res);
    	assertEquals(Payment.Status.SUCCESS,res.getStatus());
    	assertEquals(Order.Status.PAID,so.getStatus());
    	assertNotNull(res.getTransactionId());
    	assertEquals(so,res.getOrder());
    	verify(orepo).findAll();
    	verify(orepo).save(so);
    	verify(prepo).save(any(Payment.class));
    }
    @Test
    public void testPaymentFailed() {
    	 when(orepo.findAll()).thenReturn(List.of());

         RuntimeException exception = assertThrows(RuntimeException.class, () -> {
             pSer.makePayment(1000);
         });

         assertEquals("No order found for payment", exception.getMessage());
         verify(orepo).findAll();
         verify(prepo, never()).save(any(Payment.class));
    }
}
