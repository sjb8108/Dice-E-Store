package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCart;
import com.estore.api.estoreapi.model.Wishlist.Wishlist;
import com.estore.api.estoreapi.model.Wishlist.WishlistService;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.persistence.Wishlist.WishlistDAO;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * The unit test suite for the WishlistService class
 * @author Lara Toklar
 */
@Tag("Model-tier")
public class WishlistServiceTest {

    private WishlistDAO listDAO;
    private UserDAO userDAO;
    private InventoryService inventory;
    private WishlistService wishlistService;

    @BeforeEach
    public void setUp() {
        listDAO = mock(WishlistDAO.class);
        userDAO = mock(UserDAO.class);
        inventory = mock(InventoryService.class);
        wishlistService = new WishlistService(listDAO, userDAO, inventory);
    }

    @Test
    public void addDieToCartTest() throws IOException {
        // Setup
        Wishlist list = new Wishlist();
        Dice dice = new Dice(1, "cyan", 5, 5.99);
        User user = new User("testUser", "P@ssw0rd1");
    
        when(userDAO.getCurrentUser()).thenReturn(user);
        when(inventory.getDie(dice.getId())).thenReturn(dice);
    
        // Invoke
        wishlistService.addDieToList(list, dice);
    
        // Analyze
        verify(listDAO, times(1)).saveWishlist(list, user.getUsername());
    }

    @Test
    public void removeDiceFromListTest() throws IOException {
        // Setup
        Wishlist list = new Wishlist();
        Dice dice = new Dice(1, "cyan", 5, 5.99);
        User user = new User("testUser", "P@ssw0rd1");

        when(userDAO.getCurrentUser()).thenReturn(user);
        when(inventory.getDie(dice.getId())).thenReturn(dice);

        list.addDice(dice, inventory); // Add the dice to the list first

        // Invoke
        wishlistService.removeDiceFromList(list, dice);

        // Analyze
        verify(listDAO, times(1)).saveWishlist(list, user.getUsername());
    }


    @Test
    public void loadUserListTest() {
        // Setup
        User user = new User("testUser", "P@ssw0rd1");
        Wishlist list = new Wishlist();

        when(userDAO.getCurrentUser()).thenReturn(user);
        when(listDAO.loadWishlist(user.getUsername())).thenReturn(list);

        // Invoke
        Wishlist result = wishlistService.loadUserList();

        // Analyze
        assertEquals(list, result);
    }

    @Test
    public void saveListTest() throws IOException {
        // Setup
        Wishlist list = new Wishlist();
        User user = new User("testUser", "P@ssw0rd1");

        when(userDAO.getCurrentUser()).thenReturn(user);

        // Invoke
        wishlistService.saveList(list);

        // Analyze
        verify(listDAO, times(1)).saveWishlist(list, user.getUsername());
    }

    @Test
public void clearAllDiceFromListTest() throws IOException {
    // Setup
    Wishlist list = new Wishlist();
    Dice dice1 = new Dice(1, "cyan", 5, 5.99);
    Dice dice2 = new Dice(2, "red", 6, 6.99);
    User user = new User("testUser", "P@ssw0rd1");

    when(userDAO.getCurrentUser()).thenReturn(user);
    when(inventory.getDie(dice1.getId())).thenReturn(dice1);
    when(inventory.getDie(dice2.getId())).thenReturn(dice2);

    list.addDice(dice1, inventory); // Add the first dice to the list
    list.addDice(dice2, inventory); // Add the second dice to the list

    // Assert that the list is not empty before clearing
    assertFalse(list.getWishlist().isEmpty());

    // Invoke
    wishlistService.clearAllDiceFromList(list);

    // Analyze
    assertTrue(list.getWishlist().isEmpty());
    verify(listDAO, times(1)).saveWishlist(list, user.getUsername());
}
}