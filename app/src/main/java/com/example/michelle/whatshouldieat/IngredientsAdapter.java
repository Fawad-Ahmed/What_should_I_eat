package com.example.michelle.whatshouldieat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Michelle on 8-12-2016.
 */

public class IngredientsAdapter extends ArrayAdapter<String> {

    public IngredientsAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.search_result_row, values);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.ingredient_row, parent, false);
        String ingredient = getItem(position);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(ingredient);

        return view;
    }
}