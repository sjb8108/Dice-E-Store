package com.estore.api.estoreapi.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.estore.api.estoreapi.model.Wishlist.ListItem;
import com.estore.api.estoreapi.model.Wishlist.Wishlist;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WishlistTest {
    private Wishlist wishlist;
    private InventoryService inventoryService;
    private Dice dice;

    @BeforeEach
    public void setUp() {
        wishlist = new Wishlist();
        inventoryService = Mockito.mock(InventoryService.class);
        dice = new Dice(1, "Red", 6, 4.99);
        dice.setId(1);
        dice.setColor("Red");
        dice.setSides(6);
        dice.setPrice(4.99);
    }

    @Test
    public void testaddDice() {
        Mockito.when(inventoryService.getDie(dice.getId())).thenReturn(dice);
        wishlist.addDice(dice, inventoryService);
        assertEquals(1, wishlist.getSizeOfWishlist());
    }

    @Test
    public void testaddDice_IllegalArgumentException() {
        Mockito.when(inventoryService.getDie(dice.getId())).thenReturn(dice);
        wishlist.addDice(dice, inventoryService);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> wishlist.addDice(dice, inventoryService));
        String expectedMessage = "This dice is already in the wishlist";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
}


    @Test
    public void testRemoveDice() {
        Mockito.when(inventoryService.getDie(dice.getId())).thenReturn(dice);
        wishlist.addDice(dice, inventoryService);
        wishlist.removeDice(dice);
        assertEquals(0, wishlist.getSizeOfWishlist());
    }




    @Test
    public void testClear() {
        Mockito.when(inventoryService.getDie(dice.getId())).thenReturn(dice);
        wishlist.addDice(dice, inventoryService);
        wishlist.clear();
        assertEquals(0, wishlist.getSizeOfWishlist());
    }

    @Test
    public void testGetWishlist() {
        Mockito.when(inventoryService.getDie(dice.getId())).thenReturn(dice);
        wishlist.addDice(dice, inventoryService);
        List<ListItem> items = wishlist.getWishlist();
        assertEquals(1, items.size());
        assertEquals(dice, items.get(0).getDice());
    }

    @Test
    public void testGetSizeOfWishlist() {
        Mockito.when(inventoryService.getDie(dice.getId())).thenReturn(dice);
        wishlist.addDice(dice, inventoryService);
        assertEquals(1, wishlist.getSizeOfWishlist());
    }

    @Test
    public void testToString() {
        Mockito.when(inventoryService.getDie(dice.getId())).thenReturn(dice);
        wishlist.addDice(dice, inventoryService);
        String expected = "Wishlist [items=[Dice{id=1, color='Red', sides=6, price=4.99}]]";
        assertEquals(expected, wishlist.toString());
    }



}