package com.aronmorris.autocompletesuggestions.model.admincode;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Unlike the GeonameEntry data structure, we don't need to check every single Admin Code to perform a fuzzy match.
 * Instead we can use a Map for fast lookup of the admin division name by combining Country Code & Admin Division
 * as a key.
 */
@Component
public class AdminCodeMap {

    private static HashMap<String, AdminCode> adminCodeHashMap;

    public static void setAdminCodeHashMap(HashMap<String, AdminCode> adminMap) {
        adminCodeHashMap = adminMap;
    }

    public static String getAdminDivisionName(String geoNameDivision, String countryCode) {
        AdminCode ac = adminCodeHashMap.get(countryCode + '.' + geoNameDivision);
        if (ac == null) {
            return "";
        } else {
            return ac.getName() == null ? "" : ac.getName();
        }
    }
}
