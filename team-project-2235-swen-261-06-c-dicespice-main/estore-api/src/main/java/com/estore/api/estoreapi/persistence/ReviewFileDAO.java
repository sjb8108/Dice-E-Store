package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.estore.api.estoreapi.model.Review;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * Implements the functionality for JSON file-based peristance for Review
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Scott Bullock
 */

@Component
public class ReviewFileDAO {
    private static final String DATA_FILE = "data/diceReviews.json";
    private ObjectMapper objectMapper;
    private String dataFilePath;

    /**
     * Initializes the ReviewFileDAO with a default data file path
     */
    public ReviewFileDAO() {
        objectMapper = new ObjectMapper();
        this.dataFilePath = DATA_FILE;
    }

    /**
     * Initializes the ReviewFileDAO with a data file path for testing
     * @param dataFilePath the data file path
     */
    public ReviewFileDAO(String dataFilePath) {
        objectMapper = new ObjectMapper();
        this.dataFilePath = dataFilePath;
    }

    /**
     * Loads the reviews from the data source
     * @return the map of the reviews
     */

    public Map<Integer, Review> loadDiceReviews() {
        Map<Integer, Review> diceReviewMap = new HashMap<>();
        try {
            List<Map<String, Object>> diceReviewData = objectMapper.readValue(new File(dataFilePath), new TypeReference<List<Map<String, Object>>>(){});
            for (Map<String, Object> data : diceReviewData) {
                int id = ((Number) data.get("id")).intValue();
                HashMap<String, Object> reviews = ((HashMap<String, Object>) data.get("reviews"));
                ArrayList<String> reviewList = (ArrayList<String>) reviews.get("reviews");
                Review review = new Review();
                for(String text : reviewList){
                    review.addReview(text);
                }
                diceReviewMap.put(id, review);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return diceReviewMap;
    }

    /**
     * Saves the reviews to the data source
     * @param diceList the map of the reviews
     */

    public void saveReviewsDice(Map<Integer, Review> diceReviewMap) {
        List<Map<String, Object>> diceReviewData = new ArrayList<>();
        for (Entry<Integer, Review> entry : diceReviewMap.entrySet()) {
            int id = entry.getKey();
            Review reviews = entry.getValue();
            Map<String, Object> data = new HashMap<>();
            data.put("id", id);
            data.put("reviews", reviews);
            diceReviewData.add(data);
        }
        try {
            objectMapper.writeValue(new File(dataFilePath), diceReviewData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
