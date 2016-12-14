package com.example.michelle.whatshouldieat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RecipeActivity extends AppCompatActivity {
    public static String directions_url;
    public static String acc_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        acc_id = intent.getStringExtra("acc_id");

        // Retrieve info with infoAsyncTask
        final RecipeAsyncTask infoAsyncTask = new RecipeAsyncTask(this);
        infoAsyncTask.execute(id);

    }

    public void viewOnWebsite(View view) {
        Intent intent = new Intent(this, ViewDirections.class);
        intent.putExtra("url", directions_url);
        System.out.println("URL " + directions_url);
        intent.putExtra("title", getTitle().toString());
        startActivity(intent);
    }


}
