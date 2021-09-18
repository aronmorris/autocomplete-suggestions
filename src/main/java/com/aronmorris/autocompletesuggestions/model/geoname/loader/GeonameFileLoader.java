package com.aronmorris.autocompletesuggestions.model.geoname.loader;

import com.aronmorris.autocompletesuggestions.model.geoname.GeonameEntry;
import com.aronmorris.autocompletesuggestions.model.geoname.GeonameEntryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Per Geonames README at https://download.geonames.org/export/dump/
 * The data source for this microservice is the Geonames Cities500 file. This file is provided in
 * tab-delimited text in utf8 encoding. Because reading a file is slow and access to specific
 * items can be clumsy, we load the file's contents here into the data structure of choice
 */
public class GeonameFileLoader {
    final String DATA_FILE_NAME = "src/main/resources/data/cities500.txt";
    final String FILE_ENCODING = "utf-8";

    // from the Geonames README, column numbers, index starts at 0
    final int INDEX_CITY_NAME = 1;
    final int INDEX_ASCII_NAME = 2;
    final int INDEX_ALT_NAMES = 3;
    final int INDEX_LATITUDE = 4;
    final int INDEX_LONGITUDE = 5;
    final int INDEX_POPULATION = 14;

    Logger logger = LoggerFactory.getLogger(GeonameFileLoader.class);

    public List<GeonameEntry> loadFile() {
        List<GeonameEntry> geoEntries = new ArrayList<>();

        try {
            logger.info("Starting Geonames file load.");
            BufferedReader buf = new BufferedReader(new FileReader(DATA_FILE_NAME));
            GeonameEntryBuilder geoBuilder = new GeonameEntryBuilder();
            String currLine;
            String[] entryArray;
            while (true) {
                currLine = buf.readLine();
                if (currLine == null) {
                    break;
                } else {
                    entryArray = currLine.split("\t");
                    geoEntries.add(
                            geoBuilder
                                .setName(entryArray[INDEX_CITY_NAME])
                                .setNameASCII(entryArray[INDEX_ASCII_NAME])
                                .setAltNames(Arrays.asList(entryArray[INDEX_ALT_NAMES].split(",")))
                                .setLatitude(INDEX_LATITUDE).setLongitude(INDEX_LONGITUDE)
                                .setPopulation(INDEX_POPULATION)
                                .createGeonameEntry()
                    );
                }
            }
            logger.info("File load successful.");
        } catch (IOException e) {
            logger.error("Something went wrong loading the data.");
            e.printStackTrace();
        }
        return geoEntries;
    }
}
