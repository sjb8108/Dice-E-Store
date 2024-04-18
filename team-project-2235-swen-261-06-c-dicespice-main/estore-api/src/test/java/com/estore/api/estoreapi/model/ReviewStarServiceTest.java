package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.estore.api.estoreapi.persistence.ReviewStarFileDAO;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.mock;

/**
 * Tests the ReviewStarService class
 * @author Nicholas Gouldin
 */
public class ReviewStarServiceTest {
    private ReviewStarService reviewStarService;

    /**
     * Before each test, create a new ReviewStarService object and inject
     * a mock ReviewStarFileDAO
     */
    @BeforeEach
    public void setup(){
            ReviewStarFileDAO mockReviewStarDAO = mock(ReviewStarFileDAO.class);
            reviewStarService = new ReviewStarService(mockReviewStarDAO);
    }

    @Test
    public void testAddReviewStarDie(){
        reviewStarService.addReviewStarDie(1,Double.valueOf(3));
        assertEquals(1,reviewStarService.getReviewStarDice().size());
    }

    @Test
    public void testDeleteReviewStar(){
        reviewStarService.addReviewStarDie(1,Double.valueOf(3));
        reviewStarService.deleteReviewStar(1,Double.valueOf(3));
        assertEquals(0,reviewStarService.getReviewStarDice().get(1).getReviewStarList().size());
    }

    @Test
    public void testGetReviewStarDice(){
        reviewStarService.addReviewStarDie(1,Double.valueOf(3));
        Map<Integer, ReviewStar> reviewStarData = reviewStarService.getReviewStarDice();
        assertEquals(Double.valueOf(3),reviewStarData.get(1).getReviewStarList().get(0));
    }

}
