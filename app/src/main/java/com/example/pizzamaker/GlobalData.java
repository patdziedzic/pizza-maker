package com.example.pizzamaker;

/**
 * Final class to hold global data using the Singleton Design Pattern.
 */
public final class GlobalData {
    private static GlobalData globalData; //single instance

    //global data to share
    private Pizza currPizza;
    private Order currOrder;
    private StoreOrders storeOrders;

    /**
     * Initializes the GlobalData instance
     */
    private GlobalData() { } //so JVM will not do anything

    /**
     * Gets the GlobalData instance
     * @return the GlobalData instance
     */
    public static synchronized GlobalData getInstance() {
        if (globalData == null)
            globalData = new GlobalData(); //lazy approach
        return globalData;
    }

    /**
     * Gets the current pizza
     * @return the current pizza
     */
    public Pizza getCurrPizza() {
        return currPizza;
    }

    /**
     * Sets the current pizza
     * @param currPizza the pizza to be set to the currPizza
     */
    public void setCurrPizza(Pizza currPizza) {
        this.currPizza = currPizza;
    }

    /**
     * Gets the Current Order
     * @return the Current Order
     */
    public Order getCurrOrder() {
        return currOrder;
    }

    /**
     * Sets the Current Order
     * @param currOrder the order to be set to the Current Order
     */
    public void setCurrOrder(Order currOrder) {
        this.currOrder = currOrder;
    }

    /**
     * Gets the list of Orders in Store Orders
     * @return the list of Orders
     */
    public StoreOrders getStoreOrders() {
        return storeOrders;
    }

    /**
     * Sets the list of Store Orders to the specified Store Orders
     * @param storeOrders the storeOrders to be set to the instance variable storeOrders
     */
    public void setStoreOrders(StoreOrders storeOrders) {
        this.storeOrders = storeOrders;
    }

    /*
        Code to access data:

        GlobalData globalData = GlobalData.getInstance();
        globalData.setCurrPizza("..."); //update currPizza with the setter
        Pizza currPizza = globalData.getCurrPizza(): //get currPizza with the getter
     */
}
