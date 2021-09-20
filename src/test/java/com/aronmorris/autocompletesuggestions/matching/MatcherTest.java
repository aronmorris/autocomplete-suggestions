package com.aronmorris.autocompletesuggestions.matching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatcherTest {

    @Test
    void getSuggestions() {
        Matcher matcher = new Matcher();

        assertFalse(matcher.getSuggestions("Montreal", 45.49666, -73.63989).isEmpty());
    }
}