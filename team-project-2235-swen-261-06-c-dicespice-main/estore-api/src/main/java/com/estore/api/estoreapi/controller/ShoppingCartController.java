package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Dice;
import com.estore.api.estoreapi.model.InventoryService;
import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCart;
import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCartService;


/**
 * Handles the REST API requests for the ShoppingCart resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Scott Bullock & Mark Luskiewicz
 */
@RestController
@RequestMapping("shoppingcart")
public class ShoppingCartController {
    private static final Logger LOG = Logger.getLogger(ShoppingCartController.class.getName());
    private ShoppingCartService shoppingCartService;
    private InventoryService inventoryService;

    /**
     * Constructor for the ShoppingCartController
     * @param shoppingCartService service for the shopping cart
     * @param inventoryService service for the inventory
     */
    public ShoppingCartController(ShoppingCartService shoppingCartService, InventoryService inventoryService) {
        this.shoppingCartService = shoppingCartService;
        this.inventoryService = inventoryService;
    }

    /**
     * Adds a die to the shopping cart
     * @param id the id of the die to add
     * @return the updated shopping cart
     */
    @PostMapping("/{id}")
    public ResponseEntity<ShoppingCart> addDieToCart(@PathVariable int id) {
        LOG.info("POST /shoppingcart/" + id);
      
        Dice die = inventoryService.getDie(id);
        if (die != null){
            try {
                ShoppingCart cart = shoppingCartService.loadUserCart();
                try {
                    shoppingCartService.addDieToCart(cart, die);
                } catch (IllegalArgumentException e) {
                    LOG.log(Level.SEVERE, "Error adding dice to shopping cart: " + e.getMessage(), e);
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<ShoppingCart>(cart, HttpStatus.OK);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Error adding dice to shopping cart", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Increments the quantity of a die in the shopping cart
     * @param id the id of the die to increment
     * @return the updated shopping cart
     */
    @PutMapping("decrement/{id}")
    public ResponseEntity<ShoppingCart> decrementShoppingCart(@PathVariable int id) {
        LOG.info("PUT /shoppingcart/" + id);
        
        Dice die = inventoryService.getDie(id);
        if (die != null){
            try {
                ShoppingCart cart = shoppingCartService.loadUserCart();
                shoppingCartService.decrementDice(cart, die);
                return new ResponseEntity<ShoppingCart>(cart, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                LOG.log(Level.SEVERE, "Dice was not found in cart", e);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (IOException e) {
                LOG.log(Level.SEVERE, "Error decrementing dice in shopping cart", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Decrements the quantity of a die in the shopping cart
     * @param id the id of the die to decrement
     * @return the updated shopping cart
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ShoppingCart> deleteDieFromCart(@PathVariable int id) {
        LOG.info("DELETE /shoppingcart/" + id);
        
        Dice die = inventoryService.getDie(id);
        if (die != null){
            try {
                ShoppingCart cart = shoppingCartService.loadUserCart();
                shoppingCartService.removeDiceFromCart(cart, die);
                return new ResponseEntity<ShoppingCart>(cart, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                LOG.log(Level.SEVERE, "Dice was not found in cart", e);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (IOException e) {
                LOG.log(Level.SEVERE, "Error removing dice from shopping cart", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves the shopping cart
     * @return the shopping cart
     */
    @GetMapping("")
    public ResponseEntity<ShoppingCart> getCart() {
        LOG.info("GET /shoppingcart");
        ShoppingCart cart = shoppingCartService.loadUserCart();
        return new ResponseEntity<ShoppingCart>(cart, HttpStatus.OK);
    }

     /**
     * Clears the shopping cart
     * @return the updated shopping cart
     */
    @PutMapping("/clear")
    public ResponseEntity<ShoppingCart> clearCart() {
        LOG.info("PUT /shoppingcart/clear");
        try {
            ShoppingCart cart = shoppingCartService.loadUserCart();
            shoppingCartService.clearCart(cart);
            cart = shoppingCartService.loadUserCart(); // Reload the cart after it has been cleared
            return new ResponseEntity<ShoppingCart>(cart, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error loading or clearing shopping cart", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error removing dice from shopping cart", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
