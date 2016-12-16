package com.example.michelle.whatshouldieat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
 * This is a simple activity that contains a few textviews to show
 * the user some 'about' info.
 */

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle("About"); // Set the title
    }
}
