package com.example.u_jain.mobhackapp;

import java.util.HashMap;

/**
 * Created by U_Jain on 6/15/2017.
 */

public class DataContainer {

    DataEntity entities[] = new DataEntity[5];
    final String criticalities[] = {"high", "medium high", "medium", "medium low", "low"};

    final static String paramsCriticalities[][] = new String[5][];


    final static String paramsHigh[] = {"doctype",
                                    "title tag",
                                    "3xx error",
                                    "broken canonical",
                                    "noindex nofollow",
                                    "og:title",
                                    "missing meta keyword tag",
                                    "og:description"};

    final static String paramsMediumHigh[] = {"use of flash",
                                            "missing or empty meta description tag"};

    final static String paramsMedium[] = {"og:image",
                                            "page title same as h1 tag",
                                            "duplicate meta description tag",
                                            "meta keyword repeated",
                                            "static site map"};

    final static String paramsMediumLow[] = {"4xx error",
                                                "url upper case",
                                                "url underscore",
                                                "blank canonical",
                                                "page title 65 plus",
                                                "page title 30 minus",
                                                "page title pipe symbol 2 times"};

    final static String paramsLow[] = {"status code 200",
                                        "status code 5xx",
                                        "url lower case",
                                        "url hyphens",
                                        "self canonical",
                                        "missing canonical"};
    public class DataEntity {
        String citicalityMeasure;
        HashMap<String, String> paramHashMap;
    }

    public DataContainer()
    {
        int paramsLen = 0;
        for(int i=0; i<5; i++)
        {
            entities[i].citicalityMeasure = criticalities[i];
            entities[i].paramHashMap = new HashMap<>();

        }
        paramsCriticalities[0] = new String[paramsHigh.length];
        paramsCriticalities[1] = new String[paramsMediumHigh.length];
        paramsCriticalities[2] = new String[paramsMedium.length];
        paramsCriticalities[3] = new String[paramsMediumLow.length];
        paramsCriticalities[4] = new String[paramsLow.length];

        for(int i=0; i<5; i++)
        {
            for (String s :
                    paramsCriticalities[i]) {

            }
        }



    }
}
