package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.estore.api.estoreapi.model.Dice;
import com.estore.api.estoreapi.persistence.Dice.DiceFileDAO;


public class DiceFileDAOTest {
    private String filename;
    private DiceFileDAO diceFileDAO;

    @BeforeEach
    public void setup() throws IOException { // Creating a temp file can throw IOException
        File tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();
        diceFileDAO = new DiceFileDAO(tempFile.getAbsolutePath());
    }

    @Test
    public void testLoadDice() {
        // Prepare test data
        Map<Dice, Integer> expectedDiceMap = new HashMap<>();
        expectedDiceMap.put(new Dice(1, "red", 6, 4.99), 2);
        expectedDiceMap.put(new Dice(2, "green", 8, 5.99), 3);

        // Write the expected data to the temporary file
        diceFileDAO.saveDice(expectedDiceMap);

        // Load dice from file
        Map<Dice, Integer> actualDiceMap = diceFileDAO.loadDice();

        // Assert the loaded dice map is equal to the expected dice map
        assertEquals(expectedDiceMap, actualDiceMap);
    }

    @Test
    public void testSaveDice(@TempDir File tempDir) throws IOException {
        // Prepare test data
        Map<Dice, Integer> diceMap = new HashMap<>();
        diceMap.put(new Dice(1, "red", 6, 4.99), 2);
        diceMap.put(new Dice(2, "green", 8, 5.99), 3);

        // Save dice to file
        diceFileDAO.saveDice(diceMap);

        // Load dice from file
        Map<Dice, Integer> loadedDiceMap = diceFileDAO.loadDice();

        // Assert the loaded dice map is equal to the saved dice map
        assertEquals(diceMap, loadedDiceMap);
    }
    
}
