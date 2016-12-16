package com.example.michelle.whatshouldieat;

/**
 * An allergy object. A diet object is also considered an allergy object.
 * It hold the title and the selection state of an allergy.
 */

public class Allergy {
    public String title;
    public Boolean selected;

    public Allergy (String title, Boolean selected) {
        this.title = title;
        this.selected = selected;
    }

    public String toString() {
        return title;
    }
}
