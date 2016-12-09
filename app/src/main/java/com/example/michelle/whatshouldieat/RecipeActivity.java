package com.example.michelle.whatshouldieat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class RecipeActivity extends AppCompatActivity {
    public static String directions_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

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
