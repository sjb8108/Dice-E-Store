package com.estore.api.estoreapi.model;
import java.util.regex.*;




/**
 * Represents a User entity
 * @author Mark Luskiewicz & Scott Bullock
 */
public class User {
    // Package private for tests
    private static final String STRING_FORMAT = "User [username=%s, role=%s, password=%s, address=%s, creditCardNumber=%s, creditCardCCV=%s]";

    private String username;
    private String role;
    private String password;
    private String address = "";
    private String creditCardNumber = "";
    private String creditCardCCV = "";

    /**
     * Default constructor for use with Jackson
     */
    public User() {
    }
    /**
     * Creates a user with the given username and password and sets the role 
     * to admin if the username is admin and customer if the username is not admin
     * @param username the username
     * @param password the password
     */
    public User(String username, String password) {
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        this.username = username;
        this.setRole(username);
        this.password = password;
    }

    /**
     * Sets the role of the user to admin if the username is 
     * admin and customer if the username is not admin
     * @param username the username of the user
     */
    private void setRole(String username) {
        if (this.username.equals("admin")) {
            this.role = "admin";
        } else {
            this.role = "customer";
        }
    }
    
    /**
     * Retrieves the role of the user
     * @return the role of the user
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Retrieves the username of the user
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Changes the username to the new username that is in the parameter
     * @param username the new username
     */
    public void changeUsername(String username){
        this.username = username;
    }

    /**
     * Retrieves the password of the user
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Changes the password to the new password that is in the parameter
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Changes the role to the new role that is in the parameter
     * @param role the new role
     */
    public String getAddress() {
        return address;
    }

    /**
     * Changes the address to the new address that is in the parameter
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Deletes the address
     */
    public void deleteAddress() {
        this.address = "";
    }

    /**
     * Changes the credit card number to the new credit card number that is in the parameter
     * @return the credit card number
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Changes the credit card number to the new credit card number that is in the parameter
     * @param creditCardNumber the new credit card number
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * Deletes the credit card number and CCV
     */
    public void deleteCreditCard() {
        this.creditCardNumber = "";
        this.creditCardCCV = "";
    }

    /**
     * Changes the credit card CCV to the new credit card CCV that is in the parameter
     * @return the credit card CCV
     */
    public String getCreditCardCCV() {
        return creditCardCCV;
    }

    /**
     * Changes the credit card CCV to the new credit card CCV that is in the parameter
     * @param creditCardCCV the new credit card CCV
     */
    public void setCreditCardCCV(String creditCardCCV) {
        this.creditCardCCV = creditCardCCV;
    }

    /**
     * Determines if a password is valid
     * @param password the password that is being checked
     * @return true if the password is valid, false if the password is not valid
     * @throws IllegalArgumentException if the password is not valid
     * @throws IllegalAccessException 
     */
    public static boolean isValidPassword(String password) throws IllegalArgumentException {
        boolean len= checkLen(password);
        boolean uppercase = checkUpper(password);
        boolean lowercase = checkLower(password);
        boolean number = checkNumber(password);
        boolean special = checkSpecial(password);
        if(len && uppercase && lowercase && number && special){
            return true;
        } else{
            if(len != true){
                throw new IllegalArgumentException("Password is less than 8 characters");
            } else if(uppercase != true){
                throw new IllegalArgumentException("Password does not include atleast one uppercase letter");
            } else if(lowercase != true){
                throw new IllegalArgumentException("Password does not include atleast one lowercase letter");
            } else if(number != true){
                throw new IllegalArgumentException("Password does not include atleast one number");
            } else{
                throw new IllegalArgumentException("Password does not include a special character like ~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/");
            }
        }

    }

    public static boolean checkLen(String password){
        if(password.length() >= 8){
            return true;
        } else{
            return false;
        }
    }

    public static boolean checkUpper(String password){
        boolean uppercase = false;
        for(int i = 0; i < password.length(); i++){
            char singleChar = password.charAt(i);
            int asciiValue = singleChar;
            if(asciiValue >= 65 && asciiValue <= 90){
                uppercase = true;
                return uppercase;
            }
        }
        return uppercase;
    }

    public static boolean checkLower(String password){
        boolean lowercase = false;
        for(int i = 0; i < password.length(); i++){
            char singleChar = password.charAt(i);
            int asciiValue = singleChar;
            if(asciiValue >= 97 && asciiValue <= 122){
                lowercase = true;
                return lowercase;
            }
        }
        return lowercase;
    }

    public static boolean checkNumber(String password){
        boolean number = false;
        for(int i = 0; i < password.length(); i++){
            char singleChar = password.charAt(i);
            int asciiValue = singleChar;
            if(asciiValue >= 48 && asciiValue <= 57){
                number = true;
                return number;
            }
        }
        return number;
    }
   
    public static boolean checkSpecial(String password){
        boolean special = false;
        for(int i = 0; i < password.length(); i++){
            char singleChar = password.charAt(i);
            int asciiValue = singleChar;
            if(asciiValue >= 33 && asciiValue <= 47){
                special = true;
                return special;
            } else if(asciiValue >= 58 && asciiValue <= 64){
                special = true;
                return special;
            } else if(asciiValue >= 91 && asciiValue <= 96){
                special = true;
                return special;
            } else if(asciiValue >= 123 && asciiValue <= 126){
                special = true;
                return special;
            }
        }
        return special;
    }
    /**
     * Changes the password to the new password that is in the parameter
     * @param password the new password
     * @return true if the password is valid, false if the password is not valid
     */
    public boolean changePassword(String password){
        if(isValidPassword(password) == true){
            this.password = password;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, username, role, password, address, creditCardNumber, creditCardCCV);
    }
}
