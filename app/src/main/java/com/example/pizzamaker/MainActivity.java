package com.example.pizzamaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalData globalData = GlobalData.getInstance();
        Order newOrder = new Order();
        newOrder.setPizzas(new ArrayList<>());
        StoreOrders.incrementNextOrderNumber();
        globalData.setCurrOrder(newOrder);
        globalData.setStoreOrders(new StoreOrders());
    }

    /**
     * The event handler for the SpecialtyPizza ImageView click.
     * Add extra data to an Intent object and pass the extra data to the Activity being started.
     * INTKEY is the name to be used to retrieve the extra data NUMBER.
     * @param view the Android View which fired the event.
     */
    public void openSpecialtyPizza(View view) {
        Intent intent = new Intent(this, SpecialtyPizzaActivity.class);
        startActivity(intent);
    }

    public void openBuildYourOwnPizza(View view)
    {
        Intent intent = new Intent(this, BuildYourOwnActivity.class);
        startActivity(intent);
    }

    public void openCurrentOrder(View view) {
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        startActivity(intent);
    }

    public void openStoreOrders(View view) {
        Intent intent = new Intent(this, StoreOrderActivity.class);
        startActivity(intent);
    }
}