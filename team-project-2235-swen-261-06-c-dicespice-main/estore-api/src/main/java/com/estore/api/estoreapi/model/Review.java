package com.estore.api.estoreapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a review entity
 * 
 * @author Nicholas Gouldin and Scott Bullock
 */
public class Review {
    @JsonProperty("reviewStarList") private ArrayList<String> reviewList = new ArrayList<>();

    /**
     * Creates a review list to store all reviews on a product
     * an array list is made for each product on the e-store
     */

    public Review(){
        reviewList = new ArrayList<String>();
    }

    /**
     * Gets all the reviews connected to the product
     * @return an array list of strings that are the reviews
     */

    public ArrayList<String> getReviews(){
        return this.reviewList;
    }

    /**
     * Removes one review from a product
     * @param text the text of the review that needs to be deleted
     * @throws IllegalArgumentException thrown if the review doesnt exist
     */

    public void removeReview(String text) throws IllegalArgumentException{
        boolean ReviewPresent = false;
        for(int i = 0; i < this.getReviews().size(); i++){
            if(text.equals(this.reviewList.get(i))){
                reviewList.remove(i);
                ReviewPresent = true;
                break;
            }
        }
        if(ReviewPresent != true){
            throw new IllegalArgumentException("Review not found");
        }
    }

    /**
     * Adds a review to a product
     * @param text the review to be added to the product
     */

    public void addReview(String text){
        reviewList.add(text);
    }
}
