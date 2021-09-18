package com.aronmorris.autocompletesuggestions.model.suggestion;

import java.util.List;

public interface SuggestionResponse {
    List<Suggestion> getSuggestions(String query, double latitude, double longitude);
}
