package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.estore.api.estoreapi.persistence.ReviewFileDAO;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.mock;

/**
 * Tests the ReviewService class
 * @author Nicholas Gouldin
 */
public class ReviewServiceTest {
    private ReviewService reviewService;

    /**
     * Before each test, create a new ReviewService object and inject
     * a mock ReviewFileDAO
     */
    @BeforeEach
    public void setup(){
            ReviewFileDAO mockReviewDAO = mock(ReviewFileDAO.class);
            reviewService = new ReviewService(mockReviewDAO);
    }

    @Test
    public void testAddReviewDie(){
        reviewService.addReviewDie(1,"Test review 1");
        assertEquals(1,reviewService.getReviewDice().size());
    }

    @Test
    public void testDeleteReview(){
        reviewService.addReviewDie(1,"Test review 1");
        reviewService.deleteReview(1,"Test review 1");
        assertEquals(0,reviewService.getReviewDice().get(1).getReviews().size());
    }

    @Test
    public void testGetReviewDice(){
        reviewService.addReviewDie(1,"Test review 1");
        Map<Integer, Review> reviewData = reviewService.getReviewDice();
        assertEquals("Test review 1",reviewData.get(1).getReviews().get(0));
    }

}
