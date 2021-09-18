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
public class GeonameData implements ApplicationListener<ApplicationReadyEvent> {

    private static List<GeonameEntry> geonameEntries;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        geonameEntries = GeonameFileLoader.loadFile();
    }

    public static List<GeonameEntry> getGeonameEntries() {
        return geonameEntries;
    }
}
