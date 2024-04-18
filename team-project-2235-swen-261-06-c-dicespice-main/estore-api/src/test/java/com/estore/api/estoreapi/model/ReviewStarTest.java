package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the ReviewStar class
 * 
 * @author Nicholas Gouldin
 */
public class ReviewStarTest {
    @Test
    public void testConstructor(){
        ReviewStar star = new ReviewStar();
        assertNotNull(star);
    }

    @Test
    public void testAddReviewStar(){
        ReviewStar star = new ReviewStar();
        star.addReviewStar(3);
        assertEquals(1,star.getReviewStarList().size());
    }

    @Test
    public void testAddReviewStarOutOfRange(){
        ReviewStar star = new ReviewStar();
        try {
            star.addReviewStar(6);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Star rating must be between 1 and 5", e.getMessage());
        }
    }
    @Test
    public void testGetReviewStarList(){
        ReviewStar star = new ReviewStar();
        star.addReviewStar(3);
        star.addReviewStar(4);
        ArrayList<Double> stars = star.getReviewStarList();
        assertEquals(stars.get(0),3);
        assertEquals(stars.get(1),4);
    }

    @Test
    public void testRemoveRating(){
        ReviewStar star = new ReviewStar();
        star.addReviewStar(Double.valueOf(3));
        star.removeRating(Double.valueOf(3));
        assertEquals(0,star.getReviewStarList().size());
    }
    @Test
    public void testRemoveRatingNotFound(){
        ReviewStar star = new ReviewStar();
        star.addReviewStar(Double.valueOf(3));
        try {
            star.removeRating(Double.valueOf(4));
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Review Star rating not found", e.getMessage());
        }
    }

    @Test
    public void testGetStarRatingAverage(){
        ReviewStar star = new ReviewStar();
        star.addReviewStar(3);
        star.addReviewStar(4);
        assertEquals(3.5, star.getStarRatingAverage());
    }
}
