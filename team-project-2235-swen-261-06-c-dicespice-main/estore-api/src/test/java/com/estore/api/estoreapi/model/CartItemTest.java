package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.ShoppingCart.CartItem;

/**
 * The unit test suite for the CartItem class
 * 
 * @author Scott Bullock
 */
@Tag("Model-tier")
public class CartItemTest {

    @Test
    public void TestConstructor(){
        //Setup

        //Invoke
        CartItem cartItem = new CartItem();

        //Analyze
        assertNotNull(cartItem);
    }
    
    @Test
    public void testCtor(){
        //Setup
        Dice testDice = new Dice(1, "red", 5, 5.99);
        int testQuantity = 10;

        //Invoke
        CartItem cartItem = new CartItem(testDice, testQuantity);

        //Analyze
        assertEquals(testDice, cartItem.getDice());
        assertEquals(testQuantity, cartItem.getQuantity());
    }

    @Test
    public void testDice(){
        //Setup
        Dice testDice = new Dice(1, "red", 5, 5.99);
        int testQuantity = 10;
        CartItem cartItem = new CartItem(testDice, testQuantity);
        Dice newDice = new Dice(2, "blue", 8, 8.99);

        //Invoke
        cartItem.setDice(newDice);

        //Analyze
        assertEquals(newDice, cartItem.getDice());
    }

    @Test
    public void testQuantity(){
        //Setup
        Dice testDice = new Dice(1, "red", 5, 5.99);
        int testQuantity = 10;
        CartItem cartItem = new CartItem(testDice, testQuantity);
        int newQuantity = 20;

        //Invoke
        cartItem.setQuantity(newQuantity);

        //Analyze
        assertEquals(newQuantity, cartItem.getQuantity());
    }

    @Test
    public void equalDice(){
        //Setup
        Dice testDice1 = new Dice(1, "red", 5, 5.99);
        int dice1quantity = 10;
        CartItem cartItemDice1 = new CartItem(testDice1, dice1quantity);
        Dice testDice2 = new Dice(2, "Blue", 8, 8.99);
        int dice2quantity = 20;
        CartItem cartItemDice2 = new CartItem(testDice2, dice2quantity);
        Dice testDice3 = new Dice(3, "red", 5, 5.99);
        int dice3quantity = 10;
        CartItem cartItemDice3 = new CartItem(testDice3, dice3quantity);

        //Invoke
        boolean expec_true = cartItemDice1.equalDice(testDice3);
        boolean expec_false = cartItemDice2.equals(cartItemDice3);

        //Analzye

        assertEquals(expec_true, true);
        assertEquals(expec_false, false);
    }
    
}
