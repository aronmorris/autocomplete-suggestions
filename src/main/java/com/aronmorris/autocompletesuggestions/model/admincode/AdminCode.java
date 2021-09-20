package com.aronmorris.autocompletesuggestions.model.admincode;

/**
 * Admin Code POJO. Documentation for why this is needed is in associated Loader class.
 */
public class AdminCode {
    // file columns in tab-separated values: code, name, name ascii, geonameid
    // code is in COUNTRYCODE.ADMINDIVISION format
    private final String code;
    private final String name;
    private final String nameAscii;

    public AdminCode(String code, String name, String nameAscii) {
        this.code = code;
        this.name = name;
        this.nameAscii = nameAscii;
    }

    public String getNameAscii() {
        return nameAscii;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
