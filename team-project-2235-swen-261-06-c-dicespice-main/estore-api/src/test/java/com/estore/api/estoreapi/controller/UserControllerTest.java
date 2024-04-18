package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.model.LoginRequest;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCart;
import com.estore.api.estoreapi.model.ShoppingCart.ShoppingCartService;
import com.estore.api.estoreapi.persistence.UserDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The unit test suite for the UserController class
 * @author Mark Luskiewicz & Scott Bullock & Lara Toklar
 */
@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;
    private ShoppingCartService mockShoppingCartService;

    /**
     * Setup the test creating a mock controller before each test
     */
    @BeforeEach
    public void setup() {
        mockUserDAO = mock(UserDAO.class);
        mockShoppingCartService = mock(ShoppingCartService.class);
        userController = new UserController(mockUserDAO, mockShoppingCartService);
    }
    
    @Test
    public void testLoginSuccess() throws IOException { // Login might throw an IOException
        // Mock the login request
        LoginRequest loginRequest = new LoginRequest("user", "Password123!");
        
        // Mock the response entity
        User expectedUser = new User("user", "Password123!");
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(expectedUser, HttpStatus.OK);
        
        // Mock the login method
        when(mockUserDAO.login("user", "Password123!")).thenReturn(expectedUser);
        when(mockShoppingCartService.loadUserCart()).thenReturn(new ShoppingCart());
        
        // Call the login method and assert the response
        ResponseEntity<Object> actualResponse = userController.login(loginRequest);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testLoginIOException() throws IOException {
        // Arrange
        UserDAO mockUserDAO = Mockito.mock(UserDAO.class);
        ShoppingCartService mockShoppingCartService = Mockito.mock(ShoppingCartService.class);
        UserController userController = new UserController(mockUserDAO, mockShoppingCartService);
        LoginRequest loginRequest = new LoginRequest("username", "password");

        // Simulate IOException
        when(mockUserDAO.login(loginRequest.getUsername(), loginRequest.getPassword())).thenThrow(new IOException());

        // Act
        ResponseEntity<Object> response = userController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testLoginFailure() throws IOException {
        // Mock the login request
        LoginRequest loginRequestLen = new LoginRequest("user", "p@sS");
    
        // Mock the login method to throw an IllegalArgumentException
        when(mockUserDAO.login("user", "p@sS")).thenThrow(new IllegalArgumentException("Password is less than 8 characters"));
    
        // Call the login method and assert that the response has a UNAUTHORIZED status
        ResponseEntity<Object> actualResponseLen = userController.login(loginRequestLen);
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseLen.getStatusCode());

        LoginRequest loginRequestUpper = new LoginRequest("user", "password1!");
        when(mockUserDAO.login("user", "password1!")).thenThrow(new IllegalArgumentException("Password does not include atleast one uppercase letter"));
        ResponseEntity<Object> actualResponseUpper = userController.login(loginRequestUpper);
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseUpper.getStatusCode());

        LoginRequest loginRequestLower = new LoginRequest("user", "PASSWORD1!");
        when(mockUserDAO.login("user", "PASSWORD1!")).thenThrow(new IllegalArgumentException("Password does not include atleast one lowercase letter"));
        ResponseEntity<Object> actualResponseLower = userController.login(loginRequestLower);
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseLower.getStatusCode());

        LoginRequest loginRequestNumber = new LoginRequest("user", "passWORD!");
        when(mockUserDAO.login("user", "passWORD!")).thenThrow(new IllegalArgumentException("Password does not include atleast one number"));
        ResponseEntity<Object> actualResponseNumber = userController.login(loginRequestNumber);
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseNumber.getStatusCode());

        LoginRequest loginRequestSpecial = new LoginRequest("user", "passWORD1");
        when(mockUserDAO.login("user", "passWORD1")).thenThrow(new IllegalArgumentException("Password does not include a special character like ~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/"));
        ResponseEntity<Object> actualResponseSpecial = userController.login(loginRequestSpecial);
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseSpecial.getStatusCode());
    }
    
    @Test
    public void testLogout() throws IOException {
        // Mock the response entity
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("You have been logged out", HttpStatus.OK);
        
        // Call the logout method and assert the response
        ResponseEntity<String> actualResponse = userController.logout();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testLogoutIOException() throws IOException {
        // Arrange
        UserDAO mockUserDAO = Mockito.mock(UserDAO.class);
        ShoppingCartService mockShoppingCartService = Mockito.mock(ShoppingCartService.class);
        UserController userController = new UserController(mockUserDAO, mockShoppingCartService);

        doThrow(IOException.class).when(mockShoppingCartService).saveCart(Mockito.any());

        // Act
        ResponseEntity<String> response = userController.logout();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testLogoutException() throws IOException {
        // Mock the logout method to throw an IllegalArgumentException
        doThrow(new IllegalArgumentException("User not logged in")).when(mockUserDAO).logout();
        
        // Call the logout method and assert that the response has a BAD_REQUEST status
        ResponseEntity<String> actualResponse = userController.logout();
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
    }

    @Test
    public void testGetRole() throws IOException {
        // Mock the response entity
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("admin", HttpStatus.OK);
        
        // Mock the getRole method
        when(mockUserDAO.getRole()).thenReturn("admin");
        
        // Call the getRole method and assert the response
        ResponseEntity<String> actualResponse = userController.getRole();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetRoleException() throws IOException {
        // Mock the getRole method to throw an IllegalArgumentException
        when(mockUserDAO.getRole()).thenThrow(new IllegalArgumentException("User not logged in"));
        
        // Call the getRole method and assert that the response has a BAD_REQUEST status
        ResponseEntity<String> actualResponse = userController.getRole();
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
    }

    @Test
    public void testGetCurrentUser() throws IOException {
        // Mock the response entity
        User expectedUser = new User("user", "Password123!");
        ResponseEntity<User> expectedResponse = new ResponseEntity<>(expectedUser, HttpStatus.OK);
        
        // Mock the getCurrentUser method
        when(mockUserDAO.getCurrentUser()).thenReturn(expectedUser);
        
        // Call the getCurrentUser method and assert the response
        ResponseEntity<User> actualResponse = userController.getCurrentUser();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetCurrentUserException() throws IOException {
        // Mock the getCurrentUser method to return null
        when(mockUserDAO.getCurrentUser()).thenReturn(null);
        
        // Call the getCurrentUser method and assert that the response has a BAD_REQUEST status
        ResponseEntity<User> actualResponse = userController.getCurrentUser();
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
    }

    @Test
    public void testUpdatePassword() throws IOException {
        // Mock the response entity
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Password updated", HttpStatus.OK);
        
        // Call the updatePassword method and assert the response
        ResponseEntity<String> actualResponse = userController.updatePassword("Password123!");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testUpdatePasswordException() throws IOException {
        // Mock the updatePassword method to throw an IllegalArgumentException
        doThrow(new IllegalArgumentException("Password is less than 8 characters")).when(mockUserDAO).updatePassword("p@s5");
        
        // Call the updatePassword method and assert that the response has a BAD_REQUEST status
        ResponseEntity<String> actualResponseLen = userController.updatePassword("p@s5");
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseLen.getStatusCode());

        doThrow(new IllegalArgumentException("Password does not include atleast one uppercase letter")).when(mockUserDAO).updatePassword("password1!");
        ResponseEntity<String> actualResponseUpper = userController.updatePassword("password1!");
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseUpper.getStatusCode());

        doThrow(new IllegalArgumentException("Password does not include atleast one lowercase letter")).when(mockUserDAO).updatePassword("PASSWORD1!");
        ResponseEntity<String> actualResponseLower = userController.updatePassword("PASSWORD1!");
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseLower.getStatusCode());

        doThrow(new IllegalArgumentException("Password does not include atleast one number")).when(mockUserDAO).updatePassword("passWORD!");
        ResponseEntity<String> actualResponseNumber = userController.updatePassword("passWORD!");
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseNumber.getStatusCode());

        doThrow(new IllegalArgumentException("Password does not include a special character like ~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/")).when(mockUserDAO).updatePassword("passWORD1");
        ResponseEntity<String> actualResponseSpecial = userController.updatePassword("passWORD1");
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseSpecial.getStatusCode());
    }

    @Test
    public void testUpdatePasswordIOException() throws IOException {
        // Mock the UserDAO to throw an IOException
        doThrow(new IOException("File error")).when(mockUserDAO).updatePassword(anyString());

        // Call the updatePassword method and assert that the response has an INTERNAL_SERVER_ERROR status
        ResponseEntity<String> actualResponse = userController.updatePassword("Password123");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
    }

    @Test
    public void testUpdatePassword_IOException() throws IOException {
        // Arrange
        String newPassword = "newPassword";
        doThrow(IOException.class).when(mockUserDAO).updatePassword(anyString());

        // Act
        ResponseEntity<String> response = userController.updatePassword(newPassword);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSetAddress() throws IOException {
        // Mock the response entity
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Address updated", HttpStatus.OK);
        
        // Call the setAddress method and assert the response
        ResponseEntity<String> actualResponse = userController.setAddress("123 Main St");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testSetAddressIOException() throws IOException {
        // Arrange
        String address = "123 Main St";
        doThrow(IOException.class).when(mockUserDAO).setAddress(address);

        // Act
        ResponseEntity<String> response = userController.setAddress(address);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSetCreditCardNumber() throws IOException {
        // Mock the response entity
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Credit card number updated", HttpStatus.OK);
        
        // Call the setCreditCardNumber method and assert the response
        ResponseEntity<String> actualResponse = userController.setCreditCardNumber("1234567890123456");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testSetCreditCardNumberIOException() throws IOException {
        // Arrange
        String number = "1234567890123456";
        doThrow(IOException.class).when(mockUserDAO).setCreditCardNumber(number);

        // Act
        ResponseEntity<String> response = userController.setCreditCardNumber(number);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSetCreditCardCCV() throws IOException {
        // Mock the response entity
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Credit card CCV updated", HttpStatus.OK);
        
        // Call the setCreditCardCCV method and assert the response
        ResponseEntity<String> actualResponse = userController.setCreditCardCCV("123");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testSetCreditCardCCVIOException() throws IOException {
        // Arrange
        String ccv = "123";
        doThrow(IOException.class).when(mockUserDAO).setCreditCardCCV(ccv);

        // Act
        ResponseEntity<String> response = userController.setCreditCardCCV(ccv);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteAddressIOException() throws IOException {
        // Arrange
        doThrow(IOException.class).when(mockUserDAO).deleteAddress();

        // Act
        ResponseEntity<String> response = userController.deleteAddress();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteCreditCard() throws IOException {
        // Mock the response entity
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Credit card deleted", HttpStatus.OK);
        
        // Call the deleteCreditCardNumber method and assert the response
        ResponseEntity<String> actualResponse = userController.deleteCreditCard();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testDeleteCreditCardIOException() throws IOException {
        // Arrange
        doThrow(IOException.class).when(mockUserDAO).deleteCreditCard();

        // Act
        ResponseEntity<String> response = userController.deleteCreditCard();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAddress() throws IOException {
        // Mock the response entity
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("123 Main St", HttpStatus.OK);
        
        // Mock the getAddress method
        when(mockUserDAO.getAddress()).thenReturn("123 Main St");
        
        // Call the getAddress method and assert the response
        ResponseEntity<String> actualResponse = userController.getAddress();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetAddressIOException() throws IOException {
        // Arrange
        doThrow(IOException.class).when(mockUserDAO).getAddress();

        // Act
        ResponseEntity<String> response = userController.getAddress();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetCreditCardNumber() throws IOException {
        // Mock the response entity
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("1234567890123456", HttpStatus.OK);
        
        // Mock the getCreditCardNumber method
        when(mockUserDAO.getCreditCardNumber()).thenReturn("1234567890123456");
        
        // Call the getCreditCardNumber method and assert the response
        ResponseEntity<String> actualResponse = userController.getCreditCardNumber();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetCreditCardNumberIOException() throws IOException {
        // Arrange
        doThrow(IOException.class).when(mockUserDAO).getCreditCardNumber();

        // Act
        ResponseEntity<String> response = userController.getCreditCardNumber();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetCreditCardCCV() throws IOException {
        // Mock the response entity
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("123", HttpStatus.OK);
        
        // Mock the getCreditCardCCV method
        when(mockUserDAO.getCreditCardCCV()).thenReturn("123");
        
        // Call the getCreditCardCCV method and assert the response
        ResponseEntity<String> actualResponse = userController.getCreditCardCCV();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetCreditCardCCVIOException() throws IOException {
        // Arrange
        doThrow(IOException.class).when(mockUserDAO).getCreditCardCCV();

        // Act
        ResponseEntity<String> response = userController.getCreditCardCCV();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
