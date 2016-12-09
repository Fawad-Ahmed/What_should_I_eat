package com.example.michelle.whatshouldieat;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

       
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

        add_item_editText = (EditText)findViewById(R.id.addTextBar);


        // When enter is pressed in the search bar, add item to appropriate list
        add_item_editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Add action to perform
                    Ingredient ingredient = new Ingredient(add_item_editText.getText().toString(), true);
                    if (active_tab.equals(TAB_INGREDIENTS)) {
                        ingredients.add(ingredient);
                        setListView(ingredients);
                    } else {
                        groceries.add(ingredient);
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
                Ingredient ingredient = adapter.getItem(pos);
                list.set(pos, ingredient.switch_selection());
                setListView(list);
            }
        });

        // Performed when long clicked: delete an item
        ingredients_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                list.remove(pos);
                setListView(list);
                return true;
            }
        });
    }

    // Creates the intent and starts result activity
    // Make search tag of selected items
    public void search(View view) {
        String[] ingredient_names = new String[ingredients.size()];
        for (int i = 0; i < ingredients.size(); i++) {
            ingredient_names[i] = ingredients.get(i).toString();
        }

        // Search on active ingredients
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("ingredients", ingredient_names);
        startActivity(intent);
    }
}
