package com.aronmorris.autocompletesuggestions.api;

import com.aronmorris.autocompletesuggestions.model.geoname.loader.GeonameFileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SuggestionController {

    Logger logger = LoggerFactory.getLogger(SuggestionController.class);

    // Adding /v1/ as a versioning method - future API may not call just these attribs.
    @GetMapping(value = "/v1/suggestions")
    public List<String> getSuggestions(
            @RequestParam String query,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude
    ) {
        GeonameFileLoader loader = new GeonameFileLoader();
        loader.loadFile();
        return new ArrayList<>();
    }
}
