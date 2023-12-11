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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class defines an Android Activity for building a BuildYourOwn Pizza.
 * @author Patryk Dziedzic, Aveesh Patel
 */
public class BuildYourOwnActivity extends AppCompatActivity {

    private final int MIN_TOPPINGS = 3;
    private final int MAX_TOPPINGS = 7;

    private static GlobalData globalData = GlobalData.getInstance();
    private TextView buildYourOwnText;
    private Spinner pizzaSizeSpinner;
    private RadioButton tomatoSauceButton, alfredoSauceButton;
    private CheckBox extraSauceCheckBox, extraCheeseCheckBox;
    private EditText priceTextField;
    private ListView additionalToppingsList;
    ObservableArrayList<String> addToppings = new ObservableArrayList<>();
    private ArrayAdapter<String> addTopAdapter;
    private ListView selectedToppingsList;
    ObservableArrayList<String> selToppings = new ObservableArrayList<>();
    private ArrayAdapter<String> selTopAdapter;
    private Button addToOrderButton;

    /**
     * Initial setup for the Views and the adapter for the ListView
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_your_own);

        buildYourOwnText = findViewById(R.id.buildYourOwnText);
        pizzaSizeSpinner = findViewById(R.id.pizzaSizeSpinner);
        tomatoSauceButton = findViewById(R.id.tomatoSauceButton);
        alfredoSauceButton = findViewById(R.id.alfredoSauceButton);
        extraSauceCheckBox = findViewById(R.id.extraSauceCheckBox);
        extraCheeseCheckBox = findViewById(R.id.extraCheeseCheckBox);
        priceTextField = findViewById(R.id.priceTextField);
        addToOrderButton = findViewById(R.id.addToOrderButton);
        setupAdditionalToppings();
        setupSelectedToppings();
        setOnPizzaChange();
        setAddButtonOnClick();
    }

    /**
     * Takes the list of Toppings in the Topping enum and populates all of the
     * Toppings in the additionalToppingsList ListView, as well as giving
     * functionality to adding a Topping to the selectedToppingsList ListView
     */
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
                if (selToppings.size() == MAX_TOPPINGS)
                    Toast.makeText(getApplicationContext(), "Max Toppings Reached.", Toast.LENGTH_LONG).show();
                else {
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
                            createPizza();
                            Toast.makeText(getApplicationContext(), clickedTopping + " Added.", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            }
        });
    }

    /**
     * Correctly populates the selectedToppingsList ListView and gives the functionality to
     * remove a selected Topping back to be an additional Topping
     */
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
                        createPizza();
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

    /**
     * When any component of the BuildYourOwn pizza, besides the Toppings, change, create a
     * new pizza with the desired user specifications
     */
    private void setOnPizzaChange() {
        pizzaSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                createPizza();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        tomatoSauceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPizza();
            }
        });
        alfredoSauceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPizza();
            }
        });
        extraSauceCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPizza();
            }
        });
        extraCheeseCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPizza();
            }
        });
    }

    /**
     * Provides functionality for the Add to Order Button by first ensuring the topping restrictions
     * are adhered to and then alerting the user with an AlertDialog and Toast when the pizza
     * is added to the Order
     */
    private void setAddButtonOnClick() {
        addToOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selToppings.size() < MIN_TOPPINGS)
                    Toast.makeText(getApplicationContext(),
                            "Need at least " + MIN_TOPPINGS + " toppings.", Toast.LENGTH_LONG).show();
                else if (selToppings.size() > MAX_TOPPINGS)
                    Toast.makeText(getApplicationContext(), "Exceeded Max Toppings", Toast.LENGTH_LONG).show();
                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(addToOrderButton.getContext());
                    alert.setTitle("Add to Order");
                    alert.setMessage(buildYourOwnText.getText().toString());
                    //handle the "YES" click
                    alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            createPizza();
                            Pizza currPizza = globalData.getCurrPizza();
                            Order currOrder = globalData.getCurrOrder();
                            currOrder.addPizza(currPizza);
                            globalData.setCurrOrder(currOrder);
                            Toast.makeText(getApplicationContext(),
                                    buildYourOwnText.getText().toString() + " added.", Toast.LENGTH_LONG).show();
                        }
                        //handle the "NO" click
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),
                                    buildYourOwnText.getText().toString() + " not added.", Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            }
        });
    }

    /**
     * Uses the PizzaMaker to create a new BuildYourOwn pizza with the specifications
     * specified by the user
     */
    private void createPizza() {
        Pizza currPizza = PizzaMaker.createPizza("BuildYourOwn");
        if (currPizza == null) return;

        String size = pizzaSizeSpinner.getSelectedItem().toString();
        if (size.equals(Size.SMALL.getName())) currPizza.size = Size.SMALL;
        else if (size.equals(Size.MEDIUM.getName())) currPizza.size = Size.MEDIUM;
        else if (size.equals(Size.LARGE.getName())) currPizza.size = Size.LARGE;
        if (tomatoSauceButton.isChecked()) currPizza.sauce = Sauce.TOMATO;
        else if (alfredoSauceButton.isChecked()) currPizza.sauce = Sauce.ALFREDO;
        if (extraSauceCheckBox.isChecked()) currPizza.extraSauce = true;
        if (extraCheeseCheckBox.isChecked()) currPizza.extraCheese = true;

        ArrayList<Topping> selectedToppings = new ArrayList<>();
        for (String strTopping : selToppings) {
            for (Topping topping : Topping.values()) {
                if (topping.getName().equals(strTopping))
                    selectedToppings.add(topping);
            }
        }
        currPizza.toppings = selectedToppings;

        double price = currPizza.price();
        priceTextField.setText(String.format("%.2f", price));

        globalData.setCurrPizza(currPizza);
    }
}