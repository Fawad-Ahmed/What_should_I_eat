package com.example.michelle.whatshouldieat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/*
 * The activity in which the recipe is showed. The user can
 * click ingredients to add them to their grocery list.
 * Also the directions page can be viewed.
 */

public class RecipeActivity extends AppCompatActivity {
    public static String directions_url;
    public static String acc_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // Get id's
        Intent intent = getIntent();
        String recipe_id = intent.getStringExtra("id"); // Recipe id
        acc_id = intent.getStringExtra("acc_id"); // User account id

        // Retrieve info with infoAsyncTask
        final RecipeAsyncTask infoAsyncTask = new RecipeAsyncTask(this);
        infoAsyncTask.execute(recipe_id);
    }

    // The action to perform then the view on website button is clicked.
    public void viewOnWebsite(View view) {
        Intent intent = new Intent(this, ViewDirections.class);
        intent.putExtra("url", directions_url);
        System.out.println("URL " + directions_url);
        intent.putExtra("title", getTitle().toString());
        startActivity(intent);
    }
}
