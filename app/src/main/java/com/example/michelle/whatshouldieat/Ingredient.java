package com.example.michelle.whatshouldieat;

/**
 * Created by Michelle on 8-12-2016.
 * Holds an ingredient object.
 */

public class Ingredient {
    public String title;
    public Boolean selected;

    public Ingredient (String title, Boolean selected) {
        this.title = title;
        this.selected = selected;
    }

    public Ingredient switch_selection() {
        selected = !selected;
        return this;
    }

    public String toString() {
        return title;
    }
}
