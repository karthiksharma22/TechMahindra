package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.model.User.Role;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;

public class CartServiceTest {

    @Mock
    private CartRepository repo1;

    @Mock
    private ProductRepository repo2;

    @InjectMocks
    private CartService c;

    private Product p;
    private Cart ec;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        p = new Product();
        p.setId(3433543L);
        p.setName("Lenova Laptop");
        p.setDescription("Brand:Lenova\nProcessor:I5\nSSD:500GB\nHDD:1TB\n");
        p.setPrice(50000);
        p.setStockQuantity(108);
        p.setImageUrl("https://p2-ofp.static.pub//fes/cms/2024/12/02/3we0d41t3l2j4byukm1csxhyvwn90z170608.png");
        
        User u=new User();
		u.setId(86543L);
		u.setUsername("Bhanu");
		u.setPassword("bhanu@123");
		u.setEmail("bhanu@gmail.com");
		u.setRole(Role.ADMIN);
		
		User u1=new User();
		u1.setId(86843L);
		u1.setUsername("Venkat");
		u1.setPassword("venkat@123");
		u1.setEmail("venkat@gmail.com");
		u1.setRole(Role.CUSTOMER);
		
        ec = new Cart();
        ec.setId(1L);
        ec.setUser(u);
        ec.setProducts(new ArrayList<>());
        ec.setTotalPrice(0);
    }

    @Test
    public void addProdTest() {
        when(repo2.findById(3433543L)).thenReturn(Optional.of(p));
        when(repo1.findById(1L)).thenReturn(Optional.of(ec));
        when(repo1.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cart updatedCart = c.addToCart(3433543L, 2);

        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getProducts().size());
        assertEquals(50000 * 2, updatedCart.getTotalPrice());
        assertEquals("Lenova Laptop", updatedCart.getProducts().get(0).getName());

        verify(repo2).findById(3433543L);
        verify(repo1).findById(1L);
        verify(repo1).save(any(Cart.class));
    }
    
    @Test
    public void getCartTest() {
    	when(repo1.findById(1L)).thenReturn(Optional.of(ec));
    	Cart x=c.getCart();
    	assertNotNull(x);
    	assertEquals(1L,x.getId());
    	verify(repo1).findById(1L);
    }
    @Test
    public void removeFromCartTest() {
        List<Product> products = new ArrayList<>();
        products.add(p);
        ec.setProducts(products);
        ec.setTotalPrice(p.getPrice());
        when(repo1.findById(1L)).thenReturn(Optional.of(ec));
        when(repo1.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));
        c.removeFromCart(p.getId());
        assertEquals(0, ec.getProducts().size());
        verify(repo1).save(ec);
    }
    @Test
    public void clearCartTest() {
        List<Product> products = new ArrayList<>();
        products.add(p);
        ec.setProducts(products);
        ec.setTotalPrice(p.getPrice());

        when(repo1.findById(1L)).thenReturn(Optional.of(ec));
        when(repo1.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        c.clearCart();

        assertTrue(ec.getProducts().isEmpty());
        assertEquals(0, ec.getTotalPrice());
        verify(repo1).save(ec);
    }
}
