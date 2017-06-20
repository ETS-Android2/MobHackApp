package com.example.u_jain.mobhackapp;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by U_Jain on 6/15/2017.
 */

public class DataContainer implements Serializable {

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
        String criticalityMeasure;
        HashMap<String, String> paramHashMap;
    }


    public String[][] allValues()
    {
        String str[][] = new String[28][3];
        int k=0;
        for(int i=0; i<5; i++)
        {
            Set<String> set = entities[i].paramHashMap.keySet();
            Iterator<String> iterator = set.iterator();
            while(iterator.hasNext())
            {
                String s=iterator.next();
                str[k][2]=entities[i].paramHashMap.get(s);
                str[k][1]=entities[i].criticalityMeasure;
                str[k][0]=s;
                k++;
            }
        }
        return  str;
    }

    public DataContainer(Context context)
    {
        paramsCriticalities[0] = new String[paramsHigh.length];
        paramsCriticalities[0] = paramsHigh;
        paramsCriticalities[1] = new String[paramsMediumHigh.length];
        paramsCriticalities[1] = paramsMediumHigh;
        paramsCriticalities[2] = new String[paramsMedium.length];
        paramsCriticalities[2] = paramsMedium;
        paramsCriticalities[3] = new String[paramsMediumLow.length];
        paramsCriticalities[3] = paramsMediumLow;
        paramsCriticalities[4] = new String[paramsLow.length];
        paramsCriticalities[4] = paramsLow;
        for(int i=0; i<5; i++)
        {
            entities[i] = new DataEntity();
            entities[i].criticalityMeasure = new String();
            entities[i].criticalityMeasure = criticalities[i];
            entities[i].paramHashMap = new HashMap<>();
            for (String s :
                    paramsCriticalities[i]) {
                entities[i].paramHashMap.put(s,"");
            }
        }

        for(int i=0; i<5; i++)
        {
            Set<String> set = entities[i].paramHashMap.keySet();
            Iterator<String> iterator = set.iterator();
            while(iterator.hasNext())
            {
                Toast.makeText(context, iterator.next(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
