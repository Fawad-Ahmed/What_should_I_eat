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
                JSONObject recipe = new JSONObject(result);
                String recpie_info = recipe.getString("recipe");
                JSONObject json_recpie_info = new JSONObject(recpie_info);

                String title = json_recpie_info.getString("title");
                activity.setTitle(title);

                // Get ingredients
                ArrayList<String> ingredients = new ArrayList<>();
                JSONArray jsonArray_ingredients = json_recpie_info.getJSONArray("ingredients");

                for (int i = 0; i < jsonArray_ingredients.length(); i++) {
                    System.out.println(jsonArray_ingredients.get(i).toString());
                    ingredients.add(jsonArray_ingredients.get(i).toString());
                }

                // Set results to adapter
                IngredientsAdapter adapter = new IngredientsAdapter(activity, ingredients);
                ListView listView = (ListView) activity.findViewById(R.id.ingredients_listView);
                listView.setAdapter(adapter);

                String image_url = json_recpie_info.getString("image_url");
                ImageView imageView = (ImageView) activity.findViewById(R.id.imageView);
                new DownloadImageTask(imageView).execute(image_url);

                String source_url = json_recpie_info.getString("source_url");
                RecipeActivity.directions_url = source_url;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}