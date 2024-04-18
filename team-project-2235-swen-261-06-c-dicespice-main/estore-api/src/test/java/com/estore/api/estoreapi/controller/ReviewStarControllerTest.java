package com.estore.api.estoreapi.controller;
import com.estore.api.estoreapi.model.Review;
import com.estore.api.estoreapi.model.ReviewStar;

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
import com.estore.api.estoreapi.model.ReviewStarService;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
 * Tests the ReviewStarController class
 * @author Nicholas Gouldin
 */
public class ReviewStarControllerTest {
    private ReviewStarController reviewStarController;
    private ReviewStarService mockReviewStarService;

    /**
     * Before each test, create a new ReviewController object and inject
     * a mock ReviewStarService
     */
    @BeforeEach
    public void setup(){
        mockReviewStarService = mock(ReviewStarService.class);
        reviewStarController = new ReviewStarController(mockReviewStarService);
    }

    @Test
    public void testAddReviewToDice() {

        ReviewStar reviewStar = new ReviewStar();
        reviewStar.addReviewStar(Double.valueOf(3));
        Double expectedAverage = reviewStar.getStarRatingAverage();
        when(mockReviewStarService.getReviewStarDice()).thenReturn(new HashMap<Integer, ReviewStar>() {{
            put(1, reviewStar);
        }});
        ResponseEntity<Double> response = reviewStarController.addReviewToDice(1, Double.valueOf(3));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedAverage, response.getBody());
    }

    @Test
    public void testDeleteReviewStarDie() {
        doNothing().when(mockReviewStarService).deleteReviewStar(1, Double.valueOf(3));
        ResponseEntity<Double> response = reviewStarController.deleteReviewStarDie(1, Double.valueOf(3));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteReviewStarDieNotFound() {
        doThrow(IllegalArgumentException.class).when(mockReviewStarService).deleteReviewStar(1, Double.valueOf(3));
        ResponseEntity<Double> response = reviewStarController.deleteReviewStarDie(1, Double.valueOf(3));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    public void testGetReviewStarDice() {
        ReviewStar reviewStar = new ReviewStar();
        reviewStar.addReviewStar(Double.valueOf(3));
        Double expectedAverage = reviewStar.getStarRatingAverage();
        when(mockReviewStarService.getReviewStarDice()).thenReturn(new HashMap<Integer, ReviewStar>() {{
            put(1, reviewStar);
        }});
        ResponseEntity<Double> response = reviewStarController.getReviewStarDice(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAverage, response.getBody());
    }

    @Test
    public void testGetReviewStarDiceNotFound() {
        when(mockReviewStarService.getReviewStarDice()).thenReturn(new HashMap<>());
        ResponseEntity<Double> response = reviewStarController.getReviewStarDice(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    public void testGetAllReviewStarDice() {// Arrange
        ReviewStar reviewStar1 = new ReviewStar();
        reviewStar1.addReviewStar(Double.valueOf(3));
        ReviewStar reviewStar2 = new ReviewStar();
        reviewStar2.addReviewStar(Double.valueOf(4));

        Map<Integer, ReviewStar> expectedReviewStarMap = new HashMap<Integer, ReviewStar>() {{
            put(1, reviewStar1);
            put(2, reviewStar2);
        }};
        when(mockReviewStarService.getReviewStarDice()).thenReturn(expectedReviewStarMap);
        ResponseEntity<Map<Integer, ReviewStar>> response = reviewStarController.getAllReviewStarDice();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReviewStarMap, response.getBody());
    }
}
