package com.estore.api.estoreapi.model.Wishlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.estore.api.estoreapi.model.Dice;
import com.estore.api.estoreapi.model.InventoryService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/** 
 * Represents a wishlist of dice products
 * Contains methods to add, remove, and clear dice products from the wishlist
 * Saves information into a JSON file containing "items" as a list of dice products
 * 
 * @author Lara Toklar
 */
public class Wishlist {
    @JsonProperty("items")
    private List<ListItem> items;

    /**
     * Default constructor for the Wishlist class
     * Initializes the wishlist items list
     */
    public Wishlist() {
        items = new ArrayList<>();
    }

    /**
     * Adds a dice to the wishlist
     * @param dice the dice to be added to the wishlist
     * @param inventory the inventory service
     * @throws IllegalArgumentException thrown if the dice is already in the list
     */
    public void addDice(Dice dice, InventoryService inventory) {
        for (ListItem currentDice : items){
            if (currentDice.equalDice(dice)){
                throw new IllegalArgumentException("This dice is already in the wishlist");
            }
        }
        items.add(new ListItem(dice));
    }

    /**
     * Removes a dice from the wishlist
     * @param dice the dice to be removed from the wishlist
     * @throws IllegalArgumentException thrown if the dice is not in the list
     */
    public void removeDice(Dice dice) {
        for (ListItem currentDice : items) {
                items.remove(currentDice);
                return;
        }
    }

    /**
     * Clears the entire wishlist
     */
    public void clear() {
        items.clear();
    }

    /**
     * Gets the wishlist
     * @return the wishlist
     */
    @JsonIgnore
    public List<ListItem> getWishlist(){
        return this.items;
    }

    /**
     * Gets the size of the wishlist
     * @return the size of the wishlist
     */
    @JsonIgnore
    public int getSizeOfWishlist(){
        return this.items.size();
    }


    @Override
    public String toString() {
        return "Wishlist [items=" + items.stream()
                                      .map(item -> String.format("[Dice{id=%d, color='%s', sides=%d, price=%.2f}",
                                                                  item.getDice().getId(),
                                                                  item.getDice().getColor(),
                                                                  item.getDice().getSides(),
                                                                  item.getDice().getPrice()))
                                      .collect(Collectors.joining(", ")) + "]]";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wishlist wishlist = (Wishlist) o;
        return Objects.equals(items, wishlist.items);
    }
}
