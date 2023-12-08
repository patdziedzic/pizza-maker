package com.example.pizzamaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * The event handler for the SpecialtyPizza ImageView click.
     * Add extra data to an Intent object and pass the extra data to the Activity being started.
     * INTKEY is the name to be used to retrieve the extra data NUMBER.
     * @param view the Android View which fired the event.
     */
    public void openSpecialtyPizza(View view) {
        Intent intent = new Intent(this, SpecialtyPizzaActivity.class);
        //intent.putExtra("INTKEY", NUMBER); //the extra data is an integer
        startActivity(intent);
    }

    public void openBuildYourOwnPizza(View view)
    {
        Intent intent = new Intent(this, BuildYourOwnActivity.class);
        startActivity(intent);
    }
}