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
    String social_rank;
    ArrayList<String> ingredients;
    String source_url;

    // Constructor
    Recipe(String title, String id, String image_url, String social_rank) {
        this.title = title;
        this.id = id;
        this.image_url = image_url;
        this.social_rank = social_rank;
    }

    // Constructor
    Recipe(String title, String id, String image_url, String social_rank, ArrayList<String> ingredients, String source_url) {
        this.title = title;
        this.id = id;
        this.image_url = image_url;
        this.social_rank = social_rank;
        this.ingredients = ingredients;
        this.source_url = source_url;
    }

    public String toString() {
        return title;
    }
}
