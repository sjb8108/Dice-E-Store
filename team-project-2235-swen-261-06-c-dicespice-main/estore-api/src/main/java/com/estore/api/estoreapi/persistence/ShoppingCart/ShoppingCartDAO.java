package com.estore.api.estoreapi.persistence.ShoppingCart;


import java.io.IOException;

import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCart;

/**
 * Represents the data access object for the shopping cart
 * @author Mark Luskiewicz
 */
public interface ShoppingCartDAO {
    /**
     * Saves the shopping cart to the file
     * @param cart the shopping cart to be saved
     */ 
    void saveShoppingCart(ShoppingCart cart, String username) throws IOException;

    /**
     * Loads the shopping cart from the file
     * @return the shopping cart loaded from the file
     */
    ShoppingCart loadShoppingCart(String username);

    /**
     * Creates a shopping cart for the user
     * @param username the username of the user
     */
    void createShoppingCart(String username);
}
