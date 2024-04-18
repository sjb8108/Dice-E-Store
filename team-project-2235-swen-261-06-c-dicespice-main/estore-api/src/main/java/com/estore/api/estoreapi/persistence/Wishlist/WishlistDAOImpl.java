package com.estore.api.estoreapi.persistence.Wishlist;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Wishlist.Wishlist;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation of the data access object for the wishlist
 * 
 * @author Lara Toklar
 */
@Component
public class WishlistDAOImpl implements WishlistDAO {

    private ObjectMapper mapper;

    public WishlistDAOImpl() {
        this.mapper = new ObjectMapper();
    }

    /**
     * Saves the wishlist to the file
     * @param list the wishlist to be saved
     * @param username the username of the user
     * @throws IOException thrown if there is an error saving the wishlist
     */
    public void saveWishlist(Wishlist list, String username) throws IOException {
        File directory = new File("WishlistData");
        if (!directory.exists()){
            directory.mkdir();
        }
        File file = new File("WishlistData/" + username + ".json");
        mapper.writeValue(file, list);
    }

    /**
     * Loads the wishlist from the file
     * @param username the username of the user
     * @return the wishlist loaded from the file
     */
    public Wishlist loadWishlist(String username) {
        Wishlist list = null;
        String userFilePath = "WishlistData/" + username + ".json";
        File userFile = new File(userFilePath);
        if (!userFile.exists()) {
            createWishlist(username);
        }
        try {
            list = mapper.readValue(userFile, Wishlist.class);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Creates a wishlist for the user
     * @param username the username of the user
     */
    public void createWishlist(String username) {
        Wishlist list = new Wishlist();
        try {
            saveWishlist(list, username);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}