package com.example.michelle.whatshouldieat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Search bar
        final EditText search_bar = (EditText)findViewById(R.id.search_bar);
        // When enter is pressed in the search bar, call search method
        search_bar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search(search_bar.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    // Creates the intent and starts result activity
    public void search(String tag) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("tag", tag);
        startActivity(intent);
    }

}
