package com.example.michelle.whatshouldieat;

import java.util.ArrayList;

/**
 * Created by Michelle on 8-12-2016.
 * Holds a Recipe object
 */

public class Recipe {
    String title;
    String id;
    String image_url;
    ArrayList<String> ingredients;

    // Constructor
    Recipe(String title, String id, String image_url, ArrayList<String> ingredients) {
        this.title = title;
        this.id = id;
        this.image_url = image_url;
        this.ingredients = ingredients;
    }


    public String toString() {
        return title;
    }
}
