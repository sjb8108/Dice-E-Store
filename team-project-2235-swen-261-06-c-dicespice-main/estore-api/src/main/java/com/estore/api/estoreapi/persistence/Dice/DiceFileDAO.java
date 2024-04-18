package com.estore.api.estoreapi.persistence.Dice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Dice;

/**
 * Implements the functionality for JSON file-based peristance for Dice
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Scott Bullock & Mark Luskiewicz
 */
@Component
public class DiceFileDAO {
    private static final String DATA_FILE = "data/dice.json";
    private ObjectMapper objectMapper;

    // USED FOR TESTING
    private String dataFilePath;

    /**
     * Initializes the DiceFileDAO with a default data file path
     */
    public DiceFileDAO() {
        objectMapper = new ObjectMapper();
        this.dataFilePath = DATA_FILE;
    }

    /**
     * Initializes the DiceFileDAO with a data file path for testing
     * @param dataFilePath the data file path
     */
    public DiceFileDAO(String dataFilePath) {
        objectMapper = new ObjectMapper();
        this.dataFilePath = dataFilePath;
    }

    /*
     * {@inheritDoc}
     */
    public Map<Dice, Integer> loadDice() {
        Map<Dice, Integer> diceMap = new HashMap<>();
        try {
            List<Map<String, Object>> diceData = objectMapper.readValue(new File(dataFilePath), new TypeReference<List<Map<String, Object>>>(){});
            for (Map<String, Object> data : diceData) {
                int id = ((Number) data.get("id")).intValue();
                String color = (String) data.get("color");
                int sides = ((Number) data.get("sides")).intValue();
                double price = ((Number) data.get("price")).doubleValue();
                Dice dice = new Dice(id, color, sides, price);
                int quantity = ((Number) data.get("quantity")).intValue();
                diceMap.put(dice, quantity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return diceMap;
    }

    /*
     * {@inheritDoc}
     */
    public void saveDice(Map<Dice, Integer> diceMap) {
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
        try {
            objectMapper.writeValue(new File(dataFilePath), diceData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}