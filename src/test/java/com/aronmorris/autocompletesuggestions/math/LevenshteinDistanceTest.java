package com.aronmorris.autocompletesuggestions.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevenshteinDistanceTest {

    @Test
    void levenshteinDistance() {
        int distanceA = LevenshteinDistance.levenshteinDistance("Montreal", "Montreal1");
        int distanceB = LevenshteinDistance.levenshteinDistance("Montreal", "MontrealAAA");
        int distanceC = LevenshteinDistance.levenshteinDistance("Mnotreal", "Montreal");

        assertEquals(distanceA, 1);
        assertEquals(distanceB, 3);
        assertEquals(distanceC, 2);
    }
}