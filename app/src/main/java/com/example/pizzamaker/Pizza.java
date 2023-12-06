package com.example.pizzamaker;

import java.util.ArrayList;

/**
 * This abstract class defines a Pizza.
 * @author Patryk Dziedzic, Aveesh Patel
 */
public abstract class Pizza
{
    protected ArrayList<Topping> toppings; //Topping is an enum class
    protected Size size; //Size is an enum class
    protected Sauce sauce; //Sauce is an enum class
    protected boolean extraSauce;
    protected boolean extraCheese;
    protected int image;

    /**
     * Abstract method that is required to be overridden by Pizza subclasses
     * to define the price of a particular pizza
     * @return the price of the pizza
     */
    public abstract double price(); //polymorphism

    /**
     * Overridden method which returns the textual representation of a particular Pizza
     * @return the textual representation of a particular Pizza
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(getClass().getSimpleName()).append("] ");

        sb.append(size.getName() + " Pizza w/ ");
        sb.append(sauce.getName() + " Sauce; ");

        for (int i = 0; i < toppings.size(); i++) {
            sb.append(toppings.get(i).getName());
            if (i < toppings.size() - 1) sb.append(", ");
        }

        if (extraSauce) sb.append(", Extra Sauce");
        if (extraCheese) sb.append(", Extra Cheese");

        sb.append(": $").append(String.format("%.2f", price()));
        return sb.toString();
    }
}
