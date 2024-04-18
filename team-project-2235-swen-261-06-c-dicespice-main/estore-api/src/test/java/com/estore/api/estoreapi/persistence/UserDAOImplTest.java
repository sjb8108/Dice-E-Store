package com.estore.api.estoreapi.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.estore.api.estoreapi.model.User;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOImplTest {

    private UserDAOImpl userDAO;

    @Mock
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userDAO = new UserDAOImpl();
    }

    @Test
    public void testSaveUser() throws IOException {
        // Arrange
        String username = "testuser";
        when(user.getUsername()).thenReturn(username);

        // Act
        userDAO.saveUser(user);

        // Assert
        File file = new File("data/UserData/" + username + "_userdata.json");
        assertTrue(file.exists());
    }

    @Test
    public void testLoginWithValidCredentials() throws IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123!";
        userDAO.saveUser(user);
        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn(password);

        // Act
        User loggedInUser = userDAO.login(username, password);

        // Assert
        assertNotNull(loggedInUser);
        assertEquals(username, loggedInUser.getUsername());
    }

    @Test
    public void testLoginWithInvalidUsername() throws IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123";
        userDAO.saveUser(user);
        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn(password);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userDAO.login("invalidusername", password));
    }

    @Test
    void testLoginPasswordLessThan8Characters() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userDAO.login("username", "short");
        });

        String expectedMessage = "Password is less than 8 characters";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLoginPasswordWithoutUppercaseLetter() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userDAO.login("username", "alllowercase1!");
        });

        String expectedMessage = "Password does not include atleast one uppercase letter";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLoginPasswordWithoutLowercaseLetter() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userDAO.login("username", "ALLUPPERCASE1!");
        });

        String expectedMessage = "Password does not include atleast one lowercase letter";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLoginPasswordWithoutNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userDAO.login("username", "NoNumber!");
        });

        String expectedMessage = "Password does not include atleast one number";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLoginPasswordWithoutSpecialCharacter() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userDAO.login("username", "NoSpecialChar1");
        });

        String expectedMessage = "Password does not include a special character like ~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testLogout() {
        // Act
        userDAO.logout();

        // Assert
        assertNull(userDAO.getCurrentUser());
    }

    @Test
    public void testGetRole() throws IOException {
        // Arrange
        String role = "admin";
        String username = "admin";
        String password = "TestPassword123!";
        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn(password);
        when(user.getRole()).thenReturn(role);
        userDAO.saveUser(user);
    
        // Act
        userDAO.login(username, password);
        String userRole = userDAO.getRole();
    
        // Assert
        assertEquals(role, userRole);
    }

    @Test
    public void testGetRoleWithoutLoggedInUser() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userDAO.getRole());
    }

    @Test
    public void testUpdatePassword() throws IOException {
        // Arrange
        String username = "user";
        String password = "TestPassword123!";
        String newPassword = "NewPassword123!";
        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn(password);
        userDAO.saveUser(user);
        userDAO.login(username, password);
    
        // Act
        userDAO.updatePassword(newPassword);
    
        // Assert
        assertEquals(newPassword, userDAO.getCurrentUser().getPassword());
    }

    @Test
    public void testUpdatePasswordWithShortPassword() throws IllegalArgumentException, IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123!";
        String newPassword = "short";
        userDAO.saveUser(new User(username, password));
        userDAO.login(username, password);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDAO.updatePassword(newPassword));
        assertEquals("Password is less than 8 characters", exception.getMessage());
    }

    @Test
    public void testUpdatePasswordWithoutUppercase() throws IllegalArgumentException, IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123!";
        String newPassword = "password123!";
        userDAO.saveUser(new User(username, password));
        userDAO.login(username, password);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDAO.updatePassword(newPassword));
        assertEquals("Password does not include atleast one uppercase letter", exception.getMessage());
    }

    @Test
    public void testUpdatePasswordWithoutLowercase() throws IllegalArgumentException, IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123!";
        String newPassword = "PASSWORD123!";
        userDAO.saveUser(new User(username, password));
        userDAO.login(username, password);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDAO.updatePassword(newPassword));
        assertEquals("Password does not include atleast one lowercase letter", exception.getMessage());
    }

    @Test
    public void testUpdatePasswordWithoutNumber() throws IllegalArgumentException, IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123!";
        String newPassword = "Password!";
        userDAO.saveUser(new User(username, password));
        userDAO.login(username, password);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDAO.updatePassword(newPassword));
        assertEquals("Password does not include atleast one number", exception.getMessage());
    }

    @Test
    public void testUpdatePasswordWithoutSpecialCharacter() throws IllegalArgumentException, IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123!";
        String newPassword = "Password123";
        userDAO.saveUser(new User(username, password));
        userDAO.login(username, password);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDAO.updatePassword(newPassword));
        assertEquals("Password does not include a special character like ~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/", exception.getMessage());
    }
    
    @Test
    public void testUpdatePasswordWithInvalidPassword() throws IOException {
        // Arrange
        String username = "user";
        String password = "TestPassword123!";
        String newPassword = "invalidpassword";
        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn(password);
        userDAO.saveUser(user);
        userDAO.login(username, password);
    
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userDAO.updatePassword(newPassword));
    }
    
    @Test
    public void testSetAddress() throws IOException {
        // Arrange
        String username = "user";
        String password = "TestPassword123!";
        String address = "123 Main St";
        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn(password);
        userDAO.saveUser(user);
        userDAO.login(username, password);
    
        // Act
        userDAO.setAddress(address);
    
        // Assert
        assertEquals(address, userDAO.getCurrentUser().getAddress());
    }
    
    @Test
    public void testGetAddress() throws IOException {
        // Arrange
        String username = "user";
        String password = "TestPassword123!";
        String address = "123 Main St";
        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn(password);
        userDAO.saveUser(user);
        userDAO.login(username, password);
        userDAO.setAddress(address);
    
        // Act
        String userAddress = userDAO.getAddress();
    
        // Assert
        assertEquals(address, userAddress);
    }

    @Test
    public void testLoadUser() throws IOException {
        // Arrange
        String username = "testuser";
        User user = new User(username, "TestPassword123!");
        userDAO.saveUser(user);

        // Act
        User loadedUser = userDAO.loadUser(username);

        // Assert
        assertNotNull(loadedUser);
        assertEquals(username, loadedUser.getUsername());
    }

    @Test
    public void testLoginWithNullUsername() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userDAO.login(null, "TestPassword123!"));
    }

    @Test
    public void testLoginWithEmptyUsername() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userDAO.login("", "TestPassword123!"));
    }

    @Test
    public void testDeleteAddress() throws IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123!";
        String address = "123 Main St";
        userDAO.saveUser(new User(username, password));
        userDAO.login(username, password);
        userDAO.setAddress(address);
    
        // Act
        userDAO.deleteAddress();
    
        // Assert
        assertEquals("", userDAO.getCurrentUser().getAddress());
    }

    @Test
    public void testSetCreditCardNumber() throws IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123!";
        String creditCardNumber = "1234567890123456";
        userDAO.saveUser(new User(username, password));
        userDAO.login(username, password);

        // Act
        userDAO.setCreditCardNumber(creditCardNumber);

        // Assert
        assertEquals(creditCardNumber, userDAO.getCurrentUser().getCreditCardNumber());
    }

    @Test
    public void testGetCreditCardNumber() throws IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123!";
        String creditCardNumber = "1234567890123456";
        userDAO.saveUser(new User(username, password));
        userDAO.login(username, password);
        userDAO.setCreditCardNumber(creditCardNumber);

        // Act
        String userCreditCardNumber = userDAO.getCreditCardNumber();

        // Assert
        assertEquals(creditCardNumber, userCreditCardNumber);
    }

    @Test
    public void testDeleteCreditCard() throws IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123!";
        String creditCardNumber = "1234567890123456";
        userDAO.saveUser(new User(username, password));
        userDAO.login(username, password);
        userDAO.setCreditCardNumber(creditCardNumber);

        // Act
        userDAO.deleteCreditCard();

        // Assert
        assertEquals("", userDAO.getCurrentUser().getCreditCardNumber());}

    @Test
    public void testSetCreditCardCCV() throws IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123!";
        String creditCardCCV = "123";
        userDAO.saveUser(new User(username, password));
        userDAO.login(username, password);

        // Act
        userDAO.setCreditCardCCV(creditCardCCV);

        // Assert
        assertEquals(creditCardCCV, userDAO.getCurrentUser().getCreditCardCCV());
    }

    @Test
    public void testGetCreditCardCCV() throws IOException {
        // Arrange
        String username = "testuser";
        String password = "TestPassword123!";
        String creditCardCCV = "123";
        userDAO.saveUser(new User(username, password));
        userDAO.login(username, password);
        userDAO.setCreditCardCCV(creditCardCCV);

        // Act
        String userCreditCardCCV = userDAO.getCreditCardCCV();

        // Assert
        assertEquals(creditCardCCV, userCreditCardCCV);
    }
}

