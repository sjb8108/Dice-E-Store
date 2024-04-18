package com.estore.api.estoreapi.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.model.User;

/**
 * The unit test suite for the UserDAO class
 * 
 * @author Mark Luskiewicz & Scott Bullock
 */
public class UserDAOTest {
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        userDAO = Mockito.mock(UserDAO.class);
    }

    @Test
    public void testLoginSuccess() throws IOException {
        // Arrange data
        String username = "testUser";
        String password = "P@ssw0rd1!";
        User expectedUser = new User(username, password);
    
        // Define behavior for mock
        when(userDAO.login(username, password)).thenReturn(expectedUser);
    
        // Actual User
        User actualUser = userDAO.login(username, password);
    
        assertNotNull(actualUser, "User should not be null");
        assertEquals(expectedUser.getUsername(), actualUser.getUsername(), "Username should match");
        assertEquals(expectedUser.getPassword(), actualUser.getPassword(), "Password should match");
    }

    @Test
    public void testLogout() throws IOException {
        // Arrange data
        String username = "testUser";
        String password = "P@ssw0rd1!";

        // Log in the user
        userDAO.login(username, password);

        // Logout the user
        userDAO.logout();

        // Assert that the user was logged out
        assertNull(userDAO.getCurrentUser(), "User should be null after logout");
    }

    @Test
    public void testGetCurrentUser() throws IOException {
        // Arrange data
        String username = "testUser";
        String password = "P@ssw0rd1!";
        User expectedUser = new User(username, password);
    
        // Define behavior for mock
        when(userDAO.login(username, password)).thenReturn(expectedUser);
        when(userDAO.getCurrentUser()).thenReturn(expectedUser);
    
        // Log in the user
        userDAO.login(username, password);
    
        // Get the current user
        User actualUser = userDAO.getCurrentUser();
    
        assertNotNull(actualUser, "User should not be null");
        assertEquals(expectedUser.getUsername(), actualUser.getUsername(), "Username should match");
        assertEquals(expectedUser.getPassword(), actualUser.getPassword(), "Password should match");
    }

    @Test
    public void testUpdatePassword() throws IOException {
        // Arrange data
        String username = "testUser";
        String password = "P@ssw0rd1!";
        String newPassword = "P@ssw0rd1000!";
        User expectedUser = new User(username, password);
    
        // Define behavior for mock
        when(userDAO.login(username, password)).thenReturn(expectedUser);
        doAnswer(invocation -> {
            expectedUser.setPassword(newPassword);
            return null;
        }).when(userDAO).updatePassword(newPassword);
        when(userDAO.getCurrentUser()).thenReturn(expectedUser);
    
        // Log in the user
        userDAO.login(username, password);
    
        // Update the password
        userDAO.updatePassword(newPassword);
    
        // Get the current user
        User actualUser = userDAO.getCurrentUser();
    
        assertEquals(newPassword, actualUser.getPassword(), "Password should match");
    }
    
    @Test
    public void testLoginWithNullUsername() throws IllegalArgumentException, IOException {
        UserDAO userDAO = mock(UserDAOImpl.class);
        doThrow(new IllegalArgumentException("Username cannot be null or empty")).when(userDAO).login(null, "validPassword!");
    
        assertThrows(IllegalArgumentException.class, () -> {
            userDAO.login(null, "validPassword!");
        }, "Username cannot be null or empty");
    }
    
    @Test
    public void testLoginWithEmptyUsername() throws IllegalArgumentException, IOException {
        UserDAO userDAO = mock(UserDAOImpl.class);
        doThrow(new IllegalArgumentException("Username cannot be null or empty")).when(userDAO).login("", "validPassword!");
    
        assertThrows(IllegalArgumentException.class, () -> {
            userDAO.login("", "validPassword!");
        }, "Username cannot be null or empty");
    }
    
    @Test
    public void testLoginWithInvalidPassword() throws IllegalArgumentException, IOException {
        String username = "testUser";
        String validPassword = "P@ssw0rd1!";
        String invalidPassword = "P";
        User expectedUser = new User(username, validPassword);
    
        when(userDAO.login(username, validPassword)).thenReturn(expectedUser);
        when(userDAO.login(username, invalidPassword)).thenThrow(new IllegalArgumentException("Invalid password"));
    
        assertThrows(IllegalArgumentException.class, () -> {
            userDAO.login(username, invalidPassword);
        }, "Invalid password");
    }
    
    @Test
    public void testGetRoleWithNoCurrentUser() {
        UserDAO userDAO = mock(UserDAOImpl.class);
        when(userDAO.getCurrentUser()).thenReturn(null);
        doThrow(new IllegalArgumentException("No user is currently logged in")).when(userDAO).getRole();
    
        assertThrows(IllegalArgumentException.class, () -> {
            userDAO.getRole();
        });
    }
    
    @Test
    public void testUpdatePasswordWithInvalidPassword() throws IllegalArgumentException, IOException {
        String username = "testUser";
        String password = "P@ssw0rd1!";
        User expectedUser = new User(username, password);
    
        when(userDAO.login(username, password)).thenReturn(expectedUser);
        doThrow(new IllegalArgumentException("Invalid password")).when(userDAO).updatePassword("invalidPassword!");
    
        assertThrows(IllegalArgumentException.class, () -> {
            userDAO.updatePassword("invalidPassword!");
        });
    }
}
