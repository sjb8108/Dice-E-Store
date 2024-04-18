package com.estore.api.estoreapi.controller;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.ReviewStar;
import com.estore.api.estoreapi.model.ReviewStarService;

/**
 * Handles the REST API requests for the ReviewStar resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Scott Bullock
 */

@RestController
@RequestMapping("reviewStars")
public class ReviewStarController {
    private static final Logger LOG = Logger.getLogger(ReviewStarController.class.getName());
    private ReviewStarService reviewStarService;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param ReviewService The service that handles the review stars
     * This dependency is injected by the Spring Framework
     */

    public ReviewStarController(ReviewStarService reviewStarService) {
        this.reviewStarService = reviewStarService;
    }

    /**
     * Responds to the GET request for an double for the given id
     * 
     * @param id The id used to locate the review star that the die has
     * 
     * @return ResponseEntity with double and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     */
    
    @GetMapping("/{id}")
    public ResponseEntity<Double> getReviewStarDice(@PathVariable int id) {
        LOG.info("GET /reviewStars/" + id);
        if (reviewStarService.getReviewStarDice().containsKey(id)){
            double averageStarRating = reviewStarService.getReviewStarDice().get(id).getStarRatingAverage();
            return new ResponseEntity<Double>(averageStarRating, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Responds to the GET request for all the reviews stars for all the dice
     * 
     * @return ResponseEntity with a map with the id of the dice and the 
     * reviews stars as the values for each id and HTTP status of OK if found<br>
     */
    
    @GetMapping("")
    public ResponseEntity<Map<Integer, ReviewStar>> getAllReviewStarDice() {
        LOG.info("GET /reviewStars");
        Map<Integer, ReviewStar> diceReviewStarMap = reviewStarService.getReviewStarDice();
        return new ResponseEntity<Map<Integer, ReviewStar>>(diceReviewStarMap, HttpStatus.OK);
    }

    /**
     * Creates a {@linkplain ReviewStar review} with the provided a double review
     * 
     * @param review - The review star to create
     * 
     * @return ResponseEntity with created double review star object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    
    @PostMapping("/add/{review}")
    public ResponseEntity<Double> addReviewToDice(@RequestBody Integer id, @PathVariable double review) {
        LOG.info("POST /reviewStars/add " + review);
        reviewStarService.addReviewStarDie(id, review);
        double averageStarRating = reviewStarService.getReviewStarDice().get(id).getStarRatingAverage();
        return new ResponseEntity<Double>(averageStarRating, HttpStatus.CREATED);
    }

    /**
     * Deletes a {@linkplain ReviewStar review star} with the given id
     * 
     * @param id The id of the dice that has the review star
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Double> deleteReviewStarDie(@PathVariable int id, @PathVariable double review) {
        LOG.info("DELETE /reviewStars/{id}" + review);
        try {
            reviewStarService.deleteReviewStar(id, review);
            return new ResponseEntity<>(HttpStatus.OK);
          
        } 
        catch(IllegalArgumentException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
}
