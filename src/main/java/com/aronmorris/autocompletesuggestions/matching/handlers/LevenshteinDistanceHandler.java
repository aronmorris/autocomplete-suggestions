package com.aronmorris.autocompletesuggestions.matching.handlers;

import com.aronmorris.autocompletesuggestions.matching.handlers.api.ConfidenceHandler;
import com.aronmorris.autocompletesuggestions.math.LevenshteinDistance;
import com.aronmorris.autocompletesuggestions.math.MathHelper;
import com.aronmorris.autocompletesuggestions.model.geoname.GeonameEntry;
import com.aronmorris.autocompletesuggestions.model.suggestion.Suggestion;

/**
 * The Levenshtein Distance Handler is responsible for performing a general fuzzy match by
 * using the Levenshtein Distance algorithm. If a given string is outside of some edit distance
 * value, the handler marks it as NO_CONFIDENCE so it can be removed.
 */
public class LevenshteinDistanceHandler implements ConfidenceHandler<GeonameEntry, Suggestion> {
    private double weight;
    private final String query;

    public final static int MAX_EDIT_DISTANCE = 3;
    public final static double MAXIMUM_MATCH = 1.0;

    public LevenshteinDistanceHandler(String query, double weight) {
        this.query = query;
        this.weight = weight;
    }

    @Override
    public Suggestion process(GeonameEntry data, Suggestion modifiable) {

        String query = this.query.toLowerCase();
        String target = data.getName().toLowerCase();

        //There's no reason to spend time on the calculation if we have identical strings
        if (query.equals(target)) {
            modifiable.setConfidence(MAXIMUM_MATCH * weight);
            return modifiable;
        }

        double levenshteinDistance = LevenshteinDistance.levenshteinDistance(query, target);

        //if the edit distance is too big the value can be discarded, otherwise it gets a grade based on
        //edit distance and relative difference in string size
        if (!(levenshteinDistance > MAX_EDIT_DISTANCE)) {
            modifiable.setConfidence(MathHelper.roundValue(
                            (1 - (levenshteinDistance / Math.max(query.length(), target.length()))) * weight
                    )
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
