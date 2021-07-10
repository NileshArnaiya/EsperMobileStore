package com.example.espermobilestore;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.espermobilestore.api.BaseApiClient;
import com.example.espermobilestore.api.BaseApiService;
import com.example.espermobilestore.model.FeatureResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    // retrieval of Features json
    Call<FeatureResponse> featureResponseCall;
    // Base api service
    BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseApiService apiService = new BaseApiClient().createService();

        featureResponseCall = apiService.getFeatures();

        featureResponseCall.enqueue(new Callback<FeatureResponse>() {
            @Override
            public void onResponse(@NotNull Call<FeatureResponse> call, @NotNull Response<FeatureResponse> response) {
                if (response.isSuccessful()) {

                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeatureResponse> call, @NotNull Throwable t) {
                Toast.makeText(MainActivity.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}