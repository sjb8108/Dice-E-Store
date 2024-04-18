package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Dice;
import com.estore.api.estoreapi.model.InventoryService;
import com.estore.api.estoreapi.model.Wishlist.Wishlist;
import com.estore.api.estoreapi.model.Wishlist.WishlistService;


/**
 * Handles the REST API requests for the Wishlist resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Lara Toklar
 */
@RestController
@RequestMapping("wishlist")
public class WishlistController {
    
    private static final Logger LOG = Logger.getLogger(WishlistController.class.getName());
    private WishlistService wishlistService;
    private InventoryService inventoryService;

    /**
     * Constructor for the WishlistController
     * @param wishlistService service for the wishlist
     * @param inventoryService service for the inventory
     */
    public WishlistController(WishlistService wishlistService, InventoryService inventoryService) {
        this.wishlistService = wishlistService;
        this.inventoryService = inventoryService;
    }

    /**
     * Adds a die to the wishlist
     * @param id the id of the die to add
     * @return the updated wishlist
     */
    @PostMapping("/{id}")
    public ResponseEntity<Wishlist> addDieToList(@PathVariable int id) {
        LOG.info("POST /wishlist/" + id);
      
        Dice die = inventoryService.getDie(id);
        if (die != null){
            try {
                Wishlist list = wishlistService.loadUserList();

                try {
                    wishlistService.addDieToList(list, die);

                } catch (IllegalArgumentException e) {
                    LOG.log(Level.SEVERE, "Error adding dice to wishlist: " + e.getMessage(), e);
                    // message error, cant add "Dice is already in wishlist" before the HTTP status
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<Wishlist>(list, HttpStatus.OK);

            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Error adding dice to wishlist", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Removes a die from the wishlist
     * @param id the id of the die to remove
     * @return the updated wishlist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Wishlist> deleteDieFromList(@PathVariable int id) {
        LOG.info("DELETE /wishlist/" + id);
        
        Dice die = inventoryService.getDie(id);
        if (die != null){
            try {
                Wishlist list = wishlistService.loadUserList();
                wishlistService.removeDiceFromList(list, die);
                return new ResponseEntity<Wishlist>(list, HttpStatus.OK);

            } catch (IllegalArgumentException e) {
                LOG.log(Level.SEVERE, "Dice was not found in wishlist", e);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            } catch (IOException e) {
                LOG.log(Level.SEVERE, "Error removing dice from wishlist", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets the wishlist
     * @return the wishlist
     */
    @GetMapping("")
    public ResponseEntity<Wishlist> getList() {
        LOG.info("GET /wishlist");
        Wishlist list = wishlistService.loadUserList();
        return new ResponseEntity<Wishlist>(list, HttpStatus.OK);
    }

    /**
     * Clears the wishlist
     * @return the updated wishlist
     */
    @DeleteMapping("")
    public ResponseEntity<Wishlist> clearWishlist() {
        LOG.info("DELETE /wishlist");
        
        try {
            Wishlist list = wishlistService.loadUserList();
            wishlistService.clearAllDiceFromList(list);
            return new ResponseEntity<Wishlist>(list, HttpStatus.OK);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error clearing wishlist", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
