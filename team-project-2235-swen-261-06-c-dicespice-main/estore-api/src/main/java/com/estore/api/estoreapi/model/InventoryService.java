package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.estore.api.estoreapi.persistence.Dice.DiceFileDAO;

/**
 * Represents the inventory of dice products that are available for purchase
 * @author Mark Luskiewicz
 */
@Service
public class InventoryService {
    private Map<Dice, Integer> diceMap;
    private DiceFileDAO diceDAO;
    private static int nextID;

    /**
     * Initializes the inventory service
     */
    public InventoryService(DiceFileDAO diceDAO) {
        this.diceDAO = diceDAO;
        diceMap = diceDAO.loadDice();
        nextID = diceMap.keySet().stream()
                    .mapToInt(Dice::getId)
                    .max()
                    .orElse(0) + 1;
    }

    /**
     * Gets the list of dice products
     * @return the list of dice products
     */
    public List<Map<String, Object>> getDice() {
        List<Map<String, Object>> diceData = new ArrayList<>();
        for (Map.Entry<Dice, Integer> entry : diceMap.entrySet()) {
            Dice dice = entry.getKey();
            int quantity = entry.getValue();
            Map<String, Object> data = new HashMap<>();
            data.put("id", dice.getId());
            data.put("color", dice.getColor());
            data.put("sides", dice.getSides());
            data.put("price", dice.getPrice());
            data.put("quantity", quantity);
            diceData.add(data);
        }
        return diceData;
    }

    /**
     * Gets a dice product by its id
     * @param id the id of the dice product
     * @return the dice product
     */
    public Dice getDie(int id) {
        for (Dice die : diceMap.keySet()) {
            if (die.getId() == id) {
                return die;
            }
        }
        return null;
    }

    /**
     * Adds a dice product to the inventory
     * @param dice the dice product to add
     */
    public void addDie(Dice dice, int quantity) {
        Dice existingDice = findDiceByType(dice);
        if (existingDice != null) {
            // If a dice of the same type already exists, increment its quantity
            diceMap.put(existingDice, diceMap.get(existingDice) + quantity);
        } else {
            // If no dice of the same type exists, add a new dice to the diceMap
            dice.setId(nextID++);
            diceMap.put(dice, quantity);
        }
        diceDAO.saveDice(diceMap);
    }
    
    /**
     * Finds a dice product in the inventory by its type
     * @param dice the dice product to find
     * @return the dice product if found, null otherwise
     */
    private Dice findDiceByType(Dice dice) {
        for (Dice existingDice : diceMap.keySet()) {
            if (existingDice.equals(dice)) {
                return existingDice;
            }
        }
        return null;
    }

    /**
     * Deletes a single dice product from the inventory
     * @param id the id of the dice product to delete
     */
    public void deleteDie(int id) {
        Dice diceToRemove = null;
        for (Dice die : diceMap.keySet()) {
            if (die.getId() == id) {
                diceToRemove = die;
                break;
            }
        }
    
        if (diceToRemove != null) {
            diceMap.remove(diceToRemove);
            diceDAO.saveDice(diceMap);
        } else {
            throw new IllegalArgumentException("Dice with id " + id + " not found");
        }
    }

    /**
     * Edits a dice product in the inventory by replacing it with a new dice product
     * with the same id
     * @param newDice the new dice product data
     * @throws IllegalArgumentException if the dice product is not found
     */
    public void updateDie(Dice newDice) throws IllegalArgumentException {
        int id = newDice.getId();
        Dice diceToUpdate = null;
        for (Dice die : diceMap.keySet()) {
            if (die.getId() == id) {
                diceToUpdate = die;
                break;
            }
        }
    
        if (diceToUpdate != null) {
            int quantity = diceMap.get(diceToUpdate);
            diceMap.remove(diceToUpdate);
            newDice.setId(id);
            diceMap.put(newDice, quantity);
            diceDAO.saveDice(diceMap);
        } else {
            throw new IllegalArgumentException("Dice with id " + id + " not found");
        }
    }

    /**
     * Searches for dice products by a substring in their color and exact side count
     * @param colorSubstring the substring to search for in the color of the dice products
     * @param quantity the exact side count of the dice products
     * @return the list of dice products that match the search criteria
     */
    public List<Dice> searchDice(String term) {
        if (term == null || term.isEmpty()) {
            return new ArrayList<>(diceMap.keySet());
        }
        return diceMap.keySet().stream()
                    .filter(dice -> dice.getColor().toLowerCase().contains(term.toLowerCase()) || String.valueOf(dice.getSides()).equals(term))
                    .collect(Collectors.toList());
    }

    /**
     * Gets the quantity of a dice product in the inventory
     * @param id the id of the dice product
     * @return the quantity of the dice product
     */
    public int getQuantity(int id) {
        for (Map.Entry<Dice, Integer> entry : diceMap.entrySet()) {
            if (entry.getKey().getId() == id) {
                return entry.getValue();
            }
        }
        return 0;
    }

    /**
     * Sets the quantity of a dice product in the inventory
     * @param id the id of the dice product
     * @param quantity the new quantity of the dice product
     * @return the quantity of the dice product
     */
    public void updateQuantity(int id, int quantity) {
        Dice diceToUpdate = getDie(id);
        if (diceToUpdate != null) {
            diceMap.put(diceToUpdate, quantity);
            diceDAO.saveDice(diceMap);
        } else {
            throw new IllegalArgumentException("Dice with id " + id + " not found");
        }
    }
}
