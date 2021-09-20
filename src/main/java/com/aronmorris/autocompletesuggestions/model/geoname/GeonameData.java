package com.aronmorris.autocompletesuggestions.model.geoname;

import com.aronmorris.autocompletesuggestions.model.geoname.loader.GeonameFileLoader;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Once data is loaded it's immutable, and given the expense of loading it we only want to do it once.
 */
@Component
public class GeonameData {

    private static List<GeonameEntry> geonameEntries;

    public static void setGeonameEntries(List<GeonameEntry> entries) {
        geonameEntries = entries;
    }

    public static List<GeonameEntry> getGeonameEntries() {
        return geonameEntries;
    }
}
