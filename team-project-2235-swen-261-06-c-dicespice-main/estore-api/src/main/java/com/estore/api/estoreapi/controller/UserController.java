package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.LoginRequest;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCartService;
import com.estore.api.estoreapi.persistence.UserDAO;

/**
 * Handles the REST API requests for the User resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Scott Bullock & Mark Luskiewicz
 */

@RestController
@RequestMapping("user")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDAO userDAO;
    private ShoppingCartService shoppingCartService;

    /**
     * UserController constructor for use in production
     * @param userDAO the user data access object
     * @param shoppingCartService the shopping cart service
     */
    public UserController(UserDAO userDAO, ShoppingCartService shoppingCartService) {
        this.userDAO = userDAO;
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * Responds to the POST request to login {@linkplain User user}
     * @param loginRequest the {@linkplain User user} to login
     * @return ResponseEntity with the {@link User user} object and HTTP status of OK if successful<br>
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        LOG.info("POST /login " + loginRequest.getUsername());
        User user = null;

        try {
            user = userDAO.login(loginRequest.getUsername(), loginRequest.getPassword());

            // Load the user's shopping cart (this will create a new cart if one doesn't exist)
            shoppingCartService.loadUserCart();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            if(e.getMessage().equals("Password is less than 8 characters")){
                return new ResponseEntity<>("Password is less than 8 characters", HttpStatus.BAD_REQUEST);
            } else if(e.getMessage().equals("Password does not include atleast one uppercase letter")){
                return new ResponseEntity<>("Password does not include atleast one uppercase letter", HttpStatus.BAD_REQUEST);
            } else if(e.getMessage().equals("Password does not include atleast one lowercase letter")){
                return new ResponseEntity<>("Password does not include atleast one lowercase letter", HttpStatus.BAD_REQUEST);
            } else if(e.getMessage().equals("Password does not include atleast one number")){
                return new ResponseEntity<>("Password does not include atleast one number", HttpStatus.BAD_REQUEST);
            } else if(e.getMessage().equals("Password does not include a special character like ~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/")){
                return new ResponseEntity<>("Password does not include a special character like ~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/", HttpStatus.BAD_REQUEST);
            } else{
                return new ResponseEntity<>("Invalid username", HttpStatus.BAD_REQUEST);
            }
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the POST request to logout the current {@linkplain User user}
     * @return ResponseEntity with the message "User logged out" and HTTP status of OK if successful<br>
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() throws IOException {
        LOG.info("POST /logout");

        try {
            shoppingCartService.saveCart(shoppingCartService.loadUserCart());
            userDAO.logout();
            return new ResponseEntity<>("You have been logged out", HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request to get the role of the current {@linkplain User user}
     * @return ResponseEntity with the role of the current {@link User user} and HTTP status of OK if successful<br>
     */
    @GetMapping("/role")
    public ResponseEntity<String> getRole() {
        LOG.info("GET /role");

        try {
            String role = userDAO.getRole();
            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Responds to the GET request to get the current {@linkplain User user}
     * @return ResponseEntity with the current {@link User user} and HTTP status of OK if successful<br>
     */
    @GetMapping("/currentUser")
    public ResponseEntity<User> getCurrentUser() {
        LOG.info("GET /currentUser");

        User user = userDAO.getCurrentUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    /**
     * Responds to the PUT request to update the password of the current {@linkplain User user}
     * @param newPassword the new password to be used
     * @return ResponseEntity with the message "Password updated" and HTTP status of OK if successful<br>
     */
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody String newPassword) {
        LOG.info("PUT /updatePassword " + newPassword);

        try {
            userDAO.updatePassword(newPassword);
            return new ResponseEntity<>("Password updated", HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            if(e.getMessage().equals("Password is less than 8 characters")){
                return new ResponseEntity<>("Password is less than 8 characters", HttpStatus.BAD_REQUEST);
            } else if(e.getMessage().equals("Password does not include atleast one uppercase letter")){
                return new ResponseEntity<>("Password does not include atleast one uppercase letter", HttpStatus.BAD_REQUEST);
            } else if(e.getMessage().equals("Password does not include atleast one lowercase letter")){
                return new ResponseEntity<>("Password does not include atleast one lowercase letter", HttpStatus.BAD_REQUEST);
            } else if(e.getMessage().equals("Password does not include atleast one number")){
                return new ResponseEntity<>("Password does not include atleast one number", HttpStatus.BAD_REQUEST);
            } else{
                return new ResponseEntity<>("Password does not include a special character like ~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/", HttpStatus.BAD_REQUEST);
            }
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the PUT request to set the address of the current {@linkplain User user}
     * @param address the address to set
     * @return ResponseEntity with the message "Address updated" and HTTP status of OK if successful<br>
     */
    @PutMapping("/setAddress")
    public ResponseEntity<String> setAddress(@RequestBody String address) {
        LOG.info("PUT /setAddress " + address);

        try {
            userDAO.setAddress(address);
            userDAO.saveUser(userDAO.getCurrentUser());
            return new ResponseEntity<>("Address updated", HttpStatus.OK);
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request to get the address of the current {@linkplain User user}
     * @return ResponseEntity with the address of the current {@link User user} and HTTP status of OK if successful<br>
     */
    @GetMapping("/getAddress")
    public ResponseEntity<String> getAddress() {
        LOG.info("GET /getAddress");

        try {
            String address = userDAO.getAddress();
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the DELETE request to delete the address of the current {@linkplain User user}
     * @return ResponseEntity with the message "Address deleted" and HTTP status of OK if successful<br>
     */
    @DeleteMapping("/deleteAddress")
    public ResponseEntity<String> deleteAddress() {
        LOG.info("DELETE /deleteAddress");

        try {
            userDAO.deleteAddress();
            userDAO.saveUser(userDAO.getCurrentUser());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the PUT request to set the credit card number of the current {@linkplain User user}
     * @param creditCardNumber the credit card number to set
     * @return ResponseEntity with the message "Credit card number updated" and HTTP status of OK if successful<br>
     */
    @PutMapping("/setCreditCardNumber")
    public ResponseEntity<String> setCreditCardNumber(@RequestBody String creditCardNumber) {
        LOG.info("PUT /setCreditCardNumber " + creditCardNumber);

        try {
            userDAO.setCreditCardNumber(creditCardNumber);
            userDAO.saveUser(userDAO.getCurrentUser());
            return new ResponseEntity<>("Credit card number updated", HttpStatus.OK);
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request to get the credit card number of the current {@linkplain User user}
     * @return ResponseEntity with the credit card number of the current {@link User user} and HTTP status of OK if successful<br>
     */
    @GetMapping("/getCreditCardNumber")
    public ResponseEntity<String> getCreditCardNumber() {
        LOG.info("GET /getCreditCardNumber");

        try {
            String creditCardNumber = userDAO.getCreditCardNumber();
            return new ResponseEntity<>(creditCardNumber, HttpStatus.OK);
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the DELETE request to delete the credit card number of the current {@linkplain User user}
     * @return ResponseEntity with the message "Credit card number deleted" and HTTP status of OK if successful<br>
     */
    @DeleteMapping("/deleteCreditCard")
    public ResponseEntity<String> deleteCreditCard() {
        LOG.info("DELETE /deleteCreditCard");

        try {
            userDAO.deleteCreditCard();
            userDAO.saveUser(userDAO.getCurrentUser());
            return new ResponseEntity<>("Credit card deleted", HttpStatus.OK);
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the PUT request to set the credit card CCV of the current {@linkplain User user}
     * @param creditCardCCV the credit card CCV to set
     * @return ResponseEntity with the message "Credit card CCV updated" and HTTP status of OK if successful<br>
     */
    @PutMapping("/setCreditCardCCV")
    public ResponseEntity<String> setCreditCardCCV(@RequestBody String creditCardCCV) {
        LOG.info("PUT /setCreditCardCCV " + creditCardCCV);

        try {
            userDAO.setCreditCardCCV(creditCardCCV);
            userDAO.saveUser(userDAO.getCurrentUser());
            return new ResponseEntity<>("Credit card CCV updated", HttpStatus.OK);
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request to get the credit card CCV of the current {@linkplain User user}
     * @return ResponseEntity with the credit card CCV of the current {@link User user} and HTTP status of OK if successful<br>
     */
    @GetMapping("/getCreditCardCCV")
    public ResponseEntity<String> getCreditCardCCV() {
        LOG.info("GET /getCreditCardCCV");

        try {
            String creditCardCCV = userDAO.getCreditCardCCV();
            return new ResponseEntity<>(creditCardCCV, HttpStatus.OK);
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
