package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Dice;
import com.estore.api.estoreapi.model.InventoryService;

/**
 * Tests the InventoryController class
 * @author Mark Luskiewicz and Lara Toklar
 */
@Tag("Controller-tier")
public class InventoryControllerTest {
    private InventoryController inventoryController;
    private InventoryService mockInventoryService;

    /**
     * Before each test, create a new DiceController object and inject
     * a mock Dice DAO
     */
    @BeforeEach
    public void setupDieController() {
        mockInventoryService = mock(InventoryService.class);
        inventoryController = new InventoryController(mockInventoryService);
    }

    @Test
    public void testGetDie() {
        Dice die = new Dice(1, "red", 6, 4.99);

        when(mockInventoryService.getDie(die.getId())).thenReturn(die);

        Dice result = inventoryController.getDie(die.getId()).getBody();
        assertEquals(die, result);
    }

    @Test
    public void testGetDieNotFound() {
        Dice die = new Dice(1, "red", 6, 4.99);

        when(mockInventoryService.getDie(die.getId())).thenReturn(null);

        Dice result = inventoryController.getDie(die.getId()).getBody();
        assertEquals(null, result);
    }

    @Test
    public void testGetDice() {
        Dice die = new Dice(1, "red", 6, 4.99);
        List<Dice> diceList = Arrays.asList(die);

        List<Map<String, Object>> diceMapList = diceList.stream()
                .map(d -> {
                    Map<String, Object> diceMap = new HashMap<>();
                    diceMap.put("id", d.getId());
                    diceMap.put("color", d.getColor());
                    diceMap.put("sides", d.getSides());
                    diceMap.put("price", d.getPrice());
                    return diceMap;
                })
                .collect(Collectors.toList());

        when(mockInventoryService.getDice()).thenReturn(diceMapList);

        List<Map<String, Object>> result = inventoryController.getDice().getBody();
        assertEquals(1, result.size());
    }

    @Test
    public void testSearchDice() {
        Dice die = new Dice(1, "red", 6, 4.99);
        List<Dice> diceList = Arrays.asList(die);

        // Set up the mock to return the diceList when searchDice is called
        when(mockInventoryService.searchDice("red")).thenReturn(diceList);

        List<Dice> result = inventoryController.searchDice("red").getBody();
        assertEquals(1, result.size());
    }

    @Test
    public void testAddDie() {
        Dice die = new Dice(1, "red", 6, 4.99);

        doNothing().when(mockInventoryService).addDie(die, 2);

        Dice result = inventoryController.addDie(die, 2).getBody();
        assertEquals(die, result);
    }

    @Test
    public void updateDie() {
        Dice die = new Dice(1, "red", 6, 4.99);
        Dice newDie = new Dice(1, "blue", 6, 4.99);

        doNothing().when(mockInventoryService).addDie(die, 1);
        doNothing().when(mockInventoryService).updateDie(newDie);

        Dice result = inventoryController.updateDie(newDie).getBody();
        assertEquals(newDie, result);
    }

    @Test
    public void testUpdateDieNotFound() {
        Dice die = new Dice(1, "red", 6, 4.99);
        Dice newDie = new Dice(2, "blue", 6, 4.99);

        doNothing().when(mockInventoryService).addDie(die, 1);
        doThrow(new IllegalArgumentException()).when(mockInventoryService).updateDie(newDie);

        ResponseEntity<Dice> response = inventoryController.updateDie(newDie);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteDie() {
        Dice die = new Dice(1, "red", 6, 4.99);

        doNothing().when(mockInventoryService).addDie(die, 1);
        doNothing().when(mockInventoryService).deleteDie(die.getId());

        Dice result = inventoryController.deleteDie(die.getId()).getBody();
        assertEquals(null, result);
    }

    @Test
    public void testDeleteDieNotFound() {
        Dice die = new Dice(1, "red", 6, 4.99);

        doNothing().when(mockInventoryService).addDie(die, 1);
        doThrow(new IllegalArgumentException()).when(mockInventoryService).deleteDie(die.getId());

        ResponseEntity<Dice> response = inventoryController.deleteDie(die.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetQuantitySingle() {
        Dice die = new Dice(1, "red", 6, 4.99);
        
        doNothing().when(mockInventoryService).addDie(die, 1);
        when(mockInventoryService.getQuantity(die.getId())).thenReturn(1);

        int result = inventoryController.getQuantity(die.getId()).getBody();
        assertEquals(1, result);
    }

    @Test
    public void testGetQuantityMultiple() {
        Dice die = new Dice(1, "red", 6, 4.99);
        Dice die2 = new Dice(2, "red", 6, 4.99);

        doNothing().when(mockInventoryService).addDie(die, 1);
        doNothing().when(mockInventoryService).addDie(die2, 1);
        
        when(mockInventoryService.getQuantity(die.getId())).thenReturn(2);
        when(mockInventoryService.getQuantity(die2.getId())).thenReturn(2);

        int result = inventoryController.getQuantity(die.getId()).getBody();
        int result2 = inventoryController.getQuantity(die2.getId()).getBody();
        assertEquals(2, result);
        assertEquals(2, result2);
    }

    @Test
    public void testUpdateQuantity() {
        // Arrange
        int id = 1;
        int quantity = 10;
        InventoryService inventoryService = mock(InventoryService.class);
        InventoryController inventoryController = new InventoryController(inventoryService);

        doNothing().when(inventoryService).updateQuantity(id, quantity);

        // Act
        ResponseEntity<Integer> response = inventoryController.updateQuantity(id, quantity);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quantity, response.getBody());
        verify(inventoryService, times(1)).updateQuantity(id, quantity);
    }

    @Test
    public void testUpdateQuantityThrowsIllegalArgumentException() {
        // Arrange
        InventoryService mockInventoryService = mock(InventoryService.class);
        InventoryController inventoryController = new InventoryController(mockInventoryService);
        int id = 1;
        int quantity = -1; // Invalid quantity
        doThrow(IllegalArgumentException.class).when(mockInventoryService).updateQuantity(id, quantity);

        // Act
        ResponseEntity<Integer> response = inventoryController.updateQuantity(id, quantity);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchDiceNoDiceListExists() {
        // Arrange
        String term = "ma";
        InventoryService mockInventoryService = mock(InventoryService.class);
        when(mockInventoryService.searchDice(term)).thenReturn(null);

        InventoryController inventoryController = new InventoryController(mockInventoryService);

        // Act
        ResponseEntity<List<Dice>> response = inventoryController.searchDice(term);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
}