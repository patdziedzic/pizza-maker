package com.example.pizzamaker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

class SpecialtyPizzaAdapter extends RecyclerView.Adapter<SpecialtyPizzaAdapter.SpecialtyPizzaHolder>{

    private Context context; //need the context to inflate the layout
    private static GlobalData globalData = GlobalData.getInstance();
    private ArrayList<Pizza> pizzas; //need the data binding to each row of RecyclerView
    private ArrayAdapter<String> toppingsArrayAdapter;

    /**
     * Initializes the SpecialtyPizzaAdapter with a Context and an ArrayList of pizzas
     * @param context the Context to be initialized
     * @param pizzas the list of pizzas to be initialized
     */
    public SpecialtyPizzaAdapter(Context context, ArrayList<Pizza> pizzas) {
        this.context = context;
        this.pizzas = pizzas;
    }

    /**
     * This method will inflate the row layout for the items in the RecyclerView
     * @param parent the ViewGroup to inflate via the View
     * @param viewType the integer representing the viewType
     * @return a SpecialtyPizzaHolder depicting the inflated row layout
     */
    @NonNull
    @Override
    public SpecialtyPizzaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the row layout for the items
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_specialty_pizza, parent, false);

        return new SpecialtyPizzaHolder(view);
    }

    /**
     * Assign data values for each row according to their "position" (index) when the item becomes
     * visible on the screen.
     * @param holder the instance of ItemsHolder
     * @param position the index of the item in the list of items
     */
    @Override
    public void onBindViewHolder(@NonNull SpecialtyPizzaHolder holder, int position) {
        Pizza pizza = pizzas.get(position);

        //assign values for each row
        holder.txt_pizzaName.setText(pizza.getClass().getSimpleName());
        holder.txt_price.setText(String.format("$%.2f", pizza.price()));
        holder.im_pizzaImage.setImageResource(pizza.image);

        String[] toppings = new String[pizza.toppings.size()];
        for (int i = 0; i < pizza.toppings.size(); i++) {
            toppings[i] = pizza.toppings.get(i).getName();
        }
        toppingsArrayAdapter = new ArrayAdapter<>(this.context, android.R.layout.simple_list_item_1, toppings);
        holder.list_toppings.setAdapter(toppingsArrayAdapter);
    }

    /**
     * Get the number of pizzas in the ArrayList.
     * @return the number of pizzas in the list.
     */
    @Override
    public int getItemCount() {
        return pizzas.size(); //number of Pizza in the array list.
    }




    /**
     * Holder class for Specialty Pizza
     */
    public static class SpecialtyPizzaHolder extends RecyclerView.ViewHolder {
        private TextView txt_pizzaName, txt_price;
        private ImageView im_pizzaImage;
        private Chip chip_sm, chip_md, chip_lg;
        private CheckBox check_sauce, check_cheese;
        private ListView list_toppings;
        private EditText input_Amount;
        private Button btn_add;

        /**
         * Constructor which initializes and links all the necessary elements from the GUI
         * @param itemView the View
         */
        public SpecialtyPizzaHolder(@NonNull View itemView) {
            super(itemView);
            txt_pizzaName = itemView.findViewById(R.id.txt_pizzaName);
            txt_price = itemView.findViewById(R.id.txt_price);
            im_pizzaImage = itemView.findViewById(R.id.im_pizzaImage);
            chip_sm = itemView.findViewById(R.id.chip_sm);
            chip_md = itemView.findViewById(R.id.chip_md);
            chip_lg = itemView.findViewById(R.id.chip_lg);
            check_sauce = itemView.findViewById(R.id.check_sauce);
            check_cheese = itemView.findViewById(R.id.check_cheese);
            list_toppings = itemView.findViewById(R.id.list_toppings);
            input_Amount = itemView.findViewById(R.id.input_Amount);
            btn_add = itemView.findViewById(R.id.btn_add);
            setOnPizzaChange(itemView);
            setAddButtonOnClick(itemView);
        }

        /**
         * Anytime a component of a given Specialty Pizza is changed, create a new pizza
         * depicting the new pizza from the user's specifications
         * @param itemView the View interacted with
         */
        private void setOnPizzaChange(@NonNull View itemView) {
            chip_sm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createPizza();
                }
            });
            chip_md.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createPizza();
                }
            });
            chip_lg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createPizza();
                }
            });
            check_sauce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createPizza();
                }
            });
            check_cheese.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createPizza();
                }
            });
        }

        /**
         * Set the onClickListener for the button on each row.
         * Clicking on the button will create an AlertDialog with the options of YES/NO.
         * @param itemView the View interacted with
         */
        private void setAddButtonOnClick(@NonNull View itemView) {
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isValidAmount()) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                        alert.setTitle("Add to Order");
                        alert.setMessage(txt_pizzaName.getText().toString());
                        //handle the "YES" click
                        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                createPizza();
                                Pizza currPizza = globalData.getCurrPizza();
                                Order currOrder = globalData.getCurrOrder();
                                for (int i = 0; i < Integer.parseInt(input_Amount.getText().toString()); i++)
                                    currOrder.addPizza(currPizza);
                                globalData.setCurrOrder(currOrder);
                                Toast.makeText(itemView.getContext(),
                                        txt_pizzaName.getText().toString() + " added.", Toast.LENGTH_LONG).show();
                            }
                            //handle the "NO" click
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(itemView.getContext(),
                                        txt_pizzaName.getText().toString() + " not added.", Toast.LENGTH_LONG).show();
                            }
                        });
                        AlertDialog dialog = alert.create();
                        dialog.show();
                    }
                }
            });
        }

        /**
         * Creates a Pizza object, stores it in GlobalData currPizza, and updates the price
         */
        private void createPizza() {
            Pizza currPizza = PizzaMaker.createPizza(txt_pizzaName.getText().toString());
            if (currPizza == null) return;

            if (chip_sm.isChecked()) currPizza.size = Size.SMALL;
            else if (chip_md.isChecked()) currPizza.size = Size.MEDIUM;
            else if (chip_lg.isChecked()) currPizza.size = Size.LARGE;
            if (check_sauce.isChecked()) currPizza.extraSauce = true;
            if (check_cheese.isChecked()) currPizza.extraCheese = true;

            double price = currPizza.price();
            String strInput = input_Amount.getText().toString();
            if (!strInput.isEmpty()) {
                int numInput = Integer.parseInt(strInput);
                if (numInput > 0)
                    price = price * numInput;
            }
            txt_price.setText(String.format("$%.2f", price));

            globalData.setCurrPizza(currPizza);
        }

        /**
         * Determines whether or not the amount of pizzas selected is a valid amount
         * @return true or false depicting whether or not it is a valid amount
         */
        private boolean isValidAmount() {
            String strInput = input_Amount.getText().toString();
            if (!strInput.isEmpty()) {
                int numInput = Integer.parseInt(strInput);
                if (numInput > 0) return true;
            }
            Toast.makeText(itemView.getContext(),
                    "Not enough pizzas selected.", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
