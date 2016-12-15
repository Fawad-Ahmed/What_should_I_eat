package com.example.michelle.whatshouldieat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Preferences");

        String[] preference_list = {"Allergies", "Diets"};

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, preference_list);

        ListView listView = (ListView)findViewById(R.id.prefs_listView);
        listView.setAdapter(adapter);

        // Performed when clicked: go to preferences
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                // Perform action when clicked on
                Intent intent;
                if (pos == 0) {
                    intent = new Intent(getApplicationContext(), AllergiesPrefsActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), DietPrefsActivity.class);
                }
                startActivity(intent);
            }
        });
    }
}
