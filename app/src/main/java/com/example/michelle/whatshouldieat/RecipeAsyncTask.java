package com.example.michelle.whatshouldieat;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Gets the recipe from the HttpHelper and puts in a ListView.
 * Also, sets the Firebase to add ingredients to the grocery list.
 */

class RecipeAsyncTask extends AsyncTask<String, Integer, String> {
    private Context context;
    private RecipeActivity activity;

    // Firebase URL
    private static final String FIREBASE_URL = "https://fir-whatshouldieat.firebaseio.com/";

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

                // Title
                String title = json_recpie_info.getString("name");
                activity.setTitle(title);

                // Get ingredients
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                JSONArray jsonArray_ingredients = json_recpie_info.getJSONArray("ingredientLines");

                for (int i = 0; i < jsonArray_ingredients.length(); i++) {
                    String key = "" + System.nanoTime();
                    Ingredient ingredient = new Ingredient(jsonArray_ingredients.get(i).toString(), true, key);
                    ingredients.add(ingredient);
                }

                // Set results to adapter
                final IngredientsAdapter adapter = new IngredientsAdapter(activity, ingredients);
                ListView listView = (ListView) activity.findViewById(R.id.ingredients_listView);
                listView.setAdapter(adapter);

                JSONArray images = json_recpie_info.getJSONArray("images");
                JSONObject image_urls = images.getJSONObject(0);
                String image_url = image_urls.getString("hostedLargeUrl");

                ImageView imageView = (ImageView) activity.findViewById(R.id.imageView);
                new DownloadImageTask(imageView).execute(image_url);

                JSONObject source = json_recpie_info.getJSONObject("source");
                RecipeActivity.directions_url = source.getString("sourceRecipeUrl");

                String courses = "";
                if (json_recpie_info.has("attributes")) {
                    JSONObject attributes = json_recpie_info.getJSONObject("attributes");

                    if (attributes.has("course")) {
                        JSONArray json_courses = attributes.getJSONArray("course");
                        System.out.println("COURSE" + json_courses);

                        for (int j = 0; j < json_courses.length(); j++) {
                            if (j != 0) {
                                courses += ", ";
                            }
                            courses += json_courses.getString(j);
                        }
                    }
                }

                TextView courseView = (TextView)activity.findViewById(R.id.courseView);
                courseView.setText(courses);

                // Set the Firebase Database
                Firebase.setAndroidContext(context);
                Firebase mRootRef = new Firebase(FIREBASE_URL);

                Firebase firebaseRef = mRootRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                // Get the ingredients list from the database
                final Firebase groceriesRef = firebaseRef.child("groceries");

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        // Perform action when clicked on
                        Ingredient ingredient = adapter.getItem(pos);
                        groceriesRef.child(ingredient.key).setValue(ingredient);
                        Toast.makeText(context, ingredient.title + " is added to my groceries", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}