package com.aronmorris.autocompletesuggestions.matching;

import com.aronmorris.autocompletesuggestions.math.MathHelper;
import com.aronmorris.autocompletesuggestions.model.geoname.GeonameData;
import com.aronmorris.autocompletesuggestions.model.suggestion.Suggestion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The Matcher is responsible for performing the heavy lifting of fuzzy matching. It checks the given query
 * and calls the various operations that can grade the Geoname data set
 */
public class Matcher {

    public static final double MAX_CONFIDENCE = 1.0;
    public static final double THRESHOLD_CONFIDENCE = 0.1;

    public List<Suggestion> getSuggestions(String query, double latitude, double longitude) {
        List<Suggestion> suggestions = GeonameData.getGeonameEntries().stream()
                .map(geonameEntry -> {
                    //if it's an alt-name of a place assume max confidence, otherwise run the normal math
                    double confidence =
                            isAlternateName(query, geonameEntry.getAltNames()) ?
                                    MAX_CONFIDENCE :
                                    LevenshteinDistance.normalizedDistanceScore(query, geonameEntry.getName());
                    //may as well save memory by not instantiating objects that get immediately discarded
                    return confidence > THRESHOLD_CONFIDENCE ? new Suggestion(geonameEntry, confidence) : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        //check that latitude & longitude are bounded in their real ranges
        if ((latitude <= 90.0 && latitude >= -90.0) && (longitude <= 180.0 && longitude >= -180.0)) {
            suggestions
                .forEach(suggestion -> {
                    double gpsScore = HaversineCoordinateDistance.getHaversineDistanceScore(
                            latitude,
                            longitude,
                            suggestion.getLatitude(),
                            suggestion.getLongitude()
                    );
                    // Modified Haversine returns -1 if the suggestion's coordinates are out of some bounded range
                    // This lets us flag it for deletion of low confidence items
                    suggestion.setConfidence(MathHelper.roundValue((suggestion.getConfidence() + gpsScore) / 2));
                });
        }
        suggestions = suggestions.stream()
                .filter(suggestion -> suggestion.getConfidence() >= THRESHOLD_CONFIDENCE)
                .sorted(Comparator.comparing(Suggestion::getConfidence, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        return suggestions;
    }
    private boolean isAlternateName(String query, List<String> names) {
        for (String name : names) {
            if (query.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
