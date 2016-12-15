package com.example.michelle.whatshouldieat;

/**
 * Created by Michelle on 14-12-2016.
 * An object that holds an allergy.
 */

public class Allergy {
    public String title;
    public Boolean selected;
    public String key;

    public Allergy (String title, Boolean selected) {
        this.title = title;
        this.selected = selected;
    }

    public Allergy switch_selection() {
        selected = !selected;
        return this;
    }

    public String toString() {
        return title;
    }
}
