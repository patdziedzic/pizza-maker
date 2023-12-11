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

    /**
     * Correctly initializes the StoreOrders GUI (screen) with all the necessary components to
     * be completely functional
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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

    /**
     * When the thread's control is transferred back to the StoreOrders screen, update the GUI and
     * resume the StoreOrder's associated activity
     */
    @Override
    public void onResume(){
        super.onResume();
        updateGUI();
    }

    /**
     * Updates the GUI to reflect the state of the list of Orders in Store Orders
     */
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

    /**
     * Updates the total and specified pizzas in StoreOrders for the specified Order passed as the param
     * @param selectedOrder the specified Order
     */
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

    /**
     * Gets the selected and chosen Order that the User touched
     * @return the specified Order
     */
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

    /**
     * Clears the pizzas from the spinner when specified
     */
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

    /**
     * Cancels the specified Order from StoreOrders on the click of the associated button
     */
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