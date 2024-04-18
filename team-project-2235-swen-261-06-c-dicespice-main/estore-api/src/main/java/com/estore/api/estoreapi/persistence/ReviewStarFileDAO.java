package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.estore.api.estoreapi.model.ReviewStar;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

/**
 * Implements the functionality for JSON file-based peristance for ReviewStar
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Scott Bullock
 */
@Component
public class ReviewStarFileDAO {
    private static final String DATA_FILE = "data/diceReviewStar.json";
    private ObjectMapper objectMapper;
    private String dataFilePath;

    /**
     * Initializes the ReviewStarFileDAO with a default data file path
     */
    public ReviewStarFileDAO() {
        objectMapper = new ObjectMapper();
        this.dataFilePath = DATA_FILE;
    }

    /**
     * Initializes the ReviewStarFileDAO with a data file path for testing
     * @param dataFilePath the data file path
     */
    public ReviewStarFileDAO(String dataFilePath) {
        objectMapper = new ObjectMapper();
        this.dataFilePath = dataFilePath;
    }

    /**
     * Loads the review stars from the data source
     * @return the map of the reviews
     */

    public Map<Integer, ReviewStar> loadDiceReviewStars() {
        Map<Integer, ReviewStar> diceReviewStarMap = new HashMap<>();
        try {
            List<Map<String, Object>> diceReviewStarData = objectMapper.readValue(new File(dataFilePath), new TypeReference<List<Map<String, Object>>>(){});
            for (Map<String, Object> data : diceReviewStarData) {
                int id = ((Number) data.get("id")).intValue();
                HashMap<String, Object> reviewStars = ((HashMap<String, Object>) data.get("reviewStars"));
                ArrayList<Double> reviewStarList = (ArrayList<Double>) reviewStars.get("reviewStarList");
                ReviewStar reviewStar = new ReviewStar();
                for(Double rating : reviewStarList){
                    reviewStar.addReviewStar(rating);
                }
                diceReviewStarMap.put(id, reviewStar);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return diceReviewStarMap;
    }

    /**
     * Saves the review stars to the data source
     * @param diceList the map of the reviews
     */

    public void saveReviewStarsDice(Map<Integer, ReviewStar> diceReviewStarMap) {
        List<Map<String, Object>> diceReviewStarData = new ArrayList<>();
        for (Entry<Integer, ReviewStar> entry : diceReviewStarMap.entrySet()) {
            int id = entry.getKey();
            ReviewStar reviewStars = entry.getValue();
            Map<String, Object> data = new HashMap<>();
            data.put("id", id);
            data.put("reviewStars", reviewStars);
            diceReviewStarData.add(data);
        }
        try {
            objectMapper.writeValue(new File(dataFilePath), diceReviewStarData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
