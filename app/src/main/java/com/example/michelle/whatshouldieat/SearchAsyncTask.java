package com.example.michelle.whatshouldieat;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Michelle on 8-12-2016.
 * Gets the requested results from food2fork API
 */

public class SearchAsyncTask extends AsyncTask<String, Integer, String>{
    public final ArrayList<Recipe> search_results;
    private ResultsActivity activity;
    private ListView listView;

    // Constructor
    SearchAsyncTask(ResultsActivity activity, ListView listView){
        this.activity = activity;
        this.search_results = new ArrayList<>();
        this.listView = listView;
    }

    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer_search(params[0].replaceAll("\\s+","+"));
    }

    protected void onPostExecute(String result) {
        if (result.equals("no internet")) {
            Toast.makeText(this.activity.getApplicationContext(), "No internet connection",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onPostExecute(result);

            try {
                JSONObject respObj = new JSONObject(result);
                JSONArray recipe_results = respObj.getJSONArray("recipes");

                // Get search results and put in ArrayList
                for (int i = 0; i < recipe_results.length(); i++) {
                    JSONObject recipe = recipe_results.getJSONObject(i);

                    String title = recipe.getString("title");
                    String id = recipe.getString("recipe_id");
                    String image_url = recipe.getString("image_url");
                    String social_rank = recipe.getString("social_rank");

                    search_results.add(new Recipe(title, id, image_url, social_rank));
                }

                // Set results to adapter
                ArrayAdapter moviesAdapter = new ResultAdapter(activity, search_results);
                listView.setAdapter(moviesAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this.activity.getApplicationContext(), "No results were found",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
