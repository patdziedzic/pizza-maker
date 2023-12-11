package com.example.pizzamaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CurrentOrderActivity extends AppCompatActivity
{
    private final double SALES_TAX = 0.06625;
    private static GlobalData globalData = GlobalData.getInstance();

    private TextView orderNum;

    private ListView listPizzas;

    private TextView subtotalValue;

    private TextView salesTaxValue;

    private TextView orderTotalValue;

    private Button addOrderButton;

    private ObservableArrayList<String> selPizzas = new ObservableArrayList<>();

    private ArrayAdapter<String> selPizzasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        orderNum = findViewById(R.id.orderNum);
        listPizzas = findViewById(R.id.listPizzas);
        subtotalValue = findViewById(R.id.subtotalValue);
        salesTaxValue = findViewById(R.id.salesTaxValue);
        orderTotalValue = findViewById(R.id.orderTotalValue);
        addOrderButton = findViewById(R.id.addOrderButton);
        selPizzasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selPizzas);
        listPizzas.setAdapter(selPizzasAdapter);
        setListPizzasToRemoveOnClick();
        setAddToStoreOrdersButtonOnClick();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateGUI();
    }

    /**
     * Update GUI based on currOrder
     */
    private void updateGUI() {
        selPizzas.clear();
        Order currOrder = globalData.getCurrOrder();
        orderNum.setText(String.format("%d", currOrder.getOrderNumber()));

        ArrayList<Pizza> currOrderPizzas = currOrder.getPizzas();
        if (currOrderPizzas != null && currOrderPizzas.size() > 0) {
            //set the prices and the list of pizzas
            double subtotal = 0.0;
            for (Pizza pizza : currOrderPizzas) {
                selPizzas.add(pizza.toString());
                subtotal += pizza.price();
            }
            selPizzasAdapter.notifyDataSetChanged();
            subtotalValue.setText(String.format("%.2f", subtotal));
            double tax = subtotal * SALES_TAX;
            salesTaxValue.setText(String.format("%.2f", tax));
            double total = subtotal + tax;
            orderTotalValue.setText(String.format("%.2f", total));
            currOrder.setOrderTotal(total);

            globalData.setCurrOrder(currOrder);
        }
        else {
            selPizzasAdapter.notifyDataSetChanged();
            subtotalValue.setText(String.format("%.2f", 0.00));
            salesTaxValue.setText(String.format("%.2f", 0.00));
            orderTotalValue.setText(String.format("%.2f", 0.00));
        }
    }

    private void createNewOrder() {
        Order currOrder = new Order();
        currOrder.setOrderNumber(StoreOrders.getNextOrderNumber());
        StoreOrders.incrementNextOrderNumber();
        currOrder.setPizzas(new ArrayList<>());
        globalData.setCurrOrder(currOrder);
        updateGUI();
    }

    private void setListPizzasToRemoveOnClick()
    {
        listPizzas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(listPizzas.getContext());
                alert.setTitle("Remove Pizza?");
                alert.setMessage(listPizzas.getAdapter().getItem(position).toString());
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String clickedPizza = parent.getAdapter().getItem(position).toString();
                        selPizzas.remove(position);
                        selPizzasAdapter.notifyDataSetChanged();
                        removePizzaFromGlobalOrder(clickedPizza);
                        Toast.makeText(getApplicationContext(), clickedPizza + " Removed.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) { }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }

    private void removePizzaFromGlobalOrder(String clickedPizza) {
        Order currOrder = globalData.getCurrOrder();
        ArrayList<Pizza> currOrderPizzas = currOrder.getPizzas();
        for (Pizza pizza : currOrderPizzas) {
            if (pizza.toString().equals(clickedPizza)) {
                currOrderPizzas.remove(pizza);
                currOrder.setPizzas(currOrderPizzas);
                globalData.setCurrOrder(currOrder);
                updateGUI();
                return;
            }
        }
    }

    private void setAddToStoreOrdersButtonOnClick()
    {
        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(addOrderButton.getContext());
                alert.setTitle("Add to Store Orders?");
                alert.setMessage("Order #" + orderNum.getText().toString());
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Order order = globalData.getCurrOrder();
                        StoreOrders storeOrders = globalData.getStoreOrders();
                        storeOrders.addOrder(order);
                        createNewOrder();
                        globalData.setStoreOrders(storeOrders);
                        Toast.makeText(getApplicationContext(),
                                "Order added to store orders.", Toast.LENGTH_LONG).show();
                    }
                    //handle the "NO" click
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "Order not added to store orders.", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }


}