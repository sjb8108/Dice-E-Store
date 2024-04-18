package com.estore.api.estoreapi.model;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


/**
 * The unit test suite for the Review class
 * 
 * @author Nicholas Gouldin
 */
@Tag("Model-tier")
public class ReviewTest{

    @Test
    public void testConstructor(){
        Review review = new Review();
        assertNotNull(review);
    }

    @Test
    public void testAddReview(){
        Review review = new Review();
        review.addReview("This is a test string.");
        assertEquals(1, review.getReviews().size());
    }

    @Test
    public void testRemoveReview(){
        Review review = new Review();
        review.addReview("This is a test string.");
        review.removeReview("This is a test string.");
        assertEquals(0, review.getReviews().size());
    }
    
    @Test
    public void testRemoveReviewNotFound() {
        Review review = new Review();
        review.addReview("This is a test string.");
        try {
            review.removeReview("This is another test string.");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Review not found", e.getMessage());
        }
    }
    @Test
    public void testGetReviews(){
        Review review = new Review();
        review.addReview("This is a test string.");
        review.addReview("This is another test string.");
        ArrayList<String> reviews = review.getReviews();
        assertEquals(reviews.get(0),"This is a test string.");
        assertEquals(reviews.get(1),"This is another test string.");
    }
}
