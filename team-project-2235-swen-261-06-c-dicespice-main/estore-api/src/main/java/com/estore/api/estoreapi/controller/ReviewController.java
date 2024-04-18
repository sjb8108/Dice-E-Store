package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Review;
import com.estore.api.estoreapi.model.ReviewService;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Handles the REST API requests for the Review resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Scott Bullock
 */

@RestController
@RequestMapping("reviews")
public class ReviewController {
    private static final Logger LOG = Logger.getLogger(ReviewController.class.getName());
    private ReviewService reviewService;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param ReviewService The service that handles the reviews
     * This dependency is injected by the Spring Framework
     */

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Responds to the GET request for an array list of string for the given id
     * 
     * @param id The id used to locate the reviews that the die has
     * 
     * @return ResponseEntity with array list of string object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     */
     @GetMapping("/{id}")
     public ResponseEntity<Object> getReviewDice(@PathVariable int id) {
         LOG.info("GET /reviews/" + id);
         if (reviewService.reviewExists(id)){
             ArrayList<String> reviews = reviewService.getReviewDice().get(id).getReviews();
             return new ResponseEntity<Object>(reviews, HttpStatus.OK);
         } else{
             return new ResponseEntity<Object>("Review with id " + id + " not found.", HttpStatus.NOT_FOUND);
         }
     }

    /**
     * Creates a {@linkplain Review review} with the provided string review 
     * 
     * @param review - The review to create
     * 
     * @return ResponseEntity with created array list of string reviews object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */

    @PostMapping("/add/{review}")
    public ResponseEntity<ArrayList<String>> addReviewToDice(@RequestBody Integer id, @PathVariable String review) {
        LOG.info("POST /reviews/add " + review);
        reviewService.addReviewDie(id, review);
        ArrayList<String> reviews = reviewService.getReviewDice().get(id).getReviews();
        return new ResponseEntity<ArrayList<String>>(reviews, HttpStatus.CREATED);
    }

    /**
     * Deletes a {@linkplain Review review} with the given id
     * 
     * @param id The id of the dice that has the review
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<ArrayList<String>> deleteReviewDie(@PathVariable int id, @PathVariable String review) {
        LOG.info("DELETE /reviews/{id}" + review);
        try {
            reviewService.deleteReview(id, review);
            return new ResponseEntity<>(HttpStatus.OK);
        } 
        catch(IllegalArgumentException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
