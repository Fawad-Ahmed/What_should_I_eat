package com.example.michelle.whatshouldieat;

/**
 * Holds an ingredient object. An ingredient object contains a
 * title, a state of selection and a key.
 */

public class Ingredient {
    public String title;
    public Boolean selected;
    public String key;

    public Ingredient (String title, Boolean selected, String key) {
        this.title = title;
        this.selected = selected;
        this.key = key;
    }

    public Ingredient switch_selection() {
        selected = !selected;
        return this;
    }

    public String toString() {
        return title;
    }
}
