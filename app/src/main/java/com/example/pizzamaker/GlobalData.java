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



    private GlobalData() { } //so JVM will not do anything

    public static synchronized GlobalData getInstance() {
        if (globalData == null)
            globalData = new GlobalData(); //lazy approach
        return globalData;
    }

    public Pizza getCurrPizza() {
        return currPizza;
    }

    public void setCurrPizza(Pizza currPizza) {
        this.currPizza = currPizza;
    }

    public Order getCurrOrder() {
        return currOrder;
    }

    public void setCurrOrder(Order currOrder) {
        this.currOrder = currOrder;
    }

    public StoreOrders getStoreOrders() {
        return storeOrders;
    }

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
