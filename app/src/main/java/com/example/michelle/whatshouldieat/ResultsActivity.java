package com.example.michelle.whatshouldieat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/*
 * Shows the search results to the user.
 */

public class ResultsActivity extends AppCompatActivity {

    // The listview
    private ListView search_result_listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        search_result_listView = (ListView)findViewById(R.id.listView);

        // Get search term from MainActivity and put in ListView
        Intent intent = getIntent();

        String[] search_term = intent.getStringArrayExtra("ingredients");

        show_results(search_term);
    }

    // Get results from SearchAsyncTask and puts in ListView. Sets item click listener.
    public void show_results(String[] search_term) {
        // Calls results from AsyncTask
        final SearchAsyncTask searchAsyncTask = new SearchAsyncTask(ResultsActivity.this, search_result_listView);
        searchAsyncTask.execute(search_term);

        // Performs action when clicked on item
        final Intent intent = new Intent(this, RecipeActivity.class);
        search_result_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("id", searchAsyncTask.search_results.get(position).id);

                startActivity(intent);
            }
        });


    }

}
