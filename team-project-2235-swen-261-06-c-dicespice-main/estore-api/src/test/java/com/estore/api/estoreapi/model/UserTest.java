package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the User class
 * 
 * @author Mark Luskiewicz & Scott Bullock
 */
@Tag("Model-tier")
public class UserTest {
    @Test
    public void testGetUsername() {
        User user = new User("john", "Password1!");
        assertEquals("john", user.getUsername());
    }

    @Test
    public void testGetRole() {
        User user = new User("admin", "Password1!");
        assertEquals("admin", user.getRole());
    }

    @Test
    public void testChangeUsername() {
        User user = new User("john", "Password1!");
        user.changeUsername("jane");
        assertEquals("jane", user.getUsername());
    }

    @Test
    public void testChangeValidPassword() {
        User user = new User("john", "Password1!");
        assertTrue(user.changePassword("newPass123!"));
        assertEquals("newPass123!", user.getPassword());
    }

    @Test
    public void testChangeInvalidPasswordLength() {
        User user = new User("john", "Password1!");
        try{
            user.changePassword("p@S5");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch(IllegalArgumentException e){
            assertEquals("Password is less than 8 characters", e.getMessage());
        };
    }

    @Test
    public void testChangeInvalidPasswordUppercase(){
        User user = new User("john", "Password1!");
        try{
            user.changePassword("password1!");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch(IllegalArgumentException e){
            assertEquals("Password does not include atleast one uppercase letter", e.getMessage());
        };
    }

    @Test
    public void testChangeInvalidPasswordLowercase(){
        User user = new User("john", "Password1!");
        try{
            user.changePassword("PASSWORD1!");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch(IllegalArgumentException e){
            assertEquals("Password does not include atleast one lowercase letter", e.getMessage());
        };
    }

    @Test
    public void testChangeInvalidPasswordNumber(){
        User user = new User("john", "Password1!");
        try{
            user.changePassword("passWORD!");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch(IllegalArgumentException e){
            assertEquals("Password does not include atleast one number", e.getMessage());
        };
    }

    @Test
    public void testChangeInvalidPasswordSpecial(){
        User user = new User("john", "Password1!");
        try{
            user.changePassword("passWORD1");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch(IllegalArgumentException e){
            assertEquals("Password does not include a special character like ~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/", e.getMessage());
        };
    }

    @Test
    public void testIsValidPassword() {
        assertTrue(User.isValidPassword("Password1!"));
    }

    @Test
    public void testIsValidPasswordLength(){
        try{
            User.isValidPassword("p@S5");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch(IllegalArgumentException e){
            assertEquals("Password is less than 8 characters", e.getMessage());
        };
    }

    @Test
    public void testIsValidPasswordUppercase(){
        try{
            User.isValidPassword("password1!");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch(IllegalArgumentException e){
            assertEquals("Password does not include atleast one uppercase letter", e.getMessage());
        };
    }

    @Test
    public void testIsValidPasswordLowercase(){
        try{
            User.isValidPassword("PASSWORD1!");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch(IllegalArgumentException e){
            assertEquals("Password does not include atleast one lowercase letter", e.getMessage());
        };
    }

    @Test
    public void testIsValidPasswordNumber(){
        try{
            User.isValidPassword("passWORD!");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch(IllegalArgumentException e){
            assertEquals("Password does not include atleast one number", e.getMessage());
        };
    }

    @Test
    public void testIsValidPasswordSpecial(){
        try{
            User.isValidPassword("passWORD1");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch(IllegalArgumentException e){
            assertEquals("Password does not include a special character like ~`! @#$%^&*()_-+={[}]|\\:;\"\'<,>.?/", e.getMessage());
        };
    }

    @Test
    public void testToString() {
        User user = new User("john", "P@ssw0rd1");
        user.setAddress("123 Main St");
        user.setCreditCardNumber("1234567812345678");
        user.setCreditCardCCV("123");
        String expected = "User [username=john, role=customer, password=P@ssw0rd1, address=123 Main St, creditCardNumber=1234567812345678, creditCardCCV=123]";
        assertEquals(expected, user.toString());
    }
}
