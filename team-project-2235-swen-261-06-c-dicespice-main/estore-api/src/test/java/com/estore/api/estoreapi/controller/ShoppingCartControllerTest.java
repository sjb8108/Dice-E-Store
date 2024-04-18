package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Dice;
import com.estore.api.estoreapi.model.InventoryService;
import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCartService;
import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCart;
import com.estore.api.estoreapi.controller.ShoppingCartController;
import com.estore.api.estoreapi.controller.InventoryController;

/**
 * The unit test suite for the ShoppingCartController class
 * 
 * @author Scott Bullock and Lara Toklar
 */
@Tag("Controller-tier")
public class ShoppingCartControllerTest {
    private ShoppingCartController shoppingCartController;
    private ShoppingCartService mockShoppingCartService;
    private ShoppingCart mockShoppingCart;
    private InventoryService mockInventory;
    private InventoryController inventoryController; 

    /**
     * Before each test, create a new DiceController object and inject
     * a mock Dice DAO
     */
    @BeforeEach
    public void setupCartController() {
        mockShoppingCartService = mock(ShoppingCartService.class);
        mockShoppingCart = mock(ShoppingCart.class);
        mockInventory = mock(InventoryService.class);
        shoppingCartController = new ShoppingCartController(mockShoppingCartService,mockInventory);
        inventoryController = new InventoryController(mockInventory);
    }

    @Test
    public void testGetCart() {
        when(mockShoppingCartService.loadUserCart()).thenReturn(mockShoppingCart);
        ShoppingCart result = shoppingCartController.getCart().getBody();
        assertEquals(mockShoppingCart,result);
    }

    @Test
    public void testAddDieToCart() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockShoppingCartService.loadUserCart()).thenReturn(mockShoppingCart);
        doNothing().when(mockShoppingCartService).addDieToCart(mockShoppingCart, die);

        ResponseEntity<ShoppingCart> result = shoppingCartController.addDieToCart(die.getId());

        verify(mockInventory).getDie(die.getId());
        verify(mockShoppingCartService).addDieToCart(mockShoppingCart, die);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockShoppingCart, result.getBody());
    }

    @Test
    public void testAddDieToCartNotFound() throws IOException  {
        Dice die = new Dice(1, "red", 6, 4.99);
        ResponseEntity<ShoppingCart> response = shoppingCartController.addDieToCart(die.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        
    }

    @Test
    public void testDeleteDieFromCart() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockShoppingCartService.loadUserCart()).thenReturn(mockShoppingCart);
        doNothing().when(mockShoppingCartService).removeDiceFromCart(mockShoppingCart, die);
    
        ResponseEntity<ShoppingCart> result = shoppingCartController.deleteDieFromCart(die.getId());
    
        verify(mockInventory).getDie(die.getId());
        verify(mockShoppingCartService).removeDiceFromCart(mockShoppingCart, die);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockShoppingCart, result.getBody());
    }

    @Test
    public void testDeleteDieFromCart_IllegalArgumentException() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockShoppingCartService.loadUserCart()).thenReturn(mockShoppingCart);
        doThrow(IllegalArgumentException.class).when(mockShoppingCartService).removeDiceFromCart(mockShoppingCart, die);
    
        ResponseEntity<ShoppingCart> result = shoppingCartController.deleteDieFromCart(die.getId());
    
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
    
    @Test
    public void testDeleteDieFromCart_IOException() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockShoppingCartService.loadUserCart()).thenReturn(mockShoppingCart);
        doThrow(IOException.class).when(mockShoppingCartService).removeDiceFromCart(mockShoppingCart, die);
    
        ResponseEntity<ShoppingCart> result = shoppingCartController.deleteDieFromCart(die.getId());
    
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }    

    @Test
    public void testDecrementShoppingCart() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockShoppingCartService.loadUserCart()).thenReturn(mockShoppingCart);
        doNothing().when(mockShoppingCartService).decrementDice(mockShoppingCart, die);
    
        ResponseEntity<ShoppingCart> result = shoppingCartController.decrementShoppingCart(die.getId());
    
        verify(mockInventory).getDie(die.getId());
        verify(mockShoppingCartService).decrementDice(mockShoppingCart, die);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockShoppingCart, result.getBody());
    }

    @Test
    public void testDecrementShoppingCart_IllegalArgumentException() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockShoppingCartService.loadUserCart()).thenReturn(mockShoppingCart);
        doThrow(IllegalArgumentException.class).when(mockShoppingCartService).decrementDice(mockShoppingCart, die);
    
        ResponseEntity<ShoppingCart> result = shoppingCartController.decrementShoppingCart(die.getId());
    
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
    
    @Test
    public void testDecrementShoppingCart_IOException() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockShoppingCartService.loadUserCart()).thenReturn(mockShoppingCart);
        doThrow(IOException.class).when(mockShoppingCartService).decrementDice(mockShoppingCart, die);
    
        ResponseEntity<ShoppingCart> result = shoppingCartController.decrementShoppingCart(die.getId());
    
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
    
    @Test
    public void testAddDieToCart_IllegalArgumentException() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockShoppingCartService.loadUserCart()).thenReturn(mockShoppingCart);
        doThrow(IllegalArgumentException.class).when(mockShoppingCartService).addDieToCart(mockShoppingCart, die);
    
        ResponseEntity<ShoppingCart> result = shoppingCartController.addDieToCart(die.getId());
    
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
    
    @Test
    public void testAddDieToCart_Exception() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockShoppingCartService.loadUserCart()).thenThrow(RuntimeException.class);
    
        ResponseEntity<ShoppingCart> result = shoppingCartController.addDieToCart(die.getId());
    
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }    

    @Test
    public void testAddDieToShoppingCart_NotFound() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(null);

        ResponseEntity<ShoppingCart> result = shoppingCartController.addDieToCart(die.getId());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDecrementShoppingCart_NotFound() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(null);

        ResponseEntity<ShoppingCart> result = shoppingCartController.decrementShoppingCart(die.getId());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }


    @Test
    public void testDeleteDieFromCart_NotFound() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(null);

        ResponseEntity<ShoppingCart> result = shoppingCartController.deleteDieFromCart(die.getId());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
