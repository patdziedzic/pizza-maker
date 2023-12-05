package com.example.pizzamaker;

/**
 * This enum class describes the types of pizza toppings.
 * @author Patryk Dziedzic, Aveesh Patel
 */
public enum Topping
{
    //Requirements from Project Specs
    SAUSAGE("Sausage"),
    PEPPERONI("Pepperoni"),
    HAM("Ham"),
    BEEF("Beef"),
    SHRIMP("Shrimp"),
    SQUID("Squid"),
    CRAB_MEATS("Crab Meats"),
    GREEN_PEPPER("Green Pepper"),
    ONION("Onion"),
    MUSHROOM("Mushroom"),
    BLACK_OLIVE("Black Olive"),

    //Additional Toppings
    CHICKEN("Chicken"),
    BASIL("Basil");

    private final String name;

    /**
     * Parameterized constructor for the Topping class which initializes the name
     * @param name the name
     */
    Topping(String name)
    {
        this.name = name;
    }

    /**
     * Returns the name of a given enum
     * @return the name of the given enum
     */
    public String getName()
    {
        return name;
    }
}
