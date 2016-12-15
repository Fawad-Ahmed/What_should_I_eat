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

/**
 * Created by Michelle on 8-12-2016.
 * Sets ingredients objects in a ListView.
 */

public class IngredientsAdapter extends ArrayAdapter<Ingredient> {

    public IngredientsAdapter(Context context, ArrayList<Ingredient> values) {
        super(context, R.layout.search_result_row, values);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.ingredient_row, parent, false);


        Ingredient ingredient = getItem(position);
        TextView textView = (TextView) view.findViewById(R.id.titleView);
        textView.setText(ingredient != null ? ingredient.toString() : null);

        if (ingredient != null ? ingredient.selected : null) {
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        } else {
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
        }

        return view;
    }


}