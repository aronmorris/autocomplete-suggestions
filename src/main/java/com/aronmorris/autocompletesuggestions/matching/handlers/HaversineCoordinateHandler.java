package com.aronmorris.autocompletesuggestions.matching.handlers;

import com.aronmorris.autocompletesuggestions.matching.handlers.api.ConfidenceHandler;
import com.aronmorris.autocompletesuggestions.math.HaversineCoordinateDistance;
import com.aronmorris.autocompletesuggestions.model.geoname.GeonameEntry;
import com.aronmorris.autocompletesuggestions.model.suggestion.Suggestion;

/**
 * This Confidence Handler is responsible for identifying which data items from the Cities500 set are within
 * a specified distance of the query coordinates. Any items that are not in that range are flagged as
 * NO_CONFIDENCE and can be removed by the Matcher.
 */
public class HaversineCoordinateHandler implements ConfidenceHandler<GeonameEntry, Suggestion> {
    private double weight;
    private final double queryLatitude;
    private final double queryLongitude;

    //300 km
    public final static double MAX_SEARCH_RADIUS_KM = 300;

    public HaversineCoordinateHandler(double weight, double latitude, double longitude) {
        this.weight = weight;
        this.queryLatitude = latitude;
        this.queryLongitude = longitude;
    }

    @Override
    public Suggestion process(GeonameEntry data, Suggestion modifiable) {
        double distance = HaversineCoordinateDistance.calculateHaversineDistance(
                this.queryLatitude,
                this.queryLongitude,
                modifiable.getLatitude(),
                modifiable.getLongitude()
        );
        if (distance <= MAX_SEARCH_RADIUS_KM) {
            modifiable.setConfidence(
                    modifiable.getConfidence() + ((1 - (distance / MAX_SEARCH_RADIUS_KM)) * weight)
            );
        } else {
            modifiable.setConfidence(NO_CONFIDENCE);
        }
        return modifiable;
    }

    @Override
    public void modifyWeight(double newWeight) {
        this.weight = newWeight;
    }
}
