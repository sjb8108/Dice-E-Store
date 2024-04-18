package com.estore.api.estoreapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a review star class
 * 
 * @author Scott Bullock
 */

public class ReviewStar {
    @JsonProperty("reviewStarList") private ArrayList<Double> reviewStarList = new ArrayList<>();
    public ReviewStar addReviewStar;

    /**
     * Creates a object that represents a users review star rating
     * @param starRating the review star rating of the product
     */
    public ReviewStar(){
        reviewStarList = new ArrayList<Double>();
    }

    /**
     * Gets the review star rating
     * @return the review star rating
     */
    public double getStarRatingAverage(){
        double total = 0;
        double average = 0;
        double size = reviewStarList.size();
        for(int i = 0; i < reviewStarList.size(); i++){
            total+=reviewStarList.get(i);
        }
        average = total / size;
        return average;
    }
    
    /**
     * Adds a star review to a product
     * @param starRating the star review to be added
     * @throws IllegalArgumemntException thrown if the review star is below 1 or greater than 5
     */

    public void addReviewStar(double starRating) throws IllegalArgumentException{
        if(starRating >= 1 && starRating <=5){
            this.reviewStarList.add(starRating);
        } else {
            throw new IllegalArgumentException("Star rating must be between 1 and 5");
        }
    }

    /**
     * Gets the array list of doubles that are connected to the dice
     * @return the review star list of review stars
     */
    public ArrayList<Double> getReviewStarList(){
        return reviewStarList;
    }

    /**
     * Removes a review star from a product
     * @param reviewStar the review star to be removed
     * @throws IllegalArgumentException thrown if the review star is not found in the product
     */

    public void removeRating(Double reviewStar) throws IllegalArgumentException{
        boolean starReviewPresent = false;
        for(int i = 0; i < this.getReviewStarList().size(); i++){
            double value = reviewStarList.get(i);
            if(reviewStar == value){
                reviewStarList.remove(i);
                starReviewPresent = true;
                break;
            }
        }
        if(starReviewPresent != true){
            throw new IllegalArgumentException("Review Star rating not found");
        }
    }
}
