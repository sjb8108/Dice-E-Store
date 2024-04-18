package com.estore.api.estoreapi.persistence.Dice;

import java.util.List;
import java.util.Map;

import com.estore.api.estoreapi.model.Dice;

/**
 * Defines the interface for Dice object persistence
 * 
 * @author Mark Luskiewicz
 */
public interface DiceDAO {
    /**
     * Loads the dice from the data source
     * @return the list of dice
     */
    Map<Dice, Integer> loadDice();

    /**
     * Saves the dice to the data source
     * @param diceList the list of dice to save
     */
    void saveDice(List<Dice> diceList);
}
