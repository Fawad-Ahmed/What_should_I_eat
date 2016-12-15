package com.example.michelle.whatshouldieat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Created by Michelle Appel on 14-12-2016.
 * This Fragment holds a ListView that contain all ingredients the
 * user has added to the database.
 */

public class IngredientsFragment extends ListFragment {
    // Firebase URL
    private static final String FIREBASE_URL = "https://fir-whatshouldieat.firebaseio.com/";

    // Firebase Ingredients references
    Firebase ingredientRef = null;

    // The listview in which the ingredients appear
    ListView ingredients_listView;

    // The list that holds the ingredients
    public static ArrayList<Ingredient> ingredients = new ArrayList<>();

    /* This method is called from the MainActivity. It inflates the container
    * of that activity.
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        // The ListView that is going to contain the ingredients
        ingredients_listView = (ListView) view.findViewById(R.id.ingredients_listView);

        // The search button
        FloatingActionButton searchButton = (FloatingActionButton)view.findViewById(R.id.searchButton);

        // Set the Firebase Database
        Firebase.setAndroidContext(view.getContext());
        Firebase mRootRef = new Firebase(FIREBASE_URL);
        Firebase firebaseRef = mRootRef.child(MainActivity.acc_id);

        // Get the ingredients list from the database
        ingredientRef = firebaseRef.child("ingredients");

        // Listen if data in database is changed
        ingredientRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // The arraylist that contains the ingredients
                ingredients = new ArrayList<>();

                // Add ingredients from Firebase to the ingredients ArrayList
                try {
                    // Get data from Snapshot and put in Hashmap
                    HashMap<String, HashMap<String, String>> ingredients_list =
                            (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();

                    // Get items out of Hashmap, convert to ingredients and put in ArrayList.
                    for (String key : ingredients_list.keySet()) {
                        // Get ingredient out of Hashmap
                        HashMap<String, ?> ingredient_hash = ingredients_list.get(key);
                        String title = (String) ingredient_hash.get("title");
                        Boolean selected = (Boolean) ingredient_hash.get("selected");

                        // Create Ingredient object
                        Ingredient ingredient = new Ingredient(title, selected, key);

                        // Put in ingredients ArrayList
                        ingredients.add(ingredient);
                    }
                } catch (Exception e) {
                    // Do nothing if database is empty
                }

                // Fill up the listView with ingredients from the ingredients ArrayList.
                setListView(ingredients_listView, ingredients);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // Required method
            }
        });

        // Finally, return the view to the Tabbed Activity
        return view;
    }

    /* This method fills in a given ListView with a given ArrayList.
     * Then onItemClickListener and OnItemLongClickListener are
     * instantiated.
     * When clicked on an ingredient, it appears grey and it will
     * not be included in the search query. When clicked again,
     * it will be included again.
     * When long clicked on an ingredient, it will be deleted.
     */
    public void setListView(final ListView listView, final ArrayList<Ingredient> list) {
        final IngredientsAdapter adapter = new IngredientsAdapter(listView.getContext(), list);
        listView.setAdapter(adapter);

        // Performed when clicked: set ingredients active or inactive
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                // Perform action when clicked on
                Ingredient ingredient = adapter.getItem(pos);
                ingredientRef.child(ingredient.key).setValue(ingredient.switch_selection());
                setListView(listView, list);
            }
        });

        // Performed when long clicked: delete an item
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                String key = list.get(pos).key;
                ingredientRef.child(key).removeValue();
                return true;
            }
        });
    }
}
