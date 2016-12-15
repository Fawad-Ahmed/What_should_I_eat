package com.example.michelle.whatshouldieat;

import android.content.SharedPreferences;
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

    String[] allergies;
    String[] diets;

    // Constructor
    SearchAsyncTask(ResultsActivity activity, ListView listView){
        this.activity = activity;
        this.search_results = new ArrayList<>();
        this.listView = listView;
    }

    public void onPreExecute() {
        super.onPreExecute();

        // Allergies
        ArrayList<String> allergies_arraylist = new ArrayList<>();

        // Allergy names and search values
        String[] allergy_names = AllergiesPrefsActivity.allergy_names;
        String[] allergy_searchvalues = AllergiesPrefsActivity.allergy_searchvalues;

        // The allergy prefs
        SharedPreferences allergy_prefs = activity.getSharedPreferences("Allergies", activity.MODE_PRIVATE);
        // If preferences not initialized yet, set all allergies false
        if (allergy_prefs == null) {
            for (int i = 0; i < allergy_names.length; i++) {
                Allergy allergy = new Allergy(allergy_names[i], false);
                SharedPreferences.Editor editor = allergy_prefs.edit();
                editor.putBoolean(allergy_names[i], false);
                editor.commit();
                allergies_arraylist.add(allergy.toString());
            }
        } else {
            // Get all selected allergies
            for (int i = 0; i < allergy_names.length; i++) {
                Allergy allergy = new Allergy(allergy_names[i], allergy_prefs.getBoolean(allergy_names[i], false));
                // If selected: add search value to list
                if (allergy.selected) {
                    allergies_arraylist.add(allergy_searchvalues[i]);
                }
            }
        }
        // Convert to array
        allergies = new String[allergies_arraylist.size()];
        for (int i = 0; i < allergies_arraylist.size(); i++) {
            allergies[i] = allergies_arraylist.get(i);
        }

        // Diets
        ArrayList<String> diets_arraylist = new ArrayList<>();

        // Diet names and search values
        String[] diet_names = DietPrefsActivity.diet_names;
        String[] diet_searchvalues = DietPrefsActivity.diet_searchvalues;

        // The diet prefs
        SharedPreferences diet_prefs = activity.getSharedPreferences("Diets", activity.MODE_PRIVATE);
        // If preferences not initialized yet, set all diets false
        if (diet_prefs == null) {
            for (int i = 0; i < diet_names.length; i++) {
                Allergy diet = new Allergy(diet_names[i], false);
                SharedPreferences.Editor editor = diet_prefs.edit();
                editor.putBoolean(diet_names[i], false);
                editor.commit();
                diets_arraylist.add(diet.toString());
            }
        } else {
            // Get all selected diets
            for (int i = 0; i < diet_names.length; i++) {
                Allergy diet = new Allergy(diet_names[i], diet_prefs.getBoolean(diet_names[i], false));
                // If selected: add search value to list
                if (diet.selected) {
                    diets_arraylist.add(diet_searchvalues[i]);
                }
            }
        }
        // Convert to array
        diets = new String[diets_arraylist.size()];
        for (int i = 0; i < diets_arraylist.size(); i++) {
            diets[i] = diets_arraylist.get(i);
        }
    }

    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer_search(allergies, diets, params);
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

                if (recipe_results.length() == 0) {
                    Toast.makeText(activity, "No results were found", Toast.LENGTH_SHORT).show();
                } else {

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

                        ArrayList<String> courses = new ArrayList<>();
                        if (recipe.has("attributes")) {
                            JSONObject attributes = recipe.getJSONObject("attributes");

                            if (attributes.has("course")) {
                                JSONArray json_courses = attributes.getJSONArray("course");

                                for (int j = 0; j < json_courses.length(); j++) {
                                    courses.add(json_courses.getString(j));
                                }
                            }

                            if (attributes.has("flavors")) {
                                JSONObject json_flavors = recipe.getJSONObject("flavors");
                                if (!json_flavors.equals("null")) {

                                    String piquant = json_flavors.getString("piquant");
                                    String meaty = json_flavors.getString("meaty");
                                    String bitter = json_flavors.getString("bitter");
                                    String sweet = json_flavors.getString("sweet");
                                    String sour = json_flavors.getString("sour");
                                    String salty = json_flavors.getString("salty");
                                                                    }
                            }

                            if (attributes.has("rating")) {
                                String rating = recipe.getString("rating");
                            }
                        }

                        search_results.add(new Recipe(recipeName, id, image_url, ingredients, courses));
                    }

                    // Set results to adapter
                    ArrayAdapter moviesAdapter = new ResultAdapter(activity, search_results);
                    listView.setAdapter(moviesAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this.activity.getApplicationContext(), "No results were found",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
