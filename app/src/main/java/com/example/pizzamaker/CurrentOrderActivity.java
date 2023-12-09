package com.example.pizzamaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class CurrentOrderActivity extends AppCompatActivity {

    private static GlobalData globalData = GlobalData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);
    }

    public static void createNewOrder() {
        Order currOrder = new Order();
        currOrder.setOrderNumber(StoreOrders.getNextOrderNumber());
        StoreOrders.incrementNextOrderNumber();
        currOrder.setPizzas(new ArrayList<>());

    }
}