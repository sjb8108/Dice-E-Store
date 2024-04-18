package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.User;

/**
 * Defines the interface for User object persistence
 * 
 * @author Mark Luskiewicz
 */
public interface UserDAO {

    /**
     * Saves a {@linkplain User user} to the file system.
     * @param user the {@link User user} to save
     * @throws IOException if an I/O error occurs
     */
    void saveUser(User user) throws IOException;

    /**
     * Loads a {@linkplain User user} from the file system.
     * @param username the username of the {@link User user} to load
     * @return the loaded {@link User user}
     * @throws IOException if an I/O error occurs
     */
    User loadUser(String username) throws IOException;

    /**
     * Logs in a {@linkplain User user} and sets it as the current {@linkplain User user}.
     * If the username and password are correct, the user is logged in. Otherwise, the user is not logged in.
     * 
     * @param username the username of the u{@link User user}ser to log in
     * @param password the password of the {@link User user} to log in
     * @return the logged in user if the username and password are correct, null otherwise
     * @throws IOException if an I/O error occurs
     */
    User login(String username, String password) throws IOException, IllegalArgumentException;
    
    /**
     * Logs out the current {@linkplain User user}.
     */
    void logout();
    
    /**
     * Retrieves the role of the current {@linkplain User user}.
     * @throws IllegalArgumentException if no {@link User user} is currently logged in
     * @return the role of the current {@link User user}
     */
    String getRole() throws IllegalArgumentException;

    /**
     * Retrieves the currently logged in {@linkplain User user}.
     * 
     * @return the currently logged in {@link User user}, null if no {@link User user} is logged in
     */
    User getCurrentUser();
    
    /**
     * Updates the password of the currently logged in {@linkplain User user}.
     * 
     * @param newPassword the new password to be used
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if the new password is invalid
     */
    void updatePassword(String newPassword) throws IOException, IllegalArgumentException;

    /**
     * Sets the address of the currently logged in {@linkplain User user}.
     * @param address the address to set
     * @throws IOException if an I/O error occurs
     */
    void setAddress(String address) throws IOException;

    /**
     * Retrieves the address of the currently logged in {@linkplain User user}.
     * @return the address of the currently logged in {@link User user}
     * @throws IOException if an I/O error occurs
     */
    String getAddress() throws IOException;

    /**
     * Deletes the address of the currently logged in {@linkplain User user}.
     * @throws IOException if an I/O error occurs
     */
    void deleteAddress() throws IOException;

    /**
     * Sets the credit card number of the currently logged in {@linkplain User user}.
     * @param creditCardNumber the credit card number to set
     * @throws IOException if an I/O error occurs
     */
    void setCreditCardNumber(String creditCardNumber) throws IOException;

    /**
     * Retrieves the credit card number of the currently logged in {@linkplain User user}.
     * @return the credit card number of the currently logged in {@link User user}
     * @throws IOException if an I/O error occurs
     */
    String getCreditCardNumber() throws IOException;

    /**
     * Deletes the credit card number of the currently logged in {@linkplain User user}.
     * @throws IOException if an I/O error occurs
     */
    void deleteCreditCard() throws IOException;

    /**
     * Sets the credit card CCV of the currently logged in {@linkplain User user}.
     * @param creditCardCCV the credit card CCV to set
     * @throws IOException if an I/O error occurs
     */
    void setCreditCardCCV(String creditCardCCV) throws IOException;

    /**
     * Retrieves the credit card CCV of the currently logged in {@linkplain User user}.
     * @return the credit card CCV of the currently logged in {@link User user}
     * @throws IOException if an I/O error occurs
     */
    String getCreditCardCCV() throws IOException;
}
