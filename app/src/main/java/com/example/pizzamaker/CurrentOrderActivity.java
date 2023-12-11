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
import java.util.List;

public class CurrentOrderActivity extends AppCompatActivity
{
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

    }

    public static void createNewOrder() {
        Order currOrder = new Order();
        currOrder.setOrderNumber(StoreOrders.getNextOrderNumber());
        StoreOrders.incrementNextOrderNumber();
        currOrder.setPizzas(new ArrayList<>());
    }

    private void removePizza()
    {
        selPizzasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selPizzas);
        listPizzas = findViewById(R.id.listPizzas);
        listPizzas.setAdapter(selPizzasAdapter);

        listPizzas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(listPizzas.getContext());
                alert.setTitle("Remove Pizza?");
                alert.setMessage(listPizzas.getAdapter().getItem(position).toString());

                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String clickedTopping = parent.getAdapter().getItem(position).toString();
                        selPizzas.remove(parent);
                        selPizzasAdapter.notifyDataSetChanged();

                        Toast.makeText(getApplicationContext(), clickedTopping + " Removed.", Toast.LENGTH_SHORT).show();
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

    private void updatePrices()
    {

    }

    private void addToStoreOrdersButtonOnClick()
    {
        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(addOrderButton.getContext());
                alert.setTitle("Add to Store Orders?");
                //alert.setMessage();

                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //createNewOrder(); IDK if we add this here
                        Order order = globalData.getCurrOrder();
                        StoreOrders storeOrders = globalData.getStoreOrders();
                        storeOrders.addOrder(order);
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