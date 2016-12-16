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
 * Set preferences in a ListView. Preferences are allergy objects.
 * The listview sets the title in the TextView and adapts the switch
 * of a preference to the state of it.
 */

public class PreferenceAdapter extends ArrayAdapter<Allergy> {

    public PreferenceAdapter(Context context, ArrayList<Allergy> values) {
        super(context, R.layout.preference_row, values);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        // Inflate the view
        View view = inflater.inflate(R.layout.preference_row, parent, false);

        // The allergy object
        Allergy preference = getItem(position);
        Switch switchView = (Switch) view.findViewById(R.id.switchView);

        // Set the TextView
        switchView.setText(preference.toString());

        // Set the switch
        switchView.setChecked(preference.selected);

        return view;
    }

}