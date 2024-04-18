package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


/**
 * The unit test suite for the Die class
 * 
 * @author Scott Bullock and Lara Toklar
 */
@Tag("Model-tier")
public class DiceTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_color = "green";
        int expected_sides = 5; 
        double expected_price = 19.99;

        // Invoke
        Dice die = new Dice(expected_id,expected_color,expected_sides,expected_price);

        // Analyze
        assertEquals(expected_id,die.getId());
        assertEquals(expected_color,die.getColor());
    }

    @Test
    public void testColor() {
        // Setup
        int id = 99;
        String color = "green";
        int sides = 5;
        double price = 19.99;
        Dice die = new Dice(id,color,sides,price);

        String expected_color = "green";

        // Invoke
        die.setColor(expected_color);

        // Analyze
        assertEquals(expected_color,die.getColor());
    }

    @Test
    public void testSides() {
        // Setup
        int id = 99;
        String color = "green";
        int sides = 5;
        double price = 19.99;
        Dice die = new Dice(id,color,sides,price);

        int expected_sides = 5;

        // Invoke
        die.setSides(expected_sides);

        // Analyze
        assertEquals(expected_sides,die.getSides());
    }

    @Test
    public void testPrice(){
        //Setup
        int id = 35;
        String color = "cyan";
        int sides = 20;
        double price = 29.99;
        Dice dice = new Dice(id, color, sides, price);

        double expected_price = 29.99;

        //Invoke
        dice.setPrice(expected_price);

        //Analyze
        assertEquals(expected_price, dice.getPrice());
    }


    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String color = "green";
        int sides = 5;
        double price = 19.99;
        String expected_string = String.format(Dice.STRING_FORMAT,id,color,sides,price);
        Dice die = new Dice(id,color,sides,price);

        // Invoke
        String actual_string = die.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}