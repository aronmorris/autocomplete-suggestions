package com.aronmorris.autocompletesuggestions.model.geoname;

import java.util.List;

/**
 * This class is a POJO that contains the information we care about from the Geonames Cities500 file.
 * The file has more columns than this, so there's an associated Builder that can add more fields
 * if they're ever needed.
 */
public class GeonameEntry {
    private String name;
    private String nameASCII;
    private List<String> altNames;
    private double latitude;
    private double longitude;
    private long population;

    public GeonameEntry(
            String name,
            String nameASCII,
            List<String> altNames,
            double latitude,
            double longitude,
            long population
    ) {
        this.name = name;
        this.nameASCII = nameASCII;
        this.altNames = altNames;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public String getNameASCII() {
        return nameASCII;
    }

    public List<String> getAltNames() {
        return altNames;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getPopulation() {
        return population;
    }
}
