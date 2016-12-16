package com.example.michelle.whatshouldieat;

import java.util.ArrayList;

/**
 * Holds a Recipe object. A recipe object contains a title, an id,
 * an image url, ingredients and courses.
 */

public class Recipe {
    String title;
    String id;
    String image_url;
    ArrayList<String> ingredients;
    ArrayList<String> courses;

    // Constructor
    Recipe(String title, String id, String image_url, ArrayList<String> ingredients, ArrayList<String> courses) {
        this.title = title;
        this.id = id;
        this.image_url = image_url;
        this.ingredients = ingredients;
        this.courses = courses;
    }


    public String toString() {
        return title;
    }
}
