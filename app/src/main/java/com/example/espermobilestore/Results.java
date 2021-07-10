package com.example.espermobilestore;

import com.example.espermobilestore.model.FeatureResponse;

import java.util.ArrayList;
import java.util.Map;

public class Results {

    private static Results single_instance = null;
    public Map<String, String> resultsList;
    public ArrayList<FeatureResponse> featuresList;
    public int total_pages;

    public static Results getInstance() {
        if (single_instance == null)
            single_instance = new Results();

        return single_instance;
    }
}
