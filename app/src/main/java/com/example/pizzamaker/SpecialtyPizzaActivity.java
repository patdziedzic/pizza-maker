package com.example.pizzamaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class SpecialtyPizzaActivity extends AppCompatActivity {

    //Declare an instance of ArrayList to hold the pizzas to be display with the RecyclerView
    private ArrayList<Pizza> pizzas = new ArrayList<>();
    private int [] pizzaImages = {R.drawable.deluxe, R.drawable.meatzza, R.drawable.pepperoni,
        R.drawable.seafood, R.drawable.supreme,
            R.drawable.deluxe, R.drawable.meatzza, R.drawable.pepperoni,
            R.drawable.seafood, R.drawable.supreme};
    //^ include the 5 new pizza images as well

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
        /*
         * Item names are defined in a String array under res/string.xml.
         * Your item names might come from other places, such as an external file, or the database
         * from the backend.
         */
        String [] pizzaNames = getResources().getStringArray(R.array.pizzaNames);
        /* Add the items to the ArrayList.
         * Note that I use hardcoded prices for demo purpose. This should be replace by other
         * data sources.
         */
        for (int i = 0; i < pizzaNames.length; i++) {
            Pizza pizza = PizzaMaker.createPizza(pizzaNames[i]);
            pizza.size = Size.SMALL;
            //add corresponding images as well
            pizza.image = pizzaImages[i];
            pizzas.add(pizza);
        }
    }
}