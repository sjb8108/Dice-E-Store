package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.estore.api.estoreapi.persistence.ReviewFileDAO;

/**
 * Represents the catalog of reviews that are placed on individual dice
 * @author Scott Bullock
 */

@Service
public class ReviewService {
    private Map<Integer, Review> diceReviewMap;
    private ReviewFileDAO reviewDAO;
    // private static int nextID;

    /**
     * Initializes the review service
     */

    public ReviewService(ReviewFileDAO reviewsDAO) {
        this.reviewDAO = reviewsDAO;
        diceReviewMap = reviewDAO.loadDiceReviews();
    }

    /**
     * Gets the map of reviews
     * @return the map of reviews
     */
    public Map<Integer, Review> getReviewDice() {
        Map<Integer, Review> diceReviewData = new HashMap<>();
        for (Entry<Integer, Review> entry : diceReviewMap.entrySet()) {
            int id = entry.getKey();
            Review reviews = entry.getValue();
            diceReviewData.put(id,reviews);
            Map<String, Object> data = new HashMap<>();
            data.put("id", id);
            data.put("reviews", reviews);
        }
        return diceReviewData;
    }

    /**
     * Checks if a review exists
     * @param id the id of the review
     * @return true if the review exists, false otherwise
     */
    // In ReviewService.java
    public boolean reviewExists(int id) {
    return diceReviewMap.containsKey(id);
    }

    /**
     * Adds a review from a product from the catalog of reviews
     * @param id the id of the dice that the review is to be added on
     * @param review the review that is to be added
     */

    public void addReviewDie(Integer id, String review) {
        if(diceReviewMap.containsKey(id)){
            diceReviewMap.get(id).addReview(review);
            diceReviewMap.put(id, diceReviewMap.get(id));
        } else {
            Review reviews = new Review();
            reviews.addReview(review);
            diceReviewMap.put(id, reviews);
        }
        reviewDAO.saveReviewsDice(diceReviewMap);

    }

    /**
     * Deletes a review from a product from the catalog of reviews
     * @param id the id of the dice that the review is on
     * @param review the review that is to be deleted
     */

    public void deleteReview(Integer id, String review){
        ArrayList<String> reviews = diceReviewMap.get(id).getReviews();
        for(int i = 0; i < reviews.size(); i++){
            if(review.equals(reviews.get(i))){
                reviews.remove(i);
                break;
            }
        }
        Review updatedReviews = new Review();
        for(int i = 0; i < reviews.size(); i++){
            updatedReviews.addReview(reviews.get(i));
        }
        diceReviewMap.put(id, updatedReviews);
        reviewDAO.saveReviewsDice(diceReviewMap);
    }


    /**
     * Gets the reviews for a product
     * @param id the id of the dice that the reviews are on
     * @return the reviews for the product
     */
    public ArrayList<String> getReviews(Integer id){
        return diceReviewMap.get(id).getReviews();
    }
}
