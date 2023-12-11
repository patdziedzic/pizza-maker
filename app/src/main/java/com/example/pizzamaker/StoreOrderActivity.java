package com.example.pizzamaker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class StoreOrderActivity extends AppCompatActivity
{
    private static GlobalData globalData = GlobalData.getInstance();

    private Spinner orderNoSpinner;

    private TextView orderTotalVal;

    private ListView listOrderPizzas;

    private Button cancelOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order);

        orderNoSpinner = findViewById(R.id.orderNoSpinner);
        orderTotalVal = findViewById(R.id.orderTotalVal);
        listOrderPizzas = findViewById(R.id.listOrderPizzas);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
    }


}