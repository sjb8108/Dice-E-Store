package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCart;
import com.estore.api.estoreapi.persistence.Dice.DiceFileDAO;

/**
 * The unit test suite for the ShoppingCart class
 * 
 * @author Scott Bullock
 */
@Tag("Model-tier")
public class ShoppingCartTest {
    private InventoryService inventory;

    @BeforeEach
    public void setUp(){
        DiceFileDAO mockDiceDAO = mock(DiceFileDAO.class);
        inventory = new InventoryService(mockDiceDAO);
        Dice dice1 = new Dice(1, "red", 5, 5.99);
        Dice dice2 = new Dice(2, "blue", 6, 6.99);
        Dice dice3 = new Dice(3, "black", 7, 7.99);
        inventory.addDie(dice1, 1);
        inventory.addDie(dice2, 1);
        inventory.addDie(dice3, 1);
    }
    
    @Test
    public void testCtor(){
        //Setup

        //Invoke
        ShoppingCart shoppingCart = new ShoppingCart();

        //Analyze
        assertNotNull(shoppingCart.getShoppingCart(), "Shopping cart should not be null after initialization");
        assertTrue(shoppingCart.getShoppingCart().isEmpty(), "Shopping cart should be empty after initialization");
    }

    @Test
    public void addDiceTest(){
        //Setup
        Dice checkoutDice = new Dice(1, "red", 5, 5.99);
        ShoppingCart shoppingCart = new ShoppingCart();

        //Invoke
        shoppingCart.addDice(checkoutDice, inventory);

        //Analyze
        assertFalse(shoppingCart.getShoppingCart().isEmpty(), "Shopping cart should not be empty after adding a dice");
        assertEquals(1, shoppingCart.getShoppingCart().size(), "Shopping cart should contain one item after adding a dice");
        assertEquals(checkoutDice, shoppingCart.getShoppingCart().get(0).getDice(), "The dice in the shopping cart should be the one that was added");
    }

    @Test
    public void addDiceNoStock() {
        // Setup
        Dice checkoutDice = new Dice(1, "cyan", 5, 5.99);
        ShoppingCart shoppingCart = new ShoppingCart();

        // Mock the InventoryService
        InventoryService inventory = Mockito.mock(InventoryService.class);

        // When getQuantity is called on the mock inventory, return 0
        Mockito.when(inventory.getQuantity(checkoutDice.getId())).thenReturn(0);

        // Invoke and Analyze
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingCart.addDice(checkoutDice, inventory);
        });

        String expectedMessage = "This dice is out of stock";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void decrementDiceDiceQuantity2Test(){
        //Setup
        Dice checkoutDice = new Dice(1, "red", 5, 5.99);
        inventory.addDie(checkoutDice, 2);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addDice(checkoutDice, inventory);
        shoppingCart.addDice(checkoutDice, inventory);

        //Invoke
        shoppingCart.decrementDice(checkoutDice);

        //Analzye
        assertEquals(1, shoppingCart.getShoppingCart().get(0).getQuantity());
    }

    @Test
    public void decrementDiceDiceQuantity1Test(){
        //Setup
        Dice checkoutDice = new Dice(1, "red", 5, 5.99);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addDice(checkoutDice, inventory);

        //Invoke
        shoppingCart.decrementDice(checkoutDice);

        //Analzye
        assertEquals(0, shoppingCart.getSizeOfShoppingCart());
    }

    @Test
    public void removeDiceTest(){
        //Setup
        Dice checkoutDice = new Dice(1, "red", 5, 5.99);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addDice(checkoutDice, inventory);

        //Invoke
        shoppingCart.removeDice(checkoutDice);

        //Analzye
        assertEquals(0, shoppingCart.getSizeOfShoppingCart());
    }

    @Test
    public void removeDiceNoDiceTest(){
        //Setup
        Dice checkoutDice = new Dice(1, "red", 5, 5.99);
        ShoppingCart shoppingCart = new ShoppingCart();
        Dice removeDice = new Dice(2, "cyan", 5, 5.99);
        shoppingCart.addDice(checkoutDice, inventory);

        //Invoke and Analzye
        try {
            shoppingCart.removeDice(removeDice);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("This dice is not in the cart", e.getMessage());
        }
    }

    @Test
    public void clearTest(){
        //Setup
        Dice checkoutDice = new Dice(1, "red", 5, 5.99);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addDice(checkoutDice, inventory);

        //Invoke
        shoppingCart.clear();

        //Analzye
        assertEquals(0, shoppingCart.getSizeOfShoppingCart());
    }

    @Test
    public void getSizeOfShoppingCartTest(){
        //Setup
        Dice checkoutDice = new Dice(1, "red", 5, 5.99);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addDice(checkoutDice, inventory);

        //Invoke
        int actual = shoppingCart.getSizeOfShoppingCart();

        //Analzye
        assertEquals(1, actual);

    }

    @Test
    public void toStringTest(){
        //Setup
        ShoppingCart shoppingCart = new ShoppingCart();
        
        //Invoke
        String actual = shoppingCart.toString();

        //Analzye
        assertEquals("ShoppingCart [items=[]]", actual);
    }
}
