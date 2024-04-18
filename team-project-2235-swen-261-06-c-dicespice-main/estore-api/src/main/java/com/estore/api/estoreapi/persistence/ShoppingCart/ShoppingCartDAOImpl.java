package com.estore.api.estoreapi.persistence.ShoppingCart;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCart;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents the data access object for the shopping cart
 * @author Mark Luskiewicz
 */
@Component
public class ShoppingCartDAOImpl implements ShoppingCartDAO {
    private ObjectMapper mapper;

    public ShoppingCartDAOImpl() {
        this.mapper = new ObjectMapper();
    }

    /*
     * {@inheritDoc}
     */
    public void saveShoppingCart(ShoppingCart cart, String username) throws IOException {
        File directory = new File("CartData");
        if (!directory.exists()){
            directory.mkdir();
        }
        File file = new File("CartData/" + username + ".json");
        mapper.writeValue(file, cart);
    }

    /*
     * {@inheritDoc}
     */
    public ShoppingCart loadShoppingCart(String username) {
        ShoppingCart cart = null;
        String userFilePath = "CartData/" + username + ".json";
        File userFile = new File(userFilePath);
        if (!userFile.exists()) {
            createShoppingCart(username);
        }
        try {
            cart = mapper.readValue(userFile, ShoppingCart.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cart;
    }

    /*
     * {@inheritDoc}
     */
    public void createShoppingCart(String username) {
        ShoppingCart cart = new ShoppingCart();
        try {
            saveShoppingCart(cart, username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}