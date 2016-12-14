package com.example.michelle.whatshouldieat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.util.ArrayList;

public class TabbedActivity extends AppCompatActivity {

    // Contains the id of the users Google account
    static String acc_id;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    // The arraylists that hold the ingredients
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    ArrayList<Ingredient> groceries = new ArrayList<>();


    // The editText to add an item to the lists
    static EditText add_item_editText;

    // Firebase URL
    private static final String FIREBASE_URL = "https://fir-whatshouldieat.firebaseio.com/";

    // Ingredients and groceries references
    static Firebase ingredientRef = null;
    static Firebase groceriesRef = null;

    // Firebase Reference
    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        setTitle("What Should I eat?");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        add_item_editText = (EditText) findViewById(R.id.addTextBar);

        // The EditText by wich you can add ingredients to the lists
        add_item_editText = (EditText)findViewById(R.id.addTextBar);




        // Get the account id from the Google account
        Intent intent = getIntent();
        acc_id = intent.getStringExtra("acc_id");

        // Set the Firebase Database
        Firebase.setAndroidContext(this);
        Firebase mRootRef = new Firebase(FIREBASE_URL);
        Firebase firebaseRef = mRootRef.child(acc_id);

        // Get the ingredients list from the database
        ingredientRef = firebaseRef.child("ingredients");
        groceriesRef = firebaseRef.child("groceries");


        // When enter is pressed in the search bar, add item to appropriate list
        add_item_editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Add action to perform
                    String key = "" + System.nanoTime();
                    Ingredient ingredient = new Ingredient(add_item_editText.getText().toString(), true, key);
                    if (tabLayout.getSelectedTabPosition() == 0) {
                        ingredientRef.child(key).setValue(ingredient);
                    } else {
                        groceriesRef.child(key).setValue(ingredient);
                    }
                    add_item_editText.setText("");
                    return true;
                }
                return false;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // The ingredients tab
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                return ingredientsFragment.onCreateView(inflater, container, null);

            // The groceries tab
            } else {
                GroceriesFragment groceriesFragment = new GroceriesFragment();
                return groceriesFragment.onCreateView(inflater, container, null);
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Ingredients";
                case 1:
                    return "Groceries";
            }
            return null;
        }
    }
}
