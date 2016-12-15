package com.example.michelle.whatshouldieat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;

import java.util.ArrayList;
/**
 * Created by Michelle on 14-12-2016.
 * Set preferences in a listview
 */

public class PreferenceAdapter extends ArrayAdapter<Allergy> {

    public PreferenceAdapter(Context context, ArrayList<Allergy> values) {
        super(context, R.layout.preference_row, values);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.preference_row, parent, false);

        Allergy preference = getItem(position);
        Switch switchView = (Switch) view.findViewById(R.id.switchView);

        switchView.setText(preference.toString());

        switchView.setChecked(preference.selected);

        return view;
    }

}