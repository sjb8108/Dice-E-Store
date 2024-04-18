package com.estore.api.estoreapi.model;

/**
 * Represents a request to login
 * @author Mark Luskiewicz
 */
public class LoginRequest {
    private String username;
    private String password;

    /**
     * Creates a login request with no username or password
     */
    public LoginRequest() {
    }
    
    /**
     * Creates a login request with the given username and password
     * @param username the username
     * @param password the password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieves the username of the login request
     * @return the username of the login request
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Retrieves the password of the login request
     * @return the password of the login request
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the username of the login request
     * @param username the username of the login request
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password of the login request
     * @param password the password of the login request
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
