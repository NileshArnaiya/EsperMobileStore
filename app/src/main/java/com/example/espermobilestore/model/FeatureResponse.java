package com.example.espermobilestore.model;

import java.io.Serializable;
import java.util.List;

public class FeatureResponse implements Serializable {


    private String feature_id;
    private String name;
    private List<FeatureResponse> features;

    public List<FeatureResponse> getFeatures() {
        return features;
    }


}
