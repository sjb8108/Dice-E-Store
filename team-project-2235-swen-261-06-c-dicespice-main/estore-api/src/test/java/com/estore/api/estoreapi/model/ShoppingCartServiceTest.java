package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCart;
import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCartService;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.persistence.ShoppingCart.ShoppingCartDAO;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * The unit test suite for the ShoppingCartService class
 * @author Mark Luskiewicz
 */
@Tag("Model-tier")
public class ShoppingCartServiceTest {

    private ShoppingCartDAO cartDAO;
    private UserDAO userDAO;
    private InventoryService inventory;
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    public void setUp() {
        cartDAO = mock(ShoppingCartDAO.class);
        userDAO = mock(UserDAO.class);
        inventory = mock(InventoryService.class);
        shoppingCartService = new ShoppingCartService(cartDAO, userDAO, inventory);
    }

    @Test
    public void addDieToCartTest() throws IOException {
        // Setup
        ShoppingCart cart = new ShoppingCart();
        Dice dice = new Dice(1, "cyan", 5, 5.99);
        User user = new User("testUser", "Password1!");
    
        when(userDAO.getCurrentUser()).thenReturn(user);
        when(inventory.getDie(dice.getId())).thenReturn(dice);
        when(inventory.getQuantity(dice.getId())).thenReturn(10); // Add this line
    
        // Invoke
        shoppingCartService.addDieToCart(cart, dice);
    
        // Analyze
        verify(cartDAO, times(1)).saveShoppingCart(cart, user.getUsername());
    }

    @Test
    public void removeDiceFromCartTest() throws IOException {
        // Setup
        ShoppingCart cart = new ShoppingCart();
        Dice dice = new Dice(1, "cyan", 5, 5.99);
        User user = new User("testUser", "Password1!");

        when(userDAO.getCurrentUser()).thenReturn(user);
        when(inventory.getDie(dice.getId())).thenReturn(dice);
        when(inventory.getQuantity(dice.getId())).thenReturn(10);

        cart.addDice(dice, inventory); // Add the dice to the cart first

        // Invoke
        shoppingCartService.removeDiceFromCart(cart, dice);

        // Analyze
        verify(cartDAO, times(1)).saveShoppingCart(cart, user.getUsername());
    }

    @Test
    public void decrementDiceTest() throws IOException {
        // Setup
        ShoppingCart cart = new ShoppingCart();
        Dice dice = new Dice(1, "cyan", 5, 5.99);
        User user = new User("testUser", "Password1!");

        when(userDAO.getCurrentUser()).thenReturn(user);
        when(inventory.getDie(dice.getId())).thenReturn(dice);
        when(inventory.getQuantity(dice.getId())).thenReturn(10);

        cart.addDice(dice, inventory); // Add the dice to the cart first

        // Invoke
        shoppingCartService.decrementDice(cart, dice);

        // Analyze
        verify(cartDAO, times(1)).saveShoppingCart(cart, user.getUsername());
    }

    @Test
    public void loadUserCartTest() {
        // Setup
        User user = new User("testUser", "Password1!");
        ShoppingCart cart = new ShoppingCart();

        when(userDAO.getCurrentUser()).thenReturn(user);
        when(cartDAO.loadShoppingCart(user.getUsername())).thenReturn(cart);

        // Invoke
        ShoppingCart result = shoppingCartService.loadUserCart();

        // Analyze
        assertEquals(cart, result);
    }

    @Test
    public void saveCartTest() throws IOException {
        // Setup
        ShoppingCart cart = new ShoppingCart();
        User user = new User("testUser", "Password1!");

        when(userDAO.getCurrentUser()).thenReturn(user);

        // Invoke
        shoppingCartService.saveCart(cart);

        // Analyze
        verify(cartDAO, times(1)).saveShoppingCart(cart, user.getUsername());
    }

    @Test
    public void clearCartTest() throws IOException {
        // Setup
        ShoppingCart cart = new ShoppingCart();
        Dice dice = new Dice(1, "cyan", 5, 5.99);
        User user = new User("testUser", "P@ssw0rd1"); // Mock User object
    
        when(userDAO.getCurrentUser()).thenReturn(user); // Set the mocked User as the current user
        when(inventory.getDie(dice.getId())).thenReturn(dice);
        when(inventory.getQuantity(dice.getId())).thenReturn(10);
    
        cart.addDice(dice, inventory); // Add the dice to the cart first
    
        // Invoke
        shoppingCartService.clearCart(cart);
    
        // Analyze
        assertTrue(cart.getShoppingCart().isEmpty());
    }
}