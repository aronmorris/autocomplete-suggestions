package com.aronmorris.autocompletesuggestions.model.suggestion;

import com.aronmorris.autocompletesuggestions.model.geoname.GeonameEntry;

/**
 * POJO for Suggestions we want to include in the return to SuggestionController.
 */
public class Suggestion {
    private String name;
    private double latitude;
    private double longitude;
    private double confidence;

    public Suggestion(GeonameEntry entry, double confidence) {
        this.name = buildLocationName(entry);
        this.latitude = entry.getLatitude();
        this.longitude = entry.getLongitude();
        this.confidence = confidence;
    }

    // Names should include top level admin division & country code to distinguish places better
    private static String buildLocationName(GeonameEntry entry) {
        return entry.getName() + ", " + entry.getAdminDivision() + ", " + entry.getCountryCode();
    }
}
