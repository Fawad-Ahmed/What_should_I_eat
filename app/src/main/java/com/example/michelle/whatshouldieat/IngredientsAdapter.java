package com.example.michelle.whatshouldieat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * Sets ingredient objects in a ListView.
 */

public class IngredientsAdapter extends ArrayAdapter<Ingredient> {

    public IngredientsAdapter(Context context, ArrayList<Ingredient> values) {
        super(context, R.layout.search_result_row, values);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        // Inflate view
        View view = inflater.inflate(R.layout.ingredient_row, parent, false);

        // The ingredients
        Ingredient ingredient = getItem(position);

        // Fill in the title
        TextView textView = (TextView) view.findViewById(R.id.titleView);
        textView.setText(ingredient != null ? ingredient.toString() : null);

        // Changes the color of the text according to the selection value
        if (ingredient != null ? ingredient.selected : null) {
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        } else {
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
        }

        return view;
    }


}