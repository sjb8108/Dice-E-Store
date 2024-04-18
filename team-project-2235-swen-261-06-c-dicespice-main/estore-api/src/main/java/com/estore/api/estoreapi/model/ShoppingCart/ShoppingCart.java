package com.estore.api.estoreapi.model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.estore.api.estoreapi.model.Dice;
import com.estore.api.estoreapi.model.InventoryService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a shopping cart that stores the dice that a user wants to purchase
 * @author Scott Bullock & Mark Luskiewicz
 */
public class ShoppingCart {
    @JsonProperty("items")
    private List<CartItem> items;

    /**
     * Default constructor for the ShoppingCart class
     * Initializes the items list
     */
    public ShoppingCart() {
        items = new ArrayList<>();
    }

    /**
     * Adds a dice to the shopping cart
     * @param dice the dice to be added to the shopping cart
     * @throws IllegalArgumentException thrown if the dice is out of stock
     * @throws IllegalArgumentException thrown if there is not enough inventory to add more of this dice to the cart
     */
    public void addDice(Dice dice, InventoryService inventory) {
        boolean found = false;
        for (CartItem currentDice : items) {
            if (currentDice.equalDice(dice)) {
                if (currentDice.getQuantity() < inventory.getQuantity(dice.getId())) {
                    currentDice.setQuantity(currentDice.getQuantity() + 1);
                    found = true;
                    break;
                } else {
                    throw new IllegalArgumentException("Not enough inventory to add more of this dice to the cart");
                }
            }
        }

        if (!found) {
            if (inventory.getQuantity(dice.getId()) > 0) {
                items.add(new CartItem(dice, 1));
            } else {
                throw new IllegalArgumentException("This dice is out of stock");
            }
        }
    }

    /**
     * Decrements the quantity of a dice in the shopping cart by 1 or removes it if the quantity is 1
     * @param dice the dice to be decremented
     */
    public void decrementDice(Dice dice) {
        for (CartItem currentDice : items) {
            if (currentDice.equalDice(dice)) {
                if (currentDice.getQuantity() > 1) {
                    currentDice.setQuantity(currentDice.getQuantity() - 1);
                } else {
                    items.remove(currentDice);
                }
                return;
            }
        }
    }

    /**
     * Removes a dice from the shopping cart
     * @param dice the dice to be removed from the shopping cart
     * @throws IllegalArgumentException thrown if the dice is not in the cart
     */
    public void removeDice(Dice dice) {
        for (CartItem currentDice : items) {
            if (currentDice.equalDice(dice)) {
                items.remove(currentDice);
                return;
            } else {
                throw new IllegalArgumentException("This dice is not in the cart");
            }
        }
    }

    /**
     * Clears the shopping cart
     */ 
    public void clear() {
        items.clear();
    }

    /**
     * Gets a user's shopping cart
     * @return list of items that are in the shopping cart
     */
    @JsonIgnore
    public List<CartItem> getShoppingCart(){
        return this.items;
    }

    /**
     * Gets the amount of items in a user's shopping cart
     * @return the amount of items in a user's shopping cart
     */
    @JsonIgnore
    public int getSizeOfShoppingCart(){
        return this.items.size();
    }

    @Override
    public String toString() {
        return "ShoppingCart [items=" + items.stream()
                                          .map(CartItem::getDice)
                                          .collect(Collectors.toList()) + "]";
    }
}
