package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.estore.api.estoreapi.model.Review;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests the ReviewFileDAO class
 * @author Nicholas Gouldin
 */
public class ReviewFileDAOTest {
    private ReviewFileDAO reviewFileDAO;
    /**
     * Before each test, create a new ReviewFileDAO object and create
     * a temporary file to store reviews
     */
    @BeforeEach
    public void setup() throws IOException {
        File tempFile = File.createTempFile("test",".txt");
        tempFile.deleteOnExit();
        reviewFileDAO = new ReviewFileDAO(tempFile.getAbsolutePath());

    }

    @Test
    public void testLoadDiceReviews(){
        // Prepare test data
        Review review = new Review();
        review.addReview("Test string");
        Map<Integer, Review> expectedReviewMap = new HashMap<>();
        expectedReviewMap.put(1,review);

        reviewFileDAO.saveReviewsDice(expectedReviewMap);

        Map<Integer,Review> actualReviewMap = reviewFileDAO.loadDiceReviews();

        assertEquals(expectedReviewMap.get(1).getReviews(),actualReviewMap.get(1).getReviews());
    }

    @Test
    public void testSaveReviewsDice(){
        // Prepare test data
        Review review = new Review();
        review.addReview("Test string");
        Map<Integer, Review> reviewMap = new HashMap<>();
        reviewMap.put(1,review);

        reviewFileDAO.saveReviewsDice(reviewMap);

        Map<Integer,Review> loadedReviewMap = reviewFileDAO.loadDiceReviews();
        assertEquals(reviewMap.get(1).getReviews(),loadedReviewMap.get(1).getReviews());
    }
}
