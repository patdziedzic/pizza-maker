package com.example.pizzamaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StoreOrderActivity extends AppCompatActivity
{
    private static GlobalData globalData = GlobalData.getInstance();
    private int orderNum;

    private Spinner orderNoSpinner;
    private TextView orderTotalVal;
    private ListView listOrderPizzas;
    private Button cancelOrderButton;
    private ObservableArrayList<String> selPizzas = new ObservableArrayList<>();
    private ArrayAdapter<String> selPizzasAdapter;
    private ObservableArrayList<String> orderNumbers = new ObservableArrayList<>();
    private ArrayAdapter<String> orderNumbersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order);

        orderNoSpinner = findViewById(R.id.orderNoSpinner);
        orderTotalVal = findViewById(R.id.orderTotalVal);
        listOrderPizzas = findViewById(R.id.listOrderPizzas);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        selPizzasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selPizzas);
        listOrderPizzas.setAdapter(selPizzasAdapter);
        orderNumbersAdapter = new ArrayAdapter(
                this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, orderNumbers);
        orderNoSpinner.setAdapter(orderNumbersAdapter);
        setSpinnerSelectedListener();
        setCancelOrderButtonOnClick();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateGUI();
    }

    private void updateGUI() {
        orderNumbers.clear();
        selPizzas.clear();
        StoreOrders storeOrders = globalData.getStoreOrders();
        ArrayList<Order> ordersList = storeOrders.getOrdersList();

        if (ordersList != null && ordersList.size() > 0) {
            //populate orders spinner, set order total, and populate pizzas list
            for (Order order : ordersList) {
                orderNumbers.add(String.format("%d", order.getOrderNumber()));
            }
            orderNumbersAdapter.notifyDataSetChanged();
            orderNoSpinner.setSelection(0);
            updateTotalAndPizzas(getSelectedOrder());
        }
        else {
            orderNumbersAdapter.notifyDataSetChanged();
            orderTotalVal.setText(String.format("%.2f", 0.00));
            selPizzasAdapter.notifyDataSetChanged();
        }
    }

    private void updateTotalAndPizzas(Order selectedOrder) {
        if (selectedOrder != null) {
            orderTotalVal.setText(String.format("%.2f", selectedOrder.getOrderTotal()));

            ArrayList<Pizza> selectedOrderPizzas = selectedOrder.getPizzas();
            for (Pizza pizza : selectedOrderPizzas) {
                selPizzas.add(pizza.toString());
            }
            selPizzasAdapter.notifyDataSetChanged();
        }
    }

    private Order getSelectedOrder() {
        orderNum = Integer.parseInt(orderNoSpinner.getSelectedItem().toString()); //getselecteditem to string on a null
        StoreOrders storeOrders = globalData.getStoreOrders();
        ArrayList<Order> ordersList = storeOrders.getOrdersList();
        Order selectedOrder = null;
        for (Order order : ordersList) {
            if (order.getOrderNumber() == orderNum) {
                selectedOrder = order;
                break;
            }
        }
        return selectedOrder;
    }

    private void setSpinnerSelectedListener() {
        orderNoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selPizzas.clear();
                updateTotalAndPizzas(getSelectedOrder()); //getselecteditem to string on a null
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void setCancelOrderButtonOnClick()
    {
        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(cancelOrderButton.getContext());
                alert.setTitle("Cancel Order?");
                alert.setMessage("Order #" + orderNoSpinner.getSelectedItem().toString()); //getselecteditem to string on a null
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //remove selected order from orders list
                        orderNum = Integer.parseInt(orderNoSpinner.getSelectedItem().toString()); //getselecteditem to string on a null
                        StoreOrders storeOrders = globalData.getStoreOrders();
                        ArrayList<Order> ordersList = storeOrders.getOrdersList();
                        for (Order order : ordersList) {
                            if (order.getOrderNumber() == orderNum) {
                                storeOrders.removeOrder(order);
                                break;
                            }
                        }
                        globalData.setStoreOrders(storeOrders);
                        updateGUI();
                        Toast.makeText(getApplicationContext(),
                                "Order #" + orderNoSpinner.getSelectedItem().toString()
                                        +" canceled.", Toast.LENGTH_LONG).show();
                    }
                    //handle the "NO" click
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "Order not canceled.", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }


}