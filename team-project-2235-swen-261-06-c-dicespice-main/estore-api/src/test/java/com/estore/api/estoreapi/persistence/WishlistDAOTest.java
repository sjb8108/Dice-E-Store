package com.estore.api.estoreapi.persistence;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.estore.api.estoreapi.model.Wishlist.Wishlist;
import com.estore.api.estoreapi.persistence.Wishlist.WishlistDAO;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WishlistDAOTest {
    private WishlistDAO wishlistDAO;
    private Wishlist wishlist;

    @BeforeEach
    public void setUp() {
        wishlistDAO = Mockito.mock(WishlistDAO.class);
        wishlist = new Wishlist();
    }

    @Test
    public void testSaveWishlist() throws IOException {
        Mockito.doNothing().when(wishlistDAO).saveWishlist(wishlist, "username");
        wishlistDAO.saveWishlist(wishlist, "username");
        Mockito.verify(wishlistDAO, Mockito.times(1)).saveWishlist(wishlist, "username");
    }

    @Test
    public void testLoadWishlist() {
        Mockito.when(wishlistDAO.loadWishlist("username")).thenReturn(wishlist);
        Wishlist loadedWishlist = wishlistDAO.loadWishlist("username");
        assertEquals(wishlist, loadedWishlist);
    }

    @Test
    public void testCreateWishlist() {
        Mockito.doNothing().when(wishlistDAO).createWishlist("username");
        wishlistDAO.createWishlist("username");
        Mockito.verify(wishlistDAO, Mockito.times(1)).createWishlist("username");
    }
}