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
        return HttpRequestHelper.downloadFromServer_search(params);
    }

    protected void onPostExecute(String result) {
        if (result.equals("no internet")) {
            Toast.makeText(this.activity.getApplicationContext(), "No internet connection",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onPostExecute(result);

            try {
                JSONObject respObj = new JSONObject(result);
                JSONArray recipe_results = respObj.getJSONArray("matches");

                System.out.println("array " + recipe_results);

                // print
                System.out.println("length " + recipe_results.length());

                // Get search results and put in ArrayList
                for (int i = 0; i < recipe_results.length(); i++) {
                    JSONObject recipe = recipe_results.getJSONObject(i);

                    String recipeName = recipe.getString("recipeName");
                    String id = recipe.getString("id");

                    JSONObject image_urls_by_size = recipe.getJSONObject("imageUrlsBySize");
                    String image_url = image_urls_by_size.getString("90");

                    JSONArray json_ingredients = recipe.getJSONArray("ingredients");
                    ArrayList<String> ingredients = new ArrayList<>();
                    for (int j = 0; j < json_ingredients.length(); j++) {
                        ingredients.add(json_ingredients.getString(j));
                    }

                    String sourceDisplayName = recipe.getString("sourceDisplayName");
                    String totalTimeInSeconds = recipe.getString("totalTimeInSeconds");

                    if (recipe.has("attributes")) {
                        JSONObject attributes = recipe.getJSONObject("attributes");

                        if (attributes.has("course")) {
                            JSONArray json_courses = attributes.getJSONArray("course");
                            System.out.println("COURSE" + json_courses);

                            ArrayList<String> courses = new ArrayList<>();
                            for (int j = 0; j < json_courses.length(); j++) {
                                courses.add(json_courses.getString(j));
                            }
                        }

                        if (attributes.has("flavors")) {
                            JSONObject flavors = recipe.getJSONObject("flavors");
                            if (!flavors.equals("null")) {

                                String piquant = flavors.getString("piquant");
                                String meaty = flavors.getString("meaty");
                                String bitter = flavors.getString("bitter");
                                String sweet = flavors.getString("sweet");
                                String sour = flavors.getString("sour");
                                String salty = flavors.getString("salty");
                            }
                        }
                    }

                    search_results.add(new Recipe(recipeName, id, image_url, ingredients));
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
