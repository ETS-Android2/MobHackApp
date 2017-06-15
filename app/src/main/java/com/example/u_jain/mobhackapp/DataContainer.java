package com.example.u_jain.mobhackapp;

import java.util.HashMap;

/**
 * Created by U_Jain on 6/15/2017.
 */

public class DataContainer {

    DataEntity entities[] = new DataEntity[5];
    final String criticalities[] = {"high", "medium high", "medium", "medium low", "low"};

    public class DataEntity {
        String citicalityMeasure;
        HashMap<String, String> paramHashMap;
    }


    public DataContainer()
    {
        for(int i=0; i<5; i++)
        {
            entities[i].citicalityMeasure = criticalities[i];
            entities[i].paramHashMap = new HashMap<>();
            
        }
    }


}
