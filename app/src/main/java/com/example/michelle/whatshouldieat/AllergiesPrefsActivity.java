package com.example.michelle.whatshouldieat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;

public class AllergiesPrefsActivity extends AppCompatActivity {

    // The allergy prefs
    SharedPreferences allergy_prefs;
    SharedPreferences.Editor editor;

    // The ListView
    ListView listView;

    // The Array that holds the allergy names
    public static String[] allergy_names =
            {"Dairy",
                    "Egg",
                    "Gluten",
                    "Peanut",
                    "Seafood",
                    "Sesame",
                    "Soy",
                    "Sulfite",
                    "Tree Nut",
                    "Wheat"};

    public static String[] allergy_searchvalues = {
            "396^Dairy-Free",
            "397^Egg-Free",
            "393^Gluten-Free",
            "394^Peanut-Free",
            "398^Seafood-Free",
            "399^Sesame-Free",
            "400^Soy-Free",
            "401^Sulfite-Free",
            "395^Tree Nut-Free",
            "392^Wheat-Free"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergies_prefs);

        setTitle("Allergies");




        listView = (ListView)findViewById(R.id.allergies_listView);

        // The allergy prefs
        allergy_prefs = this.getSharedPreferences("Allergies", this.MODE_PRIVATE);
        editor = allergy_prefs.edit();

        ArrayList<Allergy> allergies = new ArrayList<>();
        for (int i = 0; i < allergy_names.length; i++) {
            Allergy allergy = new Allergy(allergy_names[i], allergy_prefs.getBoolean(allergy_names[i], false));
            allergies.add(allergy);
        }

        final PreferenceAdapter adapter = new PreferenceAdapter(this, allergies);
        listView.setAdapter(adapter);
    }

    public void pref_switch(View view) {
        Switch switchView = (Switch)view.findViewById(R.id.switchView);
        Allergy allergy = new Allergy(switchView.getText().toString(), switchView.isChecked());
        editor.putBoolean(allergy.title, allergy.selected);
        editor.commit();
    }
}
