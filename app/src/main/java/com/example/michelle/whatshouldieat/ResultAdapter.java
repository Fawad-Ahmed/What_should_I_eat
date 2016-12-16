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

/*
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

        // Inflates the view
        View view = inflater.inflate(R.layout.search_result_row, parent, false);
        Recipe item = getItem(position);

        // Sets the title
        TextView textView = (TextView) view.findViewById(R.id.titleView);
        textView.setText(item.toString());

        // Converts the courses to string and set them as subtitle
        TextView courseView = (TextView) view.findViewById(R.id.courseView);
        String courses = "";
        if (item.courses.size() > 0) {
            for (int i = 0; i < item.courses.size(); i++) {
                if (i != 0) {
                    courses += ", ";
                }
                courses += item.courses.get(i);
            }
            courseView.setText(courses);
        } else {
            courseView.setVisibility(View.GONE);
        }

        // Set the image
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        String image_url = item.image_url;
        new DownloadImageTask(imageView).execute(image_url);

        return view;
    }
}