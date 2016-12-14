package com.example.michelle.whatshouldieat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    // Firebase URL
    private static final String FIREBASE_URL = "https://fir-whatshouldieat.firebaseio.com/";

    // Ingredients and groceries references
    Firebase ingredientRef = null;
    Firebase groceriesRef = null;

    // Firebase Reference
    private Firebase firebaseRef;

    // Google account id
    String acc_id;

    // The arraylists that hold the ingredients
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    ArrayList<Ingredient> groceries = new ArrayList<>();

    ListView ingredients_listView;

    // The Strings values of active tabs
    final static String TAB_INGREDIENTS = "ingredients";
    final static String TAB_GROCERIES = "groceries";

    // Initial active tab is the ingredients tab
    String active_tab = TAB_INGREDIENTS;

    // The tab buttons
    Button ingredients_button;
    Button groceries_button;

    // The editText to add an item to the lists
    EditText add_item_editText;

    // The search button
    FloatingActionButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The search button
        searchButton = (FloatingActionButton)findViewById(R.id.searchButton);

        // The tab buttons
        ingredients_button = (Button)findViewById(R.id.ingredients);
        groceries_button = (Button)findViewById(R.id.groceries);

        // Give active tab white color
        if (active_tab.equals(TAB_INGREDIENTS)) {
            ingredients_button.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            groceries_button.setTextColor(ContextCompat.getColor(this, R.color.white));
        }

        // The listview that contains the ingredients
        ingredients_listView = (ListView)findViewById(R.id.ingredients_listView);

        Intent intent = getIntent();
        acc_id = intent.getStringExtra("acc_id");

        // Set the Firebase Database
        Firebase.setAndroidContext(this);
        Firebase mRootRef = new Firebase(FIREBASE_URL);
        Firebase firebaseRef = mRootRef.child(acc_id);

        // Get the ingredients list from the database
        ingredientRef = firebaseRef.child("ingredients");
        groceriesRef = firebaseRef.child("groceries");

        // Listen if data in database is changed
        ingredientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // The arraylists that contain the ingredients and the groceries
                ingredients = new ArrayList<>();

                // Add ingredients from Firebase to the ingredients ArrayList
                try {
                    HashMap<String, HashMap<String, String>> ingredients_list =
                            (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();

                    for (String key : ingredients_list.keySet()) {
                        HashMap<String, ?> ingredient_hash = ingredients_list.get(key);
                        String title = (String) ingredient_hash.get("title");
                        Boolean selected = (Boolean) ingredient_hash.get("selected");

                        Ingredient ingredient = new Ingredient(title, selected, key);
                        ingredients.add(ingredient);
                    }

                } catch (Exception e) {
                    // Do nothing
                }

                // Check if ingredients tab is selected
                if (active_tab.equals(TAB_INGREDIENTS)) {
                    setListView(ingredients);
                } else {
                    setListView(groceries);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // Listen if data in database is changed
        groceriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // The arraylists that contain the ingredients and the groceries
                groceries = new ArrayList<>();

                // Add ingredients from Firebase to the groceries ArrayList
                try {
                    HashMap<String, HashMap<String, String>> groceries_list = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();

                    for (String key : groceries_list.keySet()) {
                        HashMap<String, ?> ingredient_hash = groceries_list.get(key);
                        String title = (String) ingredient_hash.get("title");
                        Boolean selected = (Boolean) ingredient_hash.get("selected");

                        Ingredient ingredient = new Ingredient(title, selected, key);
                        groceries.add(ingredient);
                    }

                } catch (Exception e) {
                    // Do nothing
                }
                // Check if ingredients tab is selected
                if (active_tab.equals(TAB_INGREDIENTS)) {
                    setListView(ingredients);
                } else {
                    setListView(groceries);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        // The EditText by wich you can add ingredients to the lists
        add_item_editText = (EditText)findViewById(R.id.addTextBar);

        // When enter is pressed in the search bar, add item to appropriate list
        add_item_editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Add action to perform
                    String key = "" + System.nanoTime();
                    Ingredient ingredient = new Ingredient(add_item_editText.getText().toString(), true, key);
                    if (active_tab.equals(TAB_INGREDIENTS)) {
                        ingredientRef.child(key).setValue(ingredient);
                        setListView(ingredients);
                    } else {
                        groceriesRef.child(key).setValue(ingredient);
                        setListView(groceries);
                    }

                    add_item_editText.setText("");
                    return true;
                }
                return false;
            }
        });
    }


    // When ingredients tab is clicked on
    public void ingredients_tab(View view) {
        active_tab = TAB_INGREDIENTS;
        ingredients_button.setTextColor(ContextCompat.getColor(this, R.color.white));
        groceries_button.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        add_item_editText.setHint("Add your ingredients");

        setListView(ingredients);

        searchButton.setVisibility(View.VISIBLE);
    }

    // When groceries tab is clicked on
    public void groceries_tab(View view) {
        active_tab = TAB_GROCERIES;
        groceries_button.setTextColor(ContextCompat.getColor(this, R.color.white));
        ingredients_button.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        add_item_editText.setHint("Add your groceries");

        setListView(groceries);

        searchButton.setVisibility(View.GONE);
    }


    public void setListView(final ArrayList<Ingredient> list) {
        final IngredientsAdapter adapter = new IngredientsAdapter(this, list);
        ingredients_listView.setAdapter(adapter);

        // Performed when clicked: set ingredients active or inactive
        ingredients_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                // Perform action when clicked on
                if (active_tab.equals(TAB_INGREDIENTS)) {
                    Ingredient ingredient = adapter.getItem(pos);
                    ingredientRef.child(ingredient.key).setValue(ingredient.switch_selection());
                    setListView(list);
                }
            }
        });

        // Performed when long clicked: delete an item
        ingredients_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                String key = list.get(pos).key;
                if (active_tab.equals(TAB_INGREDIENTS)) {
                    ingredientRef.child(key).removeValue();
                } else {
                    groceriesRef.child(key).removeValue();
                }
                return true;
            }
        });
    }

    // Creates the intent and starts result activity
    // Make search tag of selected items
    public void search(View view) {
        // Make ArrayList of selected ingredients
        ArrayList<Ingredient> selected_ingredients = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).selected) {
                selected_ingredients.add(ingredients.get(i));
            }
        }

        // Convert to array
        String[] ingredient_names = new String[selected_ingredients.size()];
        for (int i = 0; i < selected_ingredients.size(); i++) {
            ingredient_names[i] = selected_ingredients.get(i).toString();
        }

        // Search on active ingredients
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("ingredients", ingredient_names);
        intent.putExtra("acc_id", acc_id);
        startActivity(intent);
    }


}
