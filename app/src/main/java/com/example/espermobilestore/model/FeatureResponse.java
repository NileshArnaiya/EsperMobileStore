package com.example.espermobilestore.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FeatureResponse implements Serializable {

    @SerializedName("feature_id")
    private String feature_id;

    @SerializedName("name")
    private String name;

    @SerializedName("features")
    private List<FeatureResponse> features;

    public List<FeatureResponse> getFeatures() {
        return features;
    }

    @SerializedName("options")
    private List<Options> options;

    @SerializedName("exclusions")
    private List<List<Exclusion>> exclusions;

    public List<List<Exclusion>> getExclusions() {
        return exclusions;
    }

    public String getFeature_id() {
        return feature_id;
    }

    public String getName() {
        return name;
    }


    public List<Options> getOptions() {
        return options;
    }

}
