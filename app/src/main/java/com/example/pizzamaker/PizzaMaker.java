package com.example.pizzamaker;

/**
 * This class creates an instance of a subclass of Pizza based on the chosen type.
 * @author Patryk Dziedzic, Aveesh Patel
 */
public class PizzaMaker
{
    /**
     * Creates a new Pizza with the provided user input and returns the created Pizza.
     * @param pizzaType The type of pizza
     * @return The created Pizza object.
     */
    public static Pizza createPizza(String pizzaType)
    {
        switch (pizzaType) {
            case "Deluxe":
                return new Deluxe();
            case "Supreme":
                return new Supreme();
            case "Meatzza":
                return new Meatzza();
            case "Pepperoni":
                return new Pepperoni();
            case "Seafood":
                return new Seafood();
            case "BuildYourOwn":
                return new BuildYourOwn();
            default:
                return null;
        }
    }
}