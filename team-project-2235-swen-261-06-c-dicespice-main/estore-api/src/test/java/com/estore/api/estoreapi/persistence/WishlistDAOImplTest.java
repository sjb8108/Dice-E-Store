package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Wishlist.Wishlist;
import com.estore.api.estoreapi.persistence.Wishlist.WishlistDAOImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class WishlistDAOImplTest {
    private WishlistDAOImpl wishlistDAOImpl;
    private Wishlist wishlist;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        wishlistDAOImpl = new WishlistDAOImpl();
        wishlist = new Wishlist();
        mapper = new ObjectMapper();
    }

    @Test
    public void testSaveWishlist() throws IOException {
        File directory = new File("WishlistData");
        if (!directory.exists()){
            directory.mkdir();
        }

        File file = new File("WishlistData/testUser.json");
        wishlistDAOImpl.saveWishlist(wishlist, "testUser");
        assertTrue(file.exists());
        file.delete(); // cleanup
        directory.delete(); // cleanup
    }

    @Test
    public void testLoadWishlist() throws IOException {
        File directory = new File("WishlistData");
        if (!directory.exists()){
            directory.mkdir();
        }

        File file = new File("WishlistData/testUser.json");
        mapper.writeValue(file, wishlist);
        Wishlist loadedWishlist = wishlistDAOImpl.loadWishlist("testUser");
        assertEquals(wishlist, loadedWishlist);
        file.delete(); // cleanup
        directory.delete(); // cleanup
    }

    @Test
    public void testCreateWishlist() throws IOException {
        File directory = new File("WishlistData");
        if (!directory.exists()){
            directory.mkdir();
        }

        File file = new File("WishlistData/testUser.json");
        wishlistDAOImpl.createWishlist("testUser");
        assertTrue(file.exists());
        file.delete(); // cleanup
        directory.delete(); // cleanup
    }
    
    @Test
    public void testLoadWishlistCreatesWishlistWhenNoneExists() {
        // Define the file path
        String filePath = "WishlistData/testUser.json";

        // Delete the file if it exists to simulate a scenario where the file needs to be created
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        // Assert that the file does not exist
        assertFalse(file.exists());

        // Attempt to load the wishlist for the test user
        wishlistDAOImpl.loadWishlist("testUser");

        // Assert that the file for the wishlist now exists
        assertTrue(file.exists());

        // Cleanup: delete the file
        file.delete();
    }

    @Test
    public void testCreateWishlistCatchBlock() throws IOException {
        // Create a mock WishlistDAOImpl
        WishlistDAOImpl mockWishlistDAOImpl = Mockito.mock(WishlistDAOImpl.class);

        // Make the mock WishlistDAOImpl throw an IOException when its saveWishlist method is called
        Mockito.doThrow(new IOException()).when(mockWishlistDAOImpl).saveWishlist(any(Wishlist.class), anyString());

        // Create a WishlistDAOImpl with the mock WishlistDAOImpl
        WishlistDAOImpl wishlistDAOImpl = new WishlistDAOImpl();

        // Call createWishlist and assert that no exception is thrown
        assertDoesNotThrow(() -> wishlistDAOImpl.createWishlist("testUser"));
    }

}
