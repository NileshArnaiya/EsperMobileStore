package com.example.espermobilestore.api;

import com.example.espermobilestore.model.FeatureResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BaseApiService {

    @GET("/mhrpatel12/esper-assignment/db")
    Call<FeatureResponse> getFeatures();

}
