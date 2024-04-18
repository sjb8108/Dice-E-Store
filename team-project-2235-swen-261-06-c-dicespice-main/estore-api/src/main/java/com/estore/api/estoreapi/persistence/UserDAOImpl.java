package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements the functionality for interaction with the User from the controller.
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * 
 * @author Mark Luskiewicz & Scott Bullock
 */
@Component
public class UserDAOImpl implements UserDAO {
    private User currentUser;
    private ObjectMapper mapper;

    public UserDAOImpl() {
        mapper = new ObjectMapper();
    }

    @Override
    public void saveUser(User user) {
        try {
            mapper.writeValue(new File("data/UserData/" + user.getUsername() + "_userdata.json"), user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User loadUser(String username) {
        User user = null;
        File file = new File("data/UserData/" + username + "_userdata.json");
        if (file.exists()) {
            try {
                user = mapper.readValue(file, User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User login(String username, String password) throws IOException, IllegalArgumentException {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
    
        if (!User.isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        
        try{
            User.isValidPassword(password);
        } catch(IllegalArgumentException e){
            if(e.getMessage().contains("Password is less than 8 characters")){
                throw new IllegalArgumentException("Password is less than 8 characters");
            } else if(e.getMessage().contains("Password does not include atleast one uppercase letter")){
                throw new IllegalArgumentException("Password does not include atleast one uppercase letter");
            } else if(e.getMessage().contains("Password does not include atleast one lowercase letter")){
                throw new IllegalArgumentException("Password does not include atleast one lowercase letter");
            } else if(e.getMessage().contains("Password does not include atleast one number")){
                throw new IllegalArgumentException("Password does not include atleast one number");
            } else{
                throw new IllegalArgumentException("Password does not include a special character like ~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/");
            }
        }
    
        // Load the user data
        currentUser = loadUser(username);
    
        // If the user data doesn't exist, create a new User object and save it
        if (currentUser == null) {
            currentUser = new User(username, password);
            saveUser(currentUser);
        } else if (!currentUser.getPassword().equals(password)) {
            // If the user data exists but the password doesn't match, throw an exception
            throw new IllegalArgumentException("Invalid username or password");
        }
    
        return currentUser;
    }

    @Override
    public void logout() {
        currentUser = null;
    }

    @Override    
    public String getRole() throws IllegalArgumentException {
        if (currentUser == null) {
            throw new IllegalArgumentException("No user is currently logged in");
        }
        return currentUser.getRole();
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void updatePassword(String newPassword) throws IOException, IllegalArgumentException {
        try{
            User.isValidPassword(newPassword);
        } catch(IllegalArgumentException e){
            if(e.getMessage().contains("Password is less than 8 characters")){
                throw new IllegalArgumentException("Password is less than 8 characters");
            } else if(e.getMessage().contains("Password does not include atleast one uppercase letter")){
                throw new IllegalArgumentException("Password does not include atleast one uppercase letter");
            } else if(e.getMessage().contains("Password does not include atleast one lowercase letter")){
                throw new IllegalArgumentException("Password does not include atleast one lowercase letter");
            } else if(e.getMessage().contains("Password does not include atleast one number")){
                throw new IllegalArgumentException("Password does not include atleast one number");
            } else{
                throw new IllegalArgumentException("Password does not include a special character like ~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/");
            }
        }
        currentUser.setPassword(newPassword);
    }

    @Override
    public void setAddress(String address) throws IOException {
        currentUser.setAddress(address);
    }

    @Override
    public String getAddress() throws IOException {
        return currentUser.getAddress();
    }

    @Override
    public void deleteAddress() throws IOException {
        currentUser.deleteAddress();
    }

    @Override
    public void setCreditCardNumber(String creditCardNumber) throws IOException {
        currentUser.setCreditCardNumber(creditCardNumber);
    }

    @Override
    public String getCreditCardNumber() throws IOException {
        return currentUser.getCreditCardNumber();
    }

    @Override
    public void deleteCreditCard() throws IOException {
        currentUser.deleteCreditCard();
    }

    @Override
    public void setCreditCardCCV(String creditCardCCV) throws IOException {
        currentUser.setCreditCardCCV(creditCardCCV);
    }

    @Override
    public String getCreditCardCCV() throws IOException {
        return currentUser.getCreditCardCCV();
    }
}