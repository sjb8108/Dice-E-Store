package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Dice;
import com.estore.api.estoreapi.model.InventoryService;

/**
 * Handles the REST API requests for the Inventory resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Scott Bullock & Mark Luskiewicz
 */

@RestController
@RequestMapping("inventory")
public class InventoryController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private final InventoryService inventoryService;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param InventoryService The service that handles the inventory
     * This dependency is injected by the Spring Framework
     */
    public InventoryController(InventoryService InventoryService) {
        this.inventoryService = InventoryService;
    }

    /**
     * Responds to the GET request for a {@linkplain Dice die} for the given id
     * 
     * @param id The id used to locate the {@link Dice die}
     * 
     * @return ResponseEntity with {@link Dice die} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Dice> getDie(@PathVariable int id) {
        LOG.info("GET /inventory/" + id);
        Dice die = inventoryService.getDie(id);
        if (die != null)
            return new ResponseEntity<Dice>(die, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Responds to the GET request for all {@linkplain Dice dice}
     * 
     * @return ResponseEntity with array of {@link Dice die} objects (may be empty) and
     * HTTP status of OK<br>
     */
    @GetMapping("")
    public ResponseEntity<List<Map<String, Object>>> getDice() {
        LOG.info("GET /inventory");
        List<Map<String, Object>> diceList = inventoryService.getDice();
        return new ResponseEntity<List<Map<String, Object>>>(diceList, HttpStatus.OK);
    }

    /**
     * Responds to the GET request for all {@linkplain Dice dice} whose color or side count
     * matches the text in term
     * 
     * @param term The text parameter which contains the text used to find the {@link Dice dice}
     * 
     * @return ResponseEntity with array of {@link Dice die} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * <p>
     * GET http://localhost:8080/dice/?term=ma
     */
    @GetMapping("/")
    public ResponseEntity<List<Dice>> searchDice(@RequestParam(required = false) String term) {
        LOG.info("GET /inventory/?term="+term);
        
        List<Dice> diceList = inventoryService.searchDice(term);
        if (diceList == null) {
            diceList = new ArrayList<>(); // Return an empty list if the search result is null
        }
        return new ResponseEntity<List<Dice>>(diceList, HttpStatus.OK);
    }

    /**
     * Creates a {@linkplain Dice die} with the provided dice object
     * 
     * @param dice - The {@link Dice die} to create
     * 
     * @return ResponseEntity with created {@link Dice dice} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Dice dice} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/add/{quantity}")
    public ResponseEntity<Dice> addDie(@RequestBody Dice die, @PathVariable int quantity) {
        LOG.info("POST /inventory/add " + quantity);
        inventoryService.addDie(die, quantity);
        return new ResponseEntity<Dice>(die, HttpStatus.CREATED);
    }

    /**
     * Updates the {@linkplain Dice dice} with the provided {@linkplain Dice dice} object, if it exists
     * 
     * @param dice The {@link Dice dice} to update
     * 
     * @return ResponseEntity with updated {@link Dice dice} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     */
    @PutMapping("")
    public ResponseEntity<Dice> updateDie(@RequestBody Dice die) {
        LOG.info("PUT /inventory" + die);

        try {
            inventoryService.updateDie(die);
            return new ResponseEntity<Dice>(die, HttpStatus.OK);
        } 
        catch(IllegalArgumentException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a {@linkplain Dice dice} with the given id
     * 
     * @param id The id of the {@link Dice dice} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Dice> deleteDie(@PathVariable int id) {
        LOG.info("DELETE /inventory/" + id);

        try {
            inventoryService.deleteDie(id);
            return new ResponseEntity<>(HttpStatus.OK);
          
        } 
        catch(IllegalArgumentException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets the quantity of a dice product in the inventory
     * @param id the id of the dice product
     * @return the quantity of the dice product
     */
    @GetMapping("/quantity/{id}")
    public ResponseEntity<Integer> getQuantity(@PathVariable int id) {
        LOG.info("GET /inventory/quantity/" + id);
        int quantity = inventoryService.getQuantity(id);
        return new ResponseEntity<Integer>(quantity, HttpStatus.OK);
    }

    /**
     * Sets the quantity of a dice product in the inventory
     * @param id the id of the dice product
     * @param quantity the quantity to set
     * @return the quantity of the dice product
     */
    @PutMapping("/quantity/{id}/{quantity}")
    public ResponseEntity<Integer> updateQuantity(@PathVariable int id, @PathVariable int quantity) {
        LOG.info("PUT /inventory/quantity/" + id + "/" + quantity);
        try {
            inventoryService.updateQuantity(id, quantity);
        } catch (IllegalArgumentException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Integer>(quantity, HttpStatus.OK);
    }

}
