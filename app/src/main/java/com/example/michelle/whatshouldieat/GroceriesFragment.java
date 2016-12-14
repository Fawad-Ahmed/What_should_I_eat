package com.example.michelle.whatshouldieat;

import android.os.Bundle;
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
 * user has added to the grocerylist.
 */

public class GroceriesFragment extends ListFragment {
    // Firebase URL
    private static final String FIREBASE_URL = "https://fir-whatshouldieat.firebaseio.com/";

    // Firebase Ingredients references
    Firebase groceriesRef = null;

    // The listview in which the ingredients appear
    ListView groceries_listView;

    // The list that holds the ingredients
    private ArrayList<Ingredient> groceries = new ArrayList<>();

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

        // Performed when long clicked: delete an item
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                String key = list.get(pos).key;
                groceriesRef.child(key).removeValue();
                return true;
            }
        });
    }

    /* This method is called from the TabbedActivity. It inflates the container
     * of that activity.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groceries, container, false);

        // The ListView that is going to contain the ingredients
        groceries_listView = (ListView) view.findViewById(R.id.groceries_listView);

        // Set the Firebase Database
        Firebase.setAndroidContext(view.getContext());
        Firebase mRootRef = new Firebase(FIREBASE_URL);
        Firebase firebaseRef = mRootRef.child(TabbedActivity.acc_id);

        // Get the ingredients list from the database
        groceriesRef = firebaseRef.child("groceries");

        // Listen if data in database is changed
        groceriesRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // The arraylist that contains the ingredients
                groceries = new ArrayList<>();

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
                        groceries.add(ingredient);
                    }
                } catch (Exception e) {
                    // Do nothing if database is empty
                }

                // Fill up the listView with ingredients from the ingredients ArrayList.
                setListView(groceries_listView, groceries);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // Required method
            }
        });

        // Finally, return the view to the Tabbed Activity
        return view;
    }
}
