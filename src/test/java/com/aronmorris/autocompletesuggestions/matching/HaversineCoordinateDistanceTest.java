package com.aronmorris.autocompletesuggestions.matching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HaversineCoordinateDistanceTest {

    @Test
    void getHaversineDistanceScore() {
        assertEquals(1.0, HaversineCoordinateDistance
                .getHaversineDistanceScore(90, 180, 90, 180)
        );
        assertEquals(-1.0, HaversineCoordinateDistance
                .getHaversineDistanceScore(0    , 90, 90, 180)
        );
    }
}