package com.aronmorris.autocompletesuggestions.matching.factory;

import com.aronmorris.autocompletesuggestions.matching.handlers.AlternateNameHandler;
import com.aronmorris.autocompletesuggestions.matching.handlers.HaversineCoordinateHandler;
import com.aronmorris.autocompletesuggestions.matching.handlers.api.ConfidenceHandler;
import com.aronmorris.autocompletesuggestions.matching.handlers.LevenshteinDistanceHandler;
import com.aronmorris.autocompletesuggestions.model.geoname.GeonameEntry;
import com.aronmorris.autocompletesuggestions.model.suggestion.Suggestion;

import java.util.ArrayList;
import java.util.List;

/**
 * The HandlerFactory maintains responsibility for creating the list of handlers and their scoring weights
 * based on the parameters handed in.
 * <p>
 * It should be possible to modify by overloading getHandlers() for any arbitrary set of inputs to
 * add more custom behavior, such as implementing the AlternateNameHandler or taking custom user inputs
 * into account, such as the maximum coordinate distance radius.
 * <p>
 * Different configurations of handlers can have different weighing considerations.
 */
public class HandlerFactory {

    public static List<ConfidenceHandler<GeonameEntry, Suggestion>> getHandlers(
            String query,
            double latitude,
            double longitude
    ) {
        List<ConfidenceHandler<GeonameEntry, Suggestion>> handlers = new ArrayList<>();
        LevenshteinDistanceHandler levenshteinDistanceHandler = new LevenshteinDistanceHandler(query, 1.0);

        if ((latitude <= 90.0 && latitude >= -90.0) && (longitude <= 180.0 && longitude >= -180.0)) {
            HaversineCoordinateHandler haversineCoordinateHandler = new HaversineCoordinateHandler(
                    0.8,
                    latitude,
                    longitude
            );

            levenshteinDistanceHandler.modifyWeight(0.2);

            handlers.add(levenshteinDistanceHandler);
            handlers.add(haversineCoordinateHandler);
        } else {
            handlers.add(levenshteinDistanceHandler);
        }
        return handlers;
    }
}
