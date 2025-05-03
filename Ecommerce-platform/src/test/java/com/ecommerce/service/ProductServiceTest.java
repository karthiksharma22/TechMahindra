package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.ecommerce.repository.ProductRepository;

public class ProductServiceTest {
	@Mock ProductRepository prepo;
	@InjectMocks
	private ProductService pSer;
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
	public void AllProdTest() {
		List<Product> res = pSer.getAllProducts();
		res.add(p);
		when(prepo.findAll()).thenReturn(res);
		assertNotNull(res);
		assertEquals(p,res.get(0));
	}
	@Test
	public void getProdByIdTest() {
		when(prepo.findById(3433543L)).thenReturn(Optional.of(p));
		Product res = pSer.getProductById(3433543L);
		assertNotNull(res);
		assertEquals(p,res);
	}
	@Test
	public void createProd() {
		when(prepo.save(any(Product.class))).thenReturn(p);
		Product res = pSer.createProduct(p);
		assertNotNull(res);
		assertEquals("Lenova Laptop",res.getName());
		verify(prepo).save(p);
	}
	@Test
	public void testUpdate() {
		Product p1 = new Product();
        p1.setId(3433543L);
        p1.setName("Lenova Laptop");
        p1.setDescription("Brand:Lenova\nProcessor:I5\nSSD:500GB\nHDD:1TB\n");
        p1.setPrice(55000);
        p1.setStockQuantity(108);
        p1.setImageUrl("https://example.com/image.png");
        when(prepo.findById(3433543L)).thenReturn(Optional.of(p));
        when(prepo.save(any(Product.class))).thenReturn(p);
        Product res = pSer.updateProduct(3433543L, p1);
        assertNotNull(res);
        assertEquals(55000,res.getPrice());
        verify(prepo).save(p1);
	}
	@Test
	public void TestDelete() {
		doNothing().when(prepo).deleteById(1L);
		pSer.deleteProduct(1L);
		verify(prepo).deleteById(1L);
	}
}
