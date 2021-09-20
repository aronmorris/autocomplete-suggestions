package com.aronmorris.autocompletesuggestions.model.admincode.loader;

import com.aronmorris.autocompletesuggestions.model.admincode.AdminCode;
import com.aronmorris.autocompletesuggestions.model.geoname.loader.GeonameFileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The Cities500 file does not provide all the useful information we need. Example:
 * 6077243
 * Montréal
 * Montreal
 * [Alternate Names Omitted For Brevity]
 * 45.50884
 * -73.58781
 * P
 * PPLA2
 * CA <- COUNTRY CODE
 * 10 <- ADMIN DIVISION
 * 06
 * 66023
 * 1600000
 * 216
 * America/Toronto <- Timezone, mostly useless for name purposes
 * 2019-02-25
 * <p>
 * As we can see with that data snippet, there's no useful distinction of Top-Level Admin Division: Quebec.
 * However, we can match CountryCode.AdminCode (here CA.10) against the top_level_admin_divisions.txt file
 * to get Quebec and thereby augment the results from "Montreal, 10, CA" to "Montreal, Quebec, CA".
 */
public class AdminCodeLoader {

    static Logger logger = LoggerFactory.getLogger(GeonameFileLoader.class);

    final static String DATA_FILE_NAME = "src/main/resources/data/top_level_admin_divisions.txt";

    //Column ids
    final static int INDEX_CODE = 0;
    final static int INDEX_NAME = 1;
    final static int INDEX_NAME_ASCII = 2;

    public static HashMap<String, AdminCode> loadFile() {
        HashMap<String, AdminCode> adminCodeMap = new HashMap<>();

        try {
            logger.info("Starting Admin Codes file load.");
            BufferedReader buf = new BufferedReader(new FileReader(DATA_FILE_NAME));
            String currLine;
            String[] entryArray;
            while (true) {
                currLine = buf.readLine();
                if (currLine == null) {
                    break;
                } else {
                    entryArray = currLine.split("\t");
                    AdminCode ac = new AdminCode(
                            entryArray[INDEX_CODE],
                            entryArray[INDEX_NAME],
                            entryArray[INDEX_NAME_ASCII]
                    );
                    adminCodeMap.put(ac.getCode(), ac);
                }
            }
            logger.info("File load successful.");
        } catch (IOException e) {
            logger.error("Something went wrong loading the data.");
            e.printStackTrace();
        }
        return adminCodeMap;
    }

}
