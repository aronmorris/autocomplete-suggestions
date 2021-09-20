package com.aronmorris.autocompletesuggestions.matching.handlers;

import com.aronmorris.autocompletesuggestions.matching.handlers.api.ConfidenceHandler;
import com.aronmorris.autocompletesuggestions.model.geoname.GeonameEntry;
import com.aronmorris.autocompletesuggestions.model.suggestion.Suggestion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HaversineCoordinateHandlerTest {

    @Test
    void process() {
        GeonameEntry data = new GeonameEntry(
                "Montreal",
                "Montreal",
                new ArrayList<>(),
                45.49666,
                -73.63989,
                12222220,
                "10",
                "CA"
        );
        Suggestion suggestion = new Suggestion(
                data,
                0.0
        );
        HaversineCoordinateHandler handler = new HaversineCoordinateHandler(1.0, 80, 90);
        suggestion =  handler.process(data, suggestion);
        assertEquals(suggestion.getConfidence(), ConfidenceHandler.NO_CONFIDENCE);
    }
}