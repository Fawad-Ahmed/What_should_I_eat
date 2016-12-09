package com.example.michelle.whatshouldieat;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Michelle on 8-12-2016.
 * Gets the recipe from the HttpHelper and puts in a ListView.
 */

class RecipeAsyncTask extends AsyncTask<String, Integer, String> {
    private Context context;
    private RecipeActivity activity;

    // Constructor
    public RecipeAsyncTask(RecipeActivity activity){
        super();
        this.activity = activity;
        this.context = this.activity.getApplicationContext();
    }

    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer_recipe(params);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("no internet")) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        } else {
            super.onPostExecute(result);

            try{
                JSONObject json_recpie_info = new JSONObject(result);

                String title = json_recpie_info.getString("name");
                activity.setTitle(title);

                // Get ingredients
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                JSONArray jsonArray_ingredients = json_recpie_info.getJSONArray("ingredientLines");

                for (int i = 0; i < jsonArray_ingredients.length(); i++) {
                    System.out.println(jsonArray_ingredients.get(i).toString());
                    Ingredient ingredient = new Ingredient(jsonArray_ingredients.get(i).toString(), true);
                    ingredients.add(ingredient);
                }

                // Set results to adapter
                IngredientsAdapter adapter = new IngredientsAdapter(activity, ingredients);
                ListView listView = (ListView) activity.findViewById(R.id.ingredients_listView);
                listView.setAdapter(adapter);

                JSONArray images = json_recpie_info.getJSONArray("images");
                JSONObject image_urls = images.getJSONObject(0);
                String image_url = image_urls.getString("hostedLargeUrl");

                ImageView imageView = (ImageView) activity.findViewById(R.id.imageView);
                new DownloadImageTask(imageView).execute(image_url);

                JSONObject source = json_recpie_info.getJSONObject("source");
                RecipeActivity.directions_url = source.getString("sourceRecipeUrl");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}