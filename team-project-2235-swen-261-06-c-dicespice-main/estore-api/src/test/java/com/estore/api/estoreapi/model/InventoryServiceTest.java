package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.estore.api.estoreapi.persistence.Dice.DiceFileDAO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the InventoryService class
 * @author Mark Luskiewicz and Lara Toklar
 */
@Tag("Model-tier")
public class InventoryServiceTest {
    private InventoryService inventory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        DiceFileDAO mockDiceDAO = mock(DiceFileDAO.class);
        inventory = new InventoryService(mockDiceDAO);
    }

    @Test
    public void testAddDie() {
        Dice dice = new Dice(1, "green", 6, 4.99);
        inventory.addDie(dice, 1);
        assertEquals(1, inventory.getDice().size());
    }

    @Test
    public void testGetDie() {
        Dice dice = new Dice(1, "green", 6, 4.99);
        inventory.addDie(dice, 1);
        Dice retrievedDice = inventory.getDie(dice.getId());
        assertNotNull(retrievedDice);
        assertEquals(dice, retrievedDice);
    }

    @Test
    public void testDeleteDie() {
        Dice dice = new Dice(1, "green", 6, 4.99);
        inventory.addDie(dice, 1);
        inventory.deleteDie(dice.getId());
        assertEquals(0, inventory.getDice().size());
    }

    @Test
    public void testDeleteNonExistingDie() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventory.deleteDie(999);
        });

        String expectedMessage = "Dice with id 999 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testDeleteDieWithQuantityMoreThanOne() {
        Dice dice = new Dice(1, "green", 6, 4.99);
        inventory.addDie(dice, 2); // Add 2 dice to the inventory
        inventory.deleteDie(dice.getId()); // Delete one die
        assertEquals(0, inventory.getQuantity(dice.getId()));
    }

    @Test
    public void testUpdateDie() {
        Dice dice = new Dice(1, "green", 6, 4.99);
        inventory.addDie(dice, 1);
        Dice updatedDice = new Dice(dice.getId(), "blue", 12, 9.99);
        inventory.updateDie(updatedDice);
        Dice retrievedDice = inventory.getDie(dice.getId());
        assertNotNull(retrievedDice);
        assertEquals(updatedDice, retrievedDice);
    }

    @Test
    public void testUpdateDieNotFound() {
        Dice die = new Dice(1, "red", 6, 4.99);
        Dice updatedDice = new Dice(2, "blue", 6, 4.99); // Dice with ID 2 does not exist in diceMap
    
        inventory.addDie(die, 1);
    
        try {
            inventory.updateDie(updatedDice);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Dice with id 2 not found", e.getMessage());
        }
    }

    @Test
    public void testSearchDice() {
        Dice dice1 = new Dice(1, "red", 6, 4.99);
        Dice dice2 = new Dice(2, "green", 6, 4.99);
        Dice dice3 = new Dice(3, "red", 6, 4.99);
        inventory.addDie(dice1, 1);
        inventory.addDie(dice2, 1);
        inventory.addDie(dice3, 1);
        String searchTerm = "green";
        List<Dice> searchResults = inventory.searchDice(searchTerm);
        assertEquals(1, searchResults.size());
        assertTrue(searchResults.contains(dice2));
    }

    @Test
    public void testSearchDiceReturnsEmptyList() {
        // Arrange
        String searchTerm = "";
        List<Dice> expected = new ArrayList<>();

        // Act
        List<Dice> result = inventory.searchDice(searchTerm);

        // Assert
        assertEquals(expected, result, "searchDice should return an empty list when the search term is null or empty");
    }

    @Test
    public void testGetQuantity() {
        Dice dice = new Dice(1, "green", 6, 4.99);
        inventory.addDie(dice, 1);
        int quantity = inventory.getQuantity(dice.getId());
        assertEquals(1, quantity);
    }

    @Test
    public void testGetQuantityNotFound() {
        Dice dice = new Dice(1, "green", 6, 4.99);
        inventory.addDie(dice, 1);
        int quantity = inventory.getQuantity(2);
        assertEquals(0, quantity);
    }

    @Test
    public void testUpdateQuantity() {
        Dice dice = new Dice(1, "green", 6, 4.99);
        inventory.addDie(dice, 1);
        inventory.updateQuantity(dice.getId(), 2);
        int quantity = inventory.getQuantity(dice.getId());
        assertEquals(2, quantity);
    }

    @Test
    public void testUpdateQuantityNotFound() {
        Dice dice = new Dice(1, "green", 6, 4.99);
        inventory.addDie(dice, 1);
        try {
            inventory.updateQuantity(2, 2);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Dice with id 2 not found", e.getMessage());
        }
    }
}
