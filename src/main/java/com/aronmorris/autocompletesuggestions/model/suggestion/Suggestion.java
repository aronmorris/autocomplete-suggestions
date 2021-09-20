package com.aronmorris.autocompletesuggestions.model.suggestion;

import com.aronmorris.autocompletesuggestions.model.geoname.GeonameEntry;
import com.aronmorris.autocompletesuggestions.model.admincode.AdminCodeMap;

import java.util.Locale;

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

    /*
    This method builds a full place name from the city name, admin division, and country code.
    Admin Divisions are internally defined by nations and are not consistent, but can be mapped to real
    names. See AdminCodeLoader for more.

    A Suggestion should have a full, proper place name baked in on construction since evaluation is done beforehand.
     */
    private static String buildLocationName(GeonameEntry entry) {
        String adminDivision;
        String countryName;

        countryName = new Locale("", entry.getCountryCode()).getDisplayCountry();
        adminDivision = AdminCodeMap.getAdminDivisionName(entry.getAdminDivision(), entry.getCountryCode());

        return entry.getName() + ", " +  adminDivision + ", " + countryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
