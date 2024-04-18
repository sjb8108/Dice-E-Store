package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Wishlist.ListItem;

/**
 * The unit test suite for the ListItem class
 * 
 * @author Lara Toklar
 */
@Tag("Model-tier")
public class ListItemTest {

    @Test
    public void TestConstructor(){
        //Setup

        //Invoke
        ListItem listItem = new ListItem();

        //Analyze
        assertNotNull(listItem);
    }
    
    @Test
    public void testCtor(){
        //Setup
        Dice testDice = new Dice(1, "red", 5, 5.99);

        //Invoke
        ListItem listItem = new ListItem(testDice);

        //Analyze
        assertEquals(testDice, listItem.getDice());
        
    }

    @Test
    public void testDice(){
        //Setup
        Dice testDice = new Dice(1, "red", 5, 5.99);
        ListItem listItem = new ListItem(testDice);
        Dice newDice = new Dice(2, "blue", 8, 8.99);

        //Invoke
        listItem.setDice(newDice);

        //Analyze
        assertEquals(newDice, listItem.getDice());
    }


    @Test
    public void equalDice(){
        //Setup
        Dice testDice1 = new Dice(1, "red", 5, 5.99);
        ListItem listItemDice1 = new ListItem(testDice1);
        Dice testDice2 = new Dice(2, "Blue", 8, 8.99);
        ListItem listItemDice2 = new ListItem(testDice2);
        Dice testDice3 = new Dice(3, "red", 5, 5.99);
        ListItem listItemDice3 = new ListItem(testDice3);

        //Invoke
        boolean expec_true = listItemDice1.equalDice(testDice3);
        boolean expec_false = listItemDice2.equals(listItemDice3);

        //Analzye

        assertEquals(expec_true, true);
        assertEquals(expec_false, false);
    }
    
}
