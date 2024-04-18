package com.estore.api.estoreapi.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Dice entity
 * 
 * @author Scott Bullock
 */
public class Dice {

    // Package private for tests
    static final String STRING_FORMAT = "Dice [id=%d, color=%s, sides=%d, price=%f]";

    @JsonProperty("id") private int id;
    @JsonProperty("color") private String color;
    @JsonProperty("sides") private int sides;
    @JsonProperty("price") private double price;

    /**
     * Create a Dice with the given id and sides
     * @param id The id of the dice
     * @param color The color of the dice
     * @param sides the number of sides on the dice
     * @param price the price of the dice
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Dice(@JsonProperty("id") int id, @JsonProperty("color") String color, @JsonProperty("sides") int sides, @JsonProperty("price") double price) {
        this.id = id;
        this.color = color;
        this.sides = sides;
        this.price = price;
    }

    /**
     * Retrieves the id of the die
     * @return The id of the die
     */
    public int getId() {return id;}

    /**
     * Sets the id of the dice - necessary for JSON object to Java object deserialization
     * @param id The id of the dice
     */
    public void setId(int id) {this.id = id;}

    /**
     * Sets the color of the dice - necessary for JSON object to Java object deserialization
     * @param color The name of the dice
     */
    public void setColor(String color) {this.color = color;}

    /**
     * Retrieves the color of the dice
     * @return The color of the die
     */
    public String getColor() {return color;}

    /**
     * Sets the sides of the dice - necessary for JSON object to Java object deserialization
     * @param sides The number of sides on the die
     */
    public void setSides(int sides) {this.sides = sides;}

    /**
     * Retrieves the number of sides on the die
     * @return The number of sides on the die
     */
    public int getSides() {return sides;}
    
    /**
     * Sets the price of the die - necessary for JSON object to Java object deserialization
     * @param sides The price of the die
     */
    public void setPrice(double price) {this.price = price;}

    /**
     * Retrieves the price of the die
     * @return The price of the die
     */
    public double getPrice() {return price;}

    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,color,sides,price);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof Dice) {
            Dice d = (Dice) o;
            return d.color.equals(color) && d.sides == sides && d.price == price;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, sides, price);
    }
}