package com.example.pizzamaker;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class defines a Seafood Pizza.
 * @author Patryk Dziedzic, Aveesh Patel
 */
public class Seafood extends Pizza
{
    /**
     * No parameter constructor which initializes the predefined states (sauce
     * and toppings) of a Seafood pizza
     */
    public Seafood()
    {
        sauce = Sauce.ALFREDO;
        toppings = new ArrayList<>(Arrays.asList(Topping.SHRIMP, Topping.SQUID, Topping.CRAB_MEATS));
    }

    /**
     * Overridden method which calculates and returns the price of a Seafood pizza
     * @return a double depicting the price of the pizza
     */
    @Override
    public double price()
    {
        double SM = 17.99;
        double MD = SM + 2;
        double LG = SM + 4;
        double extraCheeseAmt = 1;
        double extraSauceAmt = 1;

        double price;
        switch (size) {
            case SMALL:
                price = SM;
                break;
            case MEDIUM:
                price = MD;
                break;
            case LARGE:
                price = LG;
                break;
            default:
                throw new IllegalArgumentException();
        }

        if(extraCheese) price += extraCheeseAmt;
        if(extraSauce) price += extraSauceAmt;

        return price;
    }
}
