package com.aronmorris.autocompletesuggestions.model.geoname;

import java.util.List;

/**
 * A Builder for the GeonameEntry object. If the object ever needs more data, we can start extending it here
 * without breaking everything that's already in place.
 */
public class GeonameEntryBuilder {
    private String name;
    private String nameASCII;
    private List<String> altNames;
    private double latitude;
    private double longitude;
    private long population;
    private String adminDivision;
    private String countryCode;

    public GeonameEntryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public GeonameEntryBuilder setNameASCII(String nameASCII) {
        this.nameASCII = nameASCII;
        return this;
    }

    public GeonameEntryBuilder setAltNames(List<String> altNames) {
        this.altNames = altNames;
        return this;
    }

    public GeonameEntryBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public GeonameEntryBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public GeonameEntryBuilder setPopulation(long population) {
        this.population = population;
        return this;
    }

    public GeonameEntryBuilder setAdministrativeDivision(String adminDivision) {
        this.adminDivision = adminDivision;
        return this;
    }

    public GeonameEntryBuilder setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public GeonameEntry createGeonameEntry() {
        return new GeonameEntry(
                name,
                nameASCII,
                altNames,
                latitude,
                longitude,
                population,
                adminDivision,
                countryCode
        );
    }
}