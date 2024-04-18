package com.estore.api.estoreapi.persistence.Wishlist;

import java.io.IOException;
import com.estore.api.estoreapi.model.Wishlist.Wishlist;

/**
 * Represents the data access object for the wishlist
 * 
 * @author Lara Toklar
 */
public interface WishlistDAO {

    /**
     * Saves the wishlist to the file
     * @param list the wishlist to be saved
     */ 
    void saveWishlist(Wishlist list, String username) throws IOException;

    /**
     * Loads the wishlist from the file
     * @return the wishlist loaded from the file
     */
    Wishlist loadWishlist(String username);

    /**
     * Creates a wishlist for the user
     * @param username the username of the user
     */
    void createWishlist(String username);
}
