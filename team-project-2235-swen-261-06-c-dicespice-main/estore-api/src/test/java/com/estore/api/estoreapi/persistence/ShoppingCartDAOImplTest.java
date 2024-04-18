package com.estore.api.estoreapi.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCart;
import com.estore.api.estoreapi.persistence.ShoppingCart.ShoppingCartDAOImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartDAOImplTest {

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private ShoppingCartDAOImpl shoppingCartDAO;

    private ShoppingCart cart;
    private String username;

    @BeforeEach
    public void setup() {
        cart = new ShoppingCart();
        username = "testUser";
    }

    @Test
    public void testSaveShoppingCart() throws IOException {
        File directory = new File("CartData");
        if (!directory.exists()){
            directory.mkdir();
        }
        File file = new File("CartData/" + username + ".json");

        doNothing().when(mapper).writeValue(file, cart);

        shoppingCartDAO.saveShoppingCart(cart, username);

        verify(mapper, times(1)).writeValue(file, cart);
    }

    @Test
    public void testLoadShoppingCart() throws IOException {
        String userFilePath = "CartData/" + username + ".json";
        File userFile = new File(userFilePath);

        when(mapper.readValue(userFile, ShoppingCart.class)).thenReturn(cart);

        ShoppingCart result = shoppingCartDAO.loadShoppingCart(username);

        verify(mapper, times(1)).readValue(userFile, ShoppingCart.class);
        assertEquals(cart, result);
    }

    @Test
    public void testCreateShoppingCart() throws IOException {
        doNothing().when(mapper).writeValue(any(File.class), any(ShoppingCart.class));

        shoppingCartDAO.createShoppingCart(username);

        verify(mapper, times(1)).writeValue(any(File.class), any(ShoppingCart.class));
    }

}
