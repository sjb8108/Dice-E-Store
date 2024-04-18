package com.estore.api.estoreapi.model.Wishlist;

import com.estore.api.estoreapi.model.Dice;

/**
 * Represents an item (die) in the wishlist
 * 
 * @author Lara Toklar
 */
public class ListItem {
    
    private Dice dice;

    /**
     * Default constructor for the ListItem class
     */
    public ListItem() {
    }

    /**
     * Constructor for the ListItem class
     * @param dice the dice product
     */
    public ListItem(Dice dice) {
        this.dice = dice;
    }

    /**
     * Sets the dice product
     * @param dice the dice product
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }

    /**
     * Gets the dice product
     * @return the dice product
     */
    public Dice getDice() {
        return this.dice;
    }

    /**
     * Checks if the dice product is equal to another dice product
     * @param dice the dice product to compare
     * @return true if the dice products are equal, false otherwise
     */
    public boolean equalDice(Dice dice) {
        return this.dice.equals(dice);
    }
}
