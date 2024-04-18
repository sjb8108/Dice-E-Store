package com.estore.api.estoreapi.model.Wishlist;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.api.estoreapi.model.Dice;
import com.estore.api.estoreapi.model.InventoryService;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.persistence.Wishlist.WishlistDAO;

/**
 * Service for the wishlist
 * <p>
 * {@literal @}Service Spring annotation identifies this class as a service
 * method handler to the Spring framework
 * 
 * @author Lara Toklar
 */
@Service
public class WishlistService {
    
    private WishlistDAO listDAO;
    private UserDAO userDAO;
    private InventoryService inventory;

    /**
     * Constructor for the WishlistService
     * @param listDAO the wishlist DAO
     * @param userDAO the user DAO
     * @param inventory the inventory service
     */
    @Autowired
    public WishlistService(WishlistDAO listDAO, UserDAO userDAO, InventoryService inventory) {
        this.listDAO = listDAO;
        this.userDAO = userDAO;
        this.inventory = inventory;
    }

    /**
     * Adds a die to the wishlist
     * @param list the wishlist
     * @param dice the dice to be added to the wishlist
     * @throws IOException thrown if there is an error saving the wishlist
     */
    public void addDieToList(Wishlist list, Dice dice) throws IOException {
        User currentUser = userDAO.getCurrentUser();
        list.addDice(dice, inventory);
        listDAO.saveWishlist(list, currentUser.getUsername());
    }

    /**
     * Removes a die from the wishlist
     * @param list the wishlist
     * @param dice the dice to be removed from the wishlist
     * @throws IOException thrown if there is an error saving the wishlist
     */
    public void removeDiceFromList(Wishlist list, Dice dice) throws IOException {
        User currentUser = userDAO.getCurrentUser();
        list.removeDice(dice);
        listDAO.saveWishlist(list, currentUser.getUsername());
    }
    
    /**
     * Clears all dice from the wishlist
     * @param list the wishlist
     * @throws IOException thrown if there is an error saving the wishlist
     */
    public void clearAllDiceFromList(Wishlist list) throws IOException {
        User currentUser = userDAO.getCurrentUser();
        list.clear();
        listDAO.saveWishlist(list, currentUser.getUsername());
    }

    /**
     * Loads the wishlist for the current user
     * @return the wishlist for the current user
     */
    public Wishlist loadUserList() {
        User currentUser = userDAO.getCurrentUser();
        Wishlist list = listDAO.loadWishlist(currentUser.getUsername());
        return list;
    }

    /**
     * Saves the wishlist for the current user
     * @param list the wishlist to be saved
     * @throws IOException thrown if there is an error saving the wishlist
     */
    public void saveList(Wishlist list) throws IOException {
        User currentUser = userDAO.getCurrentUser();
        listDAO.saveWishlist(list, currentUser.getUsername());
    }
}