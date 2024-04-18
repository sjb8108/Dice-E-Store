package com.estore.api.estoreapi.model.ShoppingCart;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.api.estoreapi.model.Dice;
import com.estore.api.estoreapi.model.InventoryService;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.persistence.ShoppingCart.ShoppingCartDAO;

/**
 * Represents the shopping cart for the user to store dice products for purchase
 * @author Mark Luskiewicz
 */
@Service
public class ShoppingCartService {
    private ShoppingCartDAO cartDAO;
    private UserDAO userDAO;
    private InventoryService inventory;

    /**
     * Constructor for the ShoppingCartService
     * @param cartDAO the shopping cart DAO
     * @param userDAO the user DAO
     * @param inventory the inventory service
     */
    @Autowired
    public ShoppingCartService(ShoppingCartDAO cartDAO, UserDAO userDAO, InventoryService inventory) {
        this.cartDAO = cartDAO;
        this.userDAO = userDAO;
        this.inventory = inventory;
    }

    /**
     * Adds a die to the shopping cart
     * @param cart the shopping cart
     * @param dice the dice to be added to the shopping cart
     * @throws IOException thrown if there is an error saving the shopping cart
     */
    public void addDieToCart(ShoppingCart cart, Dice dice) throws IOException {
        User currentUser = userDAO.getCurrentUser();
        cart.addDice(dice, inventory);
        cartDAO.saveShoppingCart(cart, currentUser.getUsername());
    }

    /**
     * Removes a die from the shopping cart
     * @param cart the shopping cart
     * @param dice the dice to be removed from the shopping cart
     * @throws IOException thrown if there is an error saving the shopping cart
     */
    public void removeDiceFromCart(ShoppingCart cart, Dice dice) throws IOException {
        User currentUser = userDAO.getCurrentUser();
        cart.removeDice(dice);
        cartDAO.saveShoppingCart(cart, currentUser.getUsername());
    }

    /**
     * Decrements the quantity of a dice in the shopping cart by 1 or removes it if the quantity is 1
     * @param cart the shopping cart
     * @param dice the dice to be decremented
     * @throws IOException thrown if there is an error saving the shopping cart
     */
    public void decrementDice(ShoppingCart cart, Dice dice) throws IOException {
        User currentUser = userDAO.getCurrentUser();
        cart.decrementDice(dice);
        cartDAO.saveShoppingCart(cart, currentUser.getUsername());
    }

    /**
     * Loads the shopping cart for the current user
     * @return the shopping cart for the current user
     */
    public ShoppingCart loadUserCart() {
        User currentUser = userDAO.getCurrentUser();
        ShoppingCart cart = cartDAO.loadShoppingCart(currentUser.getUsername());
        return cart;
    }

    /**
     * Saves the shopping cart for the current user
     * @param cart the shopping cart to be saved
     * @throws IOException thrown if there is an error saving the shopping cart
     */
    public void saveCart(ShoppingCart cart) throws IOException {
        User currentUser = userDAO.getCurrentUser();
        cartDAO.saveShoppingCart(cart, currentUser.getUsername());
    }

    /**
     * Clears the shopping cart
     * @param cart the shopping cart to be cleared
     * @throws IOException thrown if there is an error saving the shopping cart
     */
    public void clearCart(ShoppingCart cart) throws IOException {
        User currentUser = userDAO.getCurrentUser();
        cart.clear();
        cartDAO.saveShoppingCart(cart, currentUser.getUsername());
    }
}
