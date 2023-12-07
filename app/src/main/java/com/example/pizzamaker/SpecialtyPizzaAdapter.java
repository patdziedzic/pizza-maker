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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

class SpecialtyPizzaAdapter extends RecyclerView.Adapter<SpecialtyPizzaAdapter.SpecialtyPizzaHolder>{

    private Context context; //need the context to inflate the layout
    private ArrayList<Pizza> pizzas; //need the data binding to each row of RecyclerView
    private ArrayAdapter<Topping> toppingsArrayAdapter;

    public SpecialtyPizzaAdapter(Context context, ArrayList<Pizza> pizzas) {
        this.context = context;
        this.pizzas = pizzas;
    }

    /**
     * This method will inflate the row layout for the items in the RecyclerView
     * @param parent
     * @param viewType
     * @return
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
        //toppingsArrayAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1, pizza.toppings);
        //holder.list_toppings.setAdapter(toppingsArrayAdapter);

        //^stopped coding here, still need to populate toppings using observable array list
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
     * Get the views from the row layout file, similar to the onCreate() method.
     */
    public static class SpecialtyPizzaHolder extends RecyclerView.ViewHolder {
        private TextView txt_pizzaName, txt_price;
        private ImageView im_pizzaImage;
        private Chip chip_sm, chip_md, chip_lg;
        private CheckBox check_sauce, check_cheese;
        private ListView list_toppings;
        private Button btn_add;
        private ConstraintLayout parentLayout; //this is the row layout

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
            btn_add = itemView.findViewById(R.id.btn_add);
            parentLayout = itemView.findViewById(R.id.rowLayout);
            setAddButtonOnClick(itemView); //register the onClicklistener for the button on each row.
        }

        /**
         * Set the onClickListener for the button on each row.
         * Clicking on the button will create an AlertDialog with the options of YES/NO.
         * @param itemView
         */
        private void setAddButtonOnClick(@NonNull View itemView) {
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                    alert.setTitle("Add to Order");
                    alert.setMessage(txt_pizzaName.getText().toString());
                    //handle the "YES" click
                    alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
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
            });
        }
    }
}
