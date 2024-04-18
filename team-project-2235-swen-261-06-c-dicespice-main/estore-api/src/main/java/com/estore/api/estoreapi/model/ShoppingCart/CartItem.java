package com.estore.api.estoreapi.model.ShoppingCart;

import com.estore.api.estoreapi.model.Dice;

public class CartItem {
    private Dice dice;
    private int quantity;

    /**
     * Default constructor for the CartItem class
     */
    public CartItem() {
    }

    /**
     * Constructor for the CartItem class
     * @param dice the dice product
     * @param quantity the quantity of the dice product
     */
    public CartItem(Dice dice, int quantity) {
        this.dice = dice;
        this.quantity = quantity;
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
     * Sets the quantity of the dice product
     * @param quantity the quantity of the dice product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the quantity of the dice product
     * @return the quantity of the dice product
     */
    public int getQuantity() {
        return this.quantity;
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
