package com.aronmorris.autocompletesuggestions.api;

import com.aronmorris.autocompletesuggestions.matching.Matcher;
import com.aronmorris.autocompletesuggestions.model.suggestion.Suggestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The policy as I'm familiar with it is for controllers to do as little as possible and offload all possible logic
 * to the model(s). This controller therefore does very little beyond receive the request, instantiate the matcher,
 * and send off the compiled list of suggestions.
 */
@RestController
public class SuggestionController {

    Logger logger = LoggerFactory.getLogger(SuggestionController.class);

    // Adding /v1/ as a versioning method - future API may not call just these attribs.
    @GetMapping(value = "/v1/suggestions")
    public List<Suggestion> getSuggestions(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "-999") Double latitude,
            @RequestParam(required = false, defaultValue = "-999") Double longitude
    ) {
        logger.info("Received request with query \"{}\", latitude {}, longitude {}", query, latitude, longitude);
        Matcher sm = new Matcher();
        return sm.getSuggestions(query, latitude, longitude);
    }
}
