package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;


/**
 * The unit test suite for the LoginRequest class
 * 
 * @author Mark Luskiewicz
 */
@Tag("Model-tier")
public class LoginRequestTest {

    @Test
    public void testGetUsername() {
        LoginRequest loginRequest = new LoginRequest("testUser", "testPassword");
        assertEquals("testUser", loginRequest.getUsername());
    }

    @Test
    public void testGetPassword() {
        LoginRequest loginRequest = new LoginRequest("testUser", "testPassword");
        assertEquals("testPassword", loginRequest.getPassword());
    }

    @Test
    public void testSetUsername() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser");
        assertEquals("testUser", loginRequest.getUsername());
    }

    @Test
    public void testSetPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("testPassword");
        assertEquals("testPassword", loginRequest.getPassword());
    }
}
