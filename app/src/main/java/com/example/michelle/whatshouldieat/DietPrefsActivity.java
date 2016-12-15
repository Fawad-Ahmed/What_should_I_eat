package com.example.michelle.whatshouldieat;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;

public class DietPrefsActivity extends AppCompatActivity {

    // The allergy prefs
    SharedPreferences diet_prefs;
    SharedPreferences.Editor editor;

    // The ListView
    ListView listView;

    // The array that holds the diet names
    public static String[] diet_names =
            {"Lacto vegetarian",
                    "Ovo vegetarian",
                    "Pescetarian",
                    "Vegan",
                    "Vegetarian"};

    public static String[] diet_searchvalues =
            {"388^Lacto vegetarian",
                    "389^Ovo vegetarian",
                    "390^Pescetarian",
                    "386^Vegan",
                    "387^Lacto-ovo vegetarian"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergies_prefs);

        setTitle("Allergies");

        listView = (ListView)findViewById(R.id.allergies_listView);

        // The allergy prefs
        diet_prefs = this.getSharedPreferences("Diets", this.MODE_PRIVATE);
        editor = diet_prefs.edit();

        ArrayList<Allergy> diets = new ArrayList<>();
        for (int i = 0; i < diet_names.length; i++) {
            Allergy allergy = new Allergy(diet_names[i], diet_prefs.getBoolean(diet_names[i], false));
            diets.add(allergy);
        }

        final PreferenceAdapter adapter = new PreferenceAdapter(this, diets);
        listView.setAdapter(adapter);
    }

    public void pref_switch(View view) {
        Switch switchView = (Switch)view.findViewById(R.id.switchView);
        Allergy allergy = new Allergy(switchView.getText().toString(), switchView.isChecked());
        editor.putBoolean(allergy.title, allergy.selected);
        editor.commit();
    }
}
