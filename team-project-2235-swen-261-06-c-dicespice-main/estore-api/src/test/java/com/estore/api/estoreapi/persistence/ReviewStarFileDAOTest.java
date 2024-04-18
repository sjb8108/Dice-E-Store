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

import com.estore.api.estoreapi.model.ReviewStar;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests the ReviewStarFileDAO class
 * @author Nicholas Gouldin
 */
public class ReviewStarFileDAOTest {
    private ReviewStarFileDAO reviewStarFileDAO;
    
    /**
     * Before each test, create a new ReviewStarFileDAO object and create
     * a temporary file to store reviews
     */
    @BeforeEach
    public void setup() throws IOException {
        File tempFile = File.createTempFile("test",".txt");
        tempFile.deleteOnExit();
        reviewStarFileDAO = new ReviewStarFileDAO(tempFile.getAbsolutePath());

    }

    @Test
    public void testLoadDiceReviews(){
        // Prepare test data
        ReviewStar reviewStar = new ReviewStar();
        reviewStar.addReviewStar(3);
        Map<Integer, ReviewStar> expectedStarMap = new HashMap<>();
        expectedStarMap.put(1,reviewStar);

        reviewStarFileDAO.saveReviewStarsDice(expectedStarMap);

        Map<Integer, ReviewStar> actualStarMap = reviewStarFileDAO.loadDiceReviewStars();

        assertEquals(expectedStarMap.get(1).getReviewStarList(),actualStarMap.get(1).getReviewStarList());
    }

    @Test
    public void testSaveReviewsDice(){
        // Prepare test data
        ReviewStar reviewStar = new ReviewStar();
        reviewStar.addReviewStar(3);
        Map<Integer, ReviewStar> reviewStarMap = new HashMap<>();
        reviewStarMap.put(1,reviewStar);

        reviewStarFileDAO.saveReviewStarsDice(reviewStarMap);

        Map<Integer, ReviewStar> loadedStarMap = reviewStarFileDAO.loadDiceReviewStars();

        assertEquals(reviewStarMap.get(1).getReviewStarList(),loadedStarMap.get(1).getReviewStarList());
    }
}

