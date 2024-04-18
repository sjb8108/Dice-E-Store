package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.estore.api.estoreapi.persistence.ReviewStarFileDAO;

/**
 * Represents the catalog of reviews stars that are placed on individual dice
 * @author Scott Bullock
 */

@Service
public class ReviewStarService {
    private Map<Integer, ReviewStar> diceReviewStarMap;
    private ReviewStarFileDAO reviewStarDAO;
    private static int nextID;

    /**
     * Initializes the review star service
     */

    public ReviewStarService(ReviewStarFileDAO reviewStarDAO) {
        this.reviewStarDAO = reviewStarDAO;
        diceReviewStarMap = reviewStarDAO.loadDiceReviewStars();
    }

    /**
     * Gets the map of reviews stars
     * @return the map of reviews stars
     */

    public Map<Integer, ReviewStar> getReviewStarDice() {
        Map<Integer, ReviewStar> diceReviewStarData = new HashMap<>();
        for (Entry<Integer, ReviewStar> entry : diceReviewStarMap.entrySet()) {
            int id = entry.getKey();
            ReviewStar reviewStars = entry.getValue();
            diceReviewStarData.put(id,reviewStars);
            Map<String, Object> data = new HashMap<>();
            data.put("id", id);
            data.put("reviewStars", reviewStars);
        }
        return diceReviewStarData;
    }

    /**
     * Adds a review star from a product from the catalog of reviews
     * @param id the id of the dice that the review star is to be added on
     * @param review the review star that is to be added
     */

    public void addReviewStarDie(Integer id, Double review) {
        if(diceReviewStarMap.containsKey(id)){
            diceReviewStarMap.get(id).addReviewStar(review);
            diceReviewStarMap.put(id, diceReviewStarMap.get(id));
        } else {
            ReviewStar reviewStar = new ReviewStar();
            reviewStar.addReviewStar(review);
            diceReviewStarMap.put(id, reviewStar);
        }
        reviewStarDAO.saveReviewStarsDice(diceReviewStarMap);
    }

    /**
     * Deletes a review star from a product from the catalog of reviews
     * @param id the id of the dice that the review star is on
     * @param review the review star that is to be deleted
     */

    public void deleteReviewStar(Integer id, Double review){
        ArrayList<Double> reviewStars = diceReviewStarMap.get(id).getReviewStarList();
        
        for(int i = 0; i < reviewStars.size(); i++){
            double value = reviewStars.get(i);
            if(review == value){
                reviewStars.remove(i);
                break;
            }
        }
        ReviewStar updatedReviewStars = new ReviewStar();
        for(int i = 0; i < reviewStars.size(); i++){
            updatedReviewStars.addReviewStar(reviewStars.get(i));
        }
        diceReviewStarMap.put(id, updatedReviewStars);
        reviewStarDAO.saveReviewStarsDice(diceReviewStarMap);
    }
}
