package com.aronmorris.autocompletesuggestions.matching.handlers;

import com.aronmorris.autocompletesuggestions.matching.handlers.api.ConfidenceHandler;
import com.aronmorris.autocompletesuggestions.model.geoname.GeonameEntry;
import com.aronmorris.autocompletesuggestions.model.suggestion.Suggestion;

import java.util.List;

/**
 * This handler is left unfinished for the time being, but it is not invoked at any point and has no impact
 * on confidence grading. It's left here as an example of how to add a new Confidence Handler and what
 * it may evaluate as part of a confidence grading flow.
 */
public class AlternateNameHandler implements ConfidenceHandler<GeonameEntry, Suggestion> {
    private final String query;
    private double weight;

    public AlternateNameHandler(String query, double weight) {
        this.query = query;
        this.weight = weight;
    }

    @Override
    public Suggestion process(GeonameEntry input, Suggestion modifiable) {
        /*
        if (isAlternateName(query, input.getAltNames())) {
            //additional checks such as common characters in main name & query name
        }
        */
        return modifiable;
    }

    @Override
    public void modifyWeight(double newWeight) {
        this.weight = newWeight;
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
