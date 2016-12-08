package com.example.michelle.whatshouldieat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Michelle on 8-12-2016.
 * Sets the ListView for the search results
 */

public class ResultAdapter extends ArrayAdapter<Recipe> {

    public ResultAdapter(Context context, ArrayList<Recipe> values) {
        super(context, R.layout.search_result_row, values);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.search_result_row, parent, false);
        Recipe item = getItem(position);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(item.toString());

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        String image_url = item.image_url;
        new DownloadImageTask(imageView).execute(image_url);

        return view;
    }
}