package com.estore.api.estoreapi.controller;
import com.estore.api.estoreapi.model.Review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.ReviewService;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Tests the ReviewController class
 * @author Nicholas Gouldin
 */
public class ReviewControllerTest {
    private ReviewController reviewController;
    private ReviewService mockReviewService;

    /**
     * Before each test, create a new ReviewController object and inject
     * a mock ReviewService
     */
    @BeforeEach
    public void setup(){
        mockReviewService = mock(ReviewService.class);
        reviewController = new ReviewController(mockReviewService);
    }

    @Test
    public void testAddReviewToDice() {
        Review review = new Review();
        review.addReview("Test review 1");
        ArrayList<String> expectedReviews = review.getReviews();
        when(mockReviewService.getReviewDice()).thenReturn(new HashMap<Integer, Review>() {{
            put(1, review);
        }});
        ResponseEntity<ArrayList<String>> response = reviewController.addReviewToDice(1, "Test review 1");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedReviews, response.getBody());
    }
    @Test
    public void testDeleteReviewDie() {
        doNothing().when(mockReviewService).deleteReview(1, "Test Review 1");
        ResponseEntity<ArrayList<String>> response = reviewController.deleteReviewDie(1, "Test Review 1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteReviewDieNotFound() {
        doThrow(IllegalArgumentException.class).when(mockReviewService).deleteReview(1, "Test Review 1");
        ResponseEntity<ArrayList<String>> response = reviewController.deleteReviewDie(1, "Test Review 1");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetReviewDice() {
        // Arrange
        int reviewId = 1;
        Review review = new Review();
        review.addReview("Test review 1");
        ArrayList<String> expectedReviews = review.getReviews();

        ReviewService mockReviewService = mock(ReviewService.class);
        when(mockReviewService.reviewExists(reviewId)).thenReturn(true);
        when(mockReviewService.getReviewDice()).thenReturn(Collections.singletonMap(reviewId, review));

        ReviewController reviewController = new ReviewController(mockReviewService);

        // Act
        ResponseEntity<Object> response = reviewController.getReviewDice(reviewId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReviews, response.getBody());
    }

    @Test
    public void testGetReviewDiceNotFound() {
        when(mockReviewService.getReviewDice()).thenReturn(new HashMap<>());
        ResponseEntity<Object> response = reviewController.getReviewDice(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }



}
