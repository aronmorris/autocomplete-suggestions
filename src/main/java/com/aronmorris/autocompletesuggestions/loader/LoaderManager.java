package com.aronmorris.autocompletesuggestions.loader;

import com.aronmorris.autocompletesuggestions.model.geoname.GeonameData;
import com.aronmorris.autocompletesuggestions.model.admincode.AdminCodeMap;
import com.aronmorris.autocompletesuggestions.model.admincode.loader.AdminCodeLoader;
import com.aronmorris.autocompletesuggestions.model.geoname.loader.GeonameFileLoader;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Manager class to ensure that data loads in controlled order, and where new data loaders can be "registered" to
 * load on startup. Loaders return some data structure which is then stored by dedicated container classes.
 */
@Component
public class LoaderManager implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        AdminCodeMap.setAdminCodeHashMap(AdminCodeLoader.loadFile());
        GeonameData.setGeonameEntries(GeonameFileLoader.loadFile());
    }
}
