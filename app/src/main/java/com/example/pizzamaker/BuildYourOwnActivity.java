package com.example.pizzamaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BuildYourOwnActivity extends AppCompatActivity {

    private ListView additionalToppingsList;
    ObservableArrayList<String> addToppings = new ObservableArrayList<>();
    private ArrayAdapter<String> addTopAdapter;
    private ListView selectedToppingsList;
    ObservableArrayList<String> selToppings = new ObservableArrayList<>();
    private ArrayAdapter<String> selTopAdapter;

    /**
     * Initial setup for the Views and the adapter for the ListView
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_your_own);

        setupAdditionalToppings();
        setupSelectedToppings();
    }

    private void setupAdditionalToppings() {
        Topping[] enumToppings = Topping.values();
        String[] allStringToppings = new String[enumToppings.length];
        for (int i = 0; i < enumToppings.length; i++) {
            allStringToppings[i] = enumToppings[i].getName();
        }
        Collections.addAll(addToppings, allStringToppings);

        addTopAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addToppings);
        additionalToppingsList = findViewById(R.id.additionalToppingsList);
        additionalToppingsList.setAdapter(addTopAdapter);
        additionalToppingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(additionalToppingsList.getContext());
                alert.setTitle("Add Topping");
                alert.setMessage(adapterView.getAdapter().getItem(i).toString());
                //anonymous inner class to handle the onClick event of YES or NO.
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String clickedTopping = adapterView.getAdapter().getItem(i).toString();
                        addToppings.remove(i);
                        addTopAdapter.notifyDataSetChanged();
                        selToppings.add(clickedTopping);
                        selTopAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), clickedTopping + " Added.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }

    private void setupSelectedToppings() {
        selTopAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selToppings);
        selectedToppingsList = findViewById(R.id.selectedToppingsList);
        selectedToppingsList.setAdapter(selTopAdapter);
        selectedToppingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(selectedToppingsList.getContext());
                alert.setTitle("Remove Topping");
                alert.setMessage(adapterView.getAdapter().getItem(i).toString());
                //anonymous inner class to handle the onClick event of YES or NO.
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String clickedTopping = adapterView.getAdapter().getItem(i).toString();
                        selToppings.remove(i);
                        selTopAdapter.notifyDataSetChanged();
                        addToppings.add(clickedTopping);
                        addTopAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), clickedTopping + " Removed.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }
}