package com.aronmorris.autocompletesuggestions.matching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LevenshteinDistanceTest {

    @Test
    void levenshteinDistanceTest() {
        assertEquals(1.0, LevenshteinDistance.normalizedDistanceScore("Montreal", "Montreal"));
    }
}