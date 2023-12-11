package com.example.pizzamaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

/**
 * This class defines the main Android Activity for navigating between other Activities.
 * @author Patryk Dziedzic, Aveesh Patel
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Sets the necessary and specified elements for the Main Screen and initializes
     * the proper elements for GlobalData
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalData globalData = GlobalData.getInstance();

        Order newOrder = new Order();
        newOrder.setOrderNumber(StoreOrders.getNextOrderNumber());
        StoreOrders.incrementNextOrderNumber();
        newOrder.setPizzas(new ArrayList<>());
        globalData.setCurrOrder(newOrder);

        globalData.setStoreOrders(new StoreOrders());
    }

    /**
     * The event handler for the SpecialtyPizza ImageView click.
     * @param view the Android View which fired the event.
     */
    public void openSpecialtyPizza(View view) {
        Intent intent = new Intent(this, SpecialtyPizzaActivity.class);
        startActivity(intent);
    }

    /**
     * The event handler for the BuildYourOwnPizza ImageView click
     * @param view the Android View which fired and began the event
     */
    public void openBuildYourOwnPizza(View view)
    {
        Intent intent = new Intent(this, BuildYourOwnActivity.class);
        startActivity(intent);
    }

    /**
     * The event handler for the CurrentOrder ImageView click
     * @param view the Android View which fired and began the event
     */
    public void openCurrentOrder(View view) {
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        startActivity(intent);
    }

    /**
     * The event handler for the BuildYourOwnPizza ImageView click
     * @param view the Android View which fired and began the event
     */
    public void openStoreOrders(View view) {
        Intent intent = new Intent(this, StoreOrderActivity.class);
        startActivity(intent);
    }
}