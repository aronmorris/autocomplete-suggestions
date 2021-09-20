package com.aronmorris.autocompletesuggestions.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HaversineCoordinateDistanceTest {

    @Test
    void calculateHaversineDistance() {
        double distance = HaversineCoordinateDistance.calculateHaversineDistance(
                38.898556, -77.037852,
                38.897147, -77.043934
        );
        assertEquals(MathHelper.roundValue(distance), 0.549);
    }
}