package com.aronmorris.autocompletesuggestions.model;

import com.aronmorris.autocompletesuggestions.model.geoname.GeonameEntry;

public class Suggestion {
    public String name;
    public double latitude;
    public double longitude;
    public double confidence;

    public Suggestion(GeonameEntry entry) {
    }
}
