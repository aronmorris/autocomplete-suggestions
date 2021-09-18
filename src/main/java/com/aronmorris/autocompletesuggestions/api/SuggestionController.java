package com.aronmorris.autocompletesuggestions.api;

import com.aronmorris.autocompletesuggestions.model.geoname.GeonameData;
import com.aronmorris.autocompletesuggestions.model.geoname.GeonameEntry;
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
    public List<GeonameEntry> getSuggestions(
            @RequestParam String query,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude
    ) {
        logger.info("Received request with query \"{}\", latitude {}, longitude {}", query, latitude, longitude);
        return GeonameData.getGeonameEntries().subList(0, 10);
    }
}
