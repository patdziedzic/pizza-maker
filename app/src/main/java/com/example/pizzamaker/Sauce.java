package com.example.pizzamaker;

/**
 * This enum class describes the types of pizza sauces.
 * @author Patryk Dziedzic, Aveesh Patel
 */
public enum Sauce
{
    TOMATO("Tomato"),
    ALFREDO("Alfredo");

    private final String name;

    /**
     * Parameterized constructor for the Sauce enum which initializes the name of the Sauce
     * @param name the name of the Sauce
     */
    Sauce(String name)
    {
        this.name = name;
    }

    /**
     * Returns the name of a given Sauce enum
     * @return the name of the given Sauce enum
     */
    public String getName()
    {
        return name;
    }
}
