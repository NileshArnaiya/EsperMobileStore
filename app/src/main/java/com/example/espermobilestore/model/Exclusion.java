package com.example.espermobilestore.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Exclusion implements Serializable {

    @SerializedName("feature_id")
    private String feature_id;
    @SerializedName("options_id")
    private String options_id;

    @SerializedName("exclusions")
    private List<Exclusion> exclusions;

    public List<Exclusion> getExclusions() {
        return exclusions;
    }

    public String getFeature_id() {
        return feature_id;
    }

    public void setFeature_id(String feature_id) {
        this.feature_id = feature_id;
    }

    public String getOptions_id() {
        return options_id;
    }

    public void setOptions_id(String options_id) {
        this.options_id = options_id;
    }
}