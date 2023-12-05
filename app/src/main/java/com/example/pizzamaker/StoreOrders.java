package com.example.pizzamaker;

import java.util.ArrayList;

/**
 * This class defines a StoreOrders object to contain all the store's orders.
 * @author Patryk Dziedzic, Aveesh Patel
 */
public class StoreOrders
{
    private ArrayList<Order> ordersList;
    private static int nextOrderNumber;

    /**
     * No-parameter constructor which initializes the ordersList to an empty ArrayList of type Order
     */
    public StoreOrders()
    {
        ordersList = new ArrayList<>();
    }

    /**
     * Getter method which returns the ordersList
     * @return the ordersList
     */
    public ArrayList<Order> getOrdersList()
    {
        return ordersList;
    }

    /**
     * Adds an order to ordersList
     * @param order the order to be added
     */
    protected void addOrder(Order order)
    {
        ordersList.add(order);
    }

    /**
     * Removes an order from ordersList
     * @param order the order to be removed
     */
    public void removeOrder(Order order)
    {
        ordersList.remove(order);
    }

    /**
     * Gets the next orderNumber
     * @return the integer depicting the nextOrderNumber
     */
    public static int getNextOrderNumber()
    {
        return nextOrderNumber;
    }

    /**
     * Sets the nextOrderNumber as specified by the integer parameter
     * @param nextOrderNumber the integer for the nextOrderNumber
     */
    public static void setNextOrderNumber(int nextOrderNumber)
    {
        StoreOrders.nextOrderNumber = nextOrderNumber;
    }

    /**
     * Increments the static nextOrderNumber variable so no two orders
     * have the same number
     */
    public static void incrementNextOrderNumber()
    {
        StoreOrders.nextOrderNumber++;
    }
}
