package com.example.michelle.whatshouldieat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class ResultsActivity extends AppCompatActivity {

    private EditText search_bar;
    private ListView search_result_listView;

    String acc_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        search_bar = (EditText) findViewById(R.id.search_bar);
        search_result_listView = (ListView)findViewById(R.id.listView);

        // Get search term from MainActivity and put in ListView
        Intent intent = getIntent();
        acc_id = intent.getStringExtra("acc_id");

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
                intent.putExtra("acc_id", acc_id);

                startActivity(intent);
            }
        });
    }
}
