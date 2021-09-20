package com.aronmorris.autocompletesuggestions.matching;

import com.aronmorris.autocompletesuggestions.matching.factory.HandlerFactory;
import com.aronmorris.autocompletesuggestions.matching.handlers.api.ConfidenceHandler;
import com.aronmorris.autocompletesuggestions.math.MathHelper;
import com.aronmorris.autocompletesuggestions.model.geoname.GeonameData;
import com.aronmorris.autocompletesuggestions.model.geoname.GeonameEntry;
import com.aronmorris.autocompletesuggestions.model.suggestion.Suggestion;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The Matcher is responsible for performing the fuzzy match operations. It calls a factory that builds a list
 * of confidence-grading handlers based on the nature of the request's parameters, and then creates a list
 * of suggestions to be returned based on the overall confidence grading of the handlers. If a Handler
 * flags a suggestion as NO_CONFIDENCE at any point, it is immediately removed and the next suggestion's
 * evaluation begins.
 */
public class Matcher {

    public static final double THRESHOLD_CONFIDENCE = 0.1;

    public List<Suggestion> getSuggestions(String query, double latitude, double longitude) {

        List<ConfidenceHandler<GeonameEntry, Suggestion>> handlers = HandlerFactory
                .getHandlers(query, latitude, longitude);

        List<Suggestion> suggestions = GeonameData.getGeonameEntries().stream()
                .map(geonameEntry -> {
                    Suggestion suggestion = new Suggestion(geonameEntry, 0.0);
                    for (ConfidenceHandler<GeonameEntry, Suggestion> handler : handlers) {
                        suggestion = handler.process(geonameEntry, suggestion);
                        if (suggestion.getConfidence() == ConfidenceHandler.NO_CONFIDENCE) {
                            suggestion = null;
                            break;
                        }
                    }
                    return suggestion;
                })
                .filter(Objects::nonNull)
                .filter(suggestion -> suggestion.getConfidence() >= THRESHOLD_CONFIDENCE)
                .sorted(Comparator.comparing(Suggestion::getConfidence, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        suggestions.forEach(suggestion ->
                suggestion.setConfidence(MathHelper.roundValue(suggestion.getConfidence()))
        );

        return suggestions;
    }
}
