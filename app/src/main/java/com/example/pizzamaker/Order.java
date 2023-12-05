package com.example.pizzamaker;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class defines a pizza Order.
 * @author Patryk Dziedzic, Aveesh Patel
 */
public class Order
{
    private int orderNumber;
    private ArrayList<Pizza> pizzas;
    private double orderTotal;

    /**
     * Getter method which returns the order number of the particular Order
     * @return the order number
     */
    public int getOrderNumber()
    {
        return orderNumber;
    }

    /**
     * Setter method which sets the order number of the particular Order
     * @param orderNumber the order number to set the particular Order to
     */
    protected void setOrderNumber(int orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    /**
     * Getter method to return the ArrayList of pizzas in the Order
     * @return the ArrayList of pizzas in the Order
     */
    public ArrayList<Pizza> getPizzas()
    {
        return pizzas;
    }

    /**
     * Setter method to set the ArrayList of pizzas in the Order
     * @param pizzas the ArrayList of pizzas to set the pizzas instance variable to
     */
    protected void setPizzas(ArrayList<Pizza> pizzas)
    {
        this.pizzas = pizzas;
    }

    /**
     * Getter method that returns the order total
     * @return the order total
     */
    public double getOrderTotal()
    {
        return orderTotal;
    }

    /**
     * Setter method that sets the order total for the Order
     * @param orderTotal the order total to set to
     */
    protected void setOrderTotal(double orderTotal)
    {
        this.orderTotal = orderTotal;
    }

    /**
     * Adds a pizza to the ArrayList of pizzas in the Order
     * @param pizza the pizza to be added
     */
    public void addPizza(Pizza pizza)
    {
        pizzas.add(pizza);
    }

    /**
     * Overridden method which returns the textual representation of an Order
     * @return the textual representation of an Order
     */
    @Override
    public String toString()
    {
        String pizzasText = "Order #" + orderNumber + ":\n\t";
        for(Pizza pizza : pizzas)
        {
            pizzasText += pizza.toString() + "\n\t";
        }
        pizzasText += "----------";
        pizzasText += "\n\tOrder Total: $" + String.format("%.2f", orderTotal);
        return pizzasText;
    }
}
