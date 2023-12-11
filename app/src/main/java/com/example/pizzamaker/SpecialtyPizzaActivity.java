package com.example.pizzamaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class SpecialtyPizzaActivity extends AppCompatActivity {

    //Declare an instance of ArrayList to hold the pizzas to be display with the RecyclerView
    private ArrayList<Pizza> pizzas = new ArrayList<>();
    private int [] pizzaImages = {R.drawable.deluxe, R.drawable.meatzza, R.drawable.mediterranean,
            R.drawable.mightyharvest, R.drawable.pepperoni, R.drawable.savoryfusion, R.drawable.seafood,
            R.drawable.sizzlingdelight, R.drawable.supreme, R.drawable.veggiefiesta};

    /**
     * Correctly sets up the necessary components of the SpecialtyPizza screen so the user
     * can order a Specialty Pizza
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_pizza);
        RecyclerView rcView = findViewById(R.id.rcView_specialtyPizza);
        setupPizzas(); //add the list of pizzas to the ArrayList
        SpecialtyPizzaAdapter adapter = new SpecialtyPizzaAdapter(this, pizzas); //create the adapter
        rcView.setAdapter(adapter); //bind the list of items to the RecyclerView
        //use the LinearLayout for the RecyclerView
        rcView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Helper method to set up the data (the Model of the MVC).
     */
    private void setupPizzas() {
        String [] pizzaNames = getResources().getStringArray(R.array.pizzaNames);
        for (int i = 0; i < pizzaNames.length; i++) {
            Pizza pizza = PizzaMaker.createPizza(pizzaNames[i]);
            pizza.size = Size.SMALL;
            pizza.image = pizzaImages[i];
            pizzas.add(pizza);
        }
    }
}