package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Dice;
import com.estore.api.estoreapi.model.InventoryService;

import com.estore.api.estoreapi.model.Wishlist.Wishlist;
import com.estore.api.estoreapi.model.Wishlist.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

    @Tag("Controller-tier")
public class WishlistControllerTest {
    private WishlistController wishlistController;
    private WishlistService mockWishlistService;
    private Wishlist mockWishlist;
    private InventoryService mockInventory;


    /**
     * Before each test, create a new DiceController object and inject
     * a mock Dice DAO
     */
    @BeforeEach
    public void setupListController() {
        mockWishlistService = mock(WishlistService.class);
        mockWishlist = mock(Wishlist.class);
        mockInventory = mock(InventoryService.class);
        wishlistController = new WishlistController(mockWishlistService,mockInventory);
    }

    @Test
    public void testGetList() {
        when(mockWishlistService.loadUserList()).thenReturn(mockWishlist);
        Wishlist result = wishlistController.getList().getBody();
        assertEquals(mockWishlist,result);
    }

    @Test
    public void testAddDieToList() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockWishlistService.loadUserList()).thenReturn(mockWishlist);
        doNothing().when(mockWishlistService).addDieToList(mockWishlist, die);

        ResponseEntity<Wishlist> result = wishlistController.addDieToList(die.getId());

        verify(mockInventory).getDie(die.getId());
        verify(mockWishlistService).addDieToList(mockWishlist, die);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockWishlist, result.getBody());
    }



    @Test
    public void testDeleteDieFromList() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockWishlistService.loadUserList()).thenReturn(mockWishlist);
        doNothing().when(mockWishlistService).removeDiceFromList(mockWishlist, die);
    
        ResponseEntity<Wishlist> result = wishlistController.deleteDieFromList(die.getId());
    
        verify(mockInventory).getDie(die.getId());
        verify(mockWishlistService).removeDiceFromList(mockWishlist, die);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockWishlist, result.getBody());
    }

    @Test
    public void testDeleteDieFromList_IllegalArgumentException() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockWishlistService.loadUserList()).thenReturn(mockWishlist);
        doThrow(IllegalArgumentException.class).when(mockWishlistService).removeDiceFromList(mockWishlist, die);
    
        ResponseEntity<Wishlist> result = wishlistController.deleteDieFromList(die.getId());
    
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteDieFromList_IOException() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockWishlistService.loadUserList()).thenReturn(mockWishlist);
        doThrow(IOException.class).when(mockWishlistService).removeDiceFromList(mockWishlist, die);
    
        ResponseEntity<Wishlist> result = wishlistController.deleteDieFromList(die.getId());
    
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }   


    @Test
    public void testAddDieToList_IllegalArgumentException() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockWishlistService.loadUserList()).thenReturn(mockWishlist);
        doThrow(IllegalArgumentException.class).when(mockWishlistService).addDieToList(mockWishlist, die);
    
        ResponseEntity<Wishlist> result = wishlistController.addDieToList(die.getId());
    
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }


        @Test
    public void testAddDieToList_NotFound() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(null);

        ResponseEntity<Wishlist> result = wishlistController.addDieToList(die.getId());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteDieFromList_NotFound() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(null);

        ResponseEntity<Wishlist> result = wishlistController.deleteDieFromList(die.getId());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
        


    @Test
    public void testAddDieToList_Exception() throws IOException {
        Dice die = new Dice(1, "red", 6, 4.99);
        when(mockInventory.getDie(die.getId())).thenReturn(die);
        when(mockWishlistService.loadUserList()).thenThrow(RuntimeException.class);
    
        ResponseEntity<Wishlist> result = wishlistController.addDieToList(die.getId());
    
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }    







    @Test
    public void testClearWishlist() throws IOException {
        when(mockWishlistService.loadUserList()).thenReturn(mockWishlist);

        ResponseEntity<Wishlist> response = wishlistController.clearWishlist();

        verify(mockWishlistService, times(1)).clearAllDiceFromList(mockWishlist);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockWishlist, response.getBody());
    }


    @Test
    public void testClearWishlist_IOException() throws IOException {
        when(mockWishlistService.loadUserList()).thenReturn(mockWishlist);
        doThrow(IOException.class).when(mockWishlistService).clearAllDiceFromList(mockWishlist);
    
        ResponseEntity<Wishlist> result = wishlistController.clearWishlist();
    
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }


}