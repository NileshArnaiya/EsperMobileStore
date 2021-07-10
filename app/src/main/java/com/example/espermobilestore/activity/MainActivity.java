package com.example.espermobilestore.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.espermobilestore.R;
import com.example.espermobilestore.Results;
import com.example.espermobilestore.adapter.FeaturesAdapter;
import com.example.espermobilestore.api.BaseApiClient;
import com.example.espermobilestore.api.BaseApiService;
import com.example.espermobilestore.common.Constants;
import com.example.espermobilestore.common.Utils;
import com.example.espermobilestore.model.Exclusion;
import com.example.espermobilestore.model.FeatureResponse;
import com.example.espermobilestore.model.Options;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    // retrieval of Features json
    Call<FeatureResponse> featureResponseCall;
    // Base api service
    BaseApiService apiService;
    RecyclerView recyclerView;
    ArrayList<FeatureResponse> featureResponses;
    FeaturesAdapter featuresAdapter;
    ActionMode actionMode;
    ActionCallback actionCallback;
    Boolean Invalid = false;
    Map<String, String> maplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // call api only when internet is present
        if (Utils.isNetworkAvailable(this)) {
            featureResponseCall.enqueue(new Callback<FeatureResponse>() {
                @Override
                public void onResponse(@NotNull Call<FeatureResponse> call, @NotNull Response<FeatureResponse> response) {
                    if (response.isSuccessful()) {
                        featureResponses = new ArrayList<>();
                        featureResponses.add(response.body());
                        actionCallback = new ActionCallback();
                        featuresAdapter = new FeaturesAdapter(MainActivity.this, featureResponses);
                        recyclerView.setAdapter(featuresAdapter);
                        Log.d(Constants.TAG, "Response: " + response.body().toString());
                        featuresAdapter.setItemClick(new FeaturesAdapter.OnItemClick() {
                            @Override
                            public void onItemClick(View view, Options features, String feature_id, int position) {
                                if (featuresAdapter.selectedItemCount() > 0) {
                                    toggleActionBar(feature_id, features, position);
                                }
                            }

                            @Override
                            public void onLongPress(View view, Options features, String feature_id, int position) {
                                toggleActionBar(feature_id, features, position);

                            }

                        });
                    } else {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<FeatureResponse> call, @NotNull Throwable t) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.unable_to_Fetch), Toast.LENGTH_SHORT).show();
                }
            });
        }
        // else load cached data
        else {
            loadData();
        }

    }

    private void initViews() {
        apiService = new BaseApiClient().createService();

        featureResponseCall = apiService.getFeatures();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
    }


    private void toggleActionBar(String feature_id, Options features, int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionCallback);
        }
        toggleSelection(feature_id, features, position);
    }

    private void toggleSelection(String feature_id, Options features, int position) {
        featuresAdapter.toggleSelection(feature_id, features, position);
        int count = featuresAdapter.selectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
        maplist = featuresAdapter.getSelectedItems();
        List<List<Exclusion>> exclusionlist = featureResponses.get(0).getExclusions();
        for (List<Exclusion> list : exclusionlist) {
            int resultcount = 0;
            for (Map.Entry m : maplist.entrySet()) {
                int i = 0;
                while (i < list.size()) {
                    if (m.getKey().equals(list.get(i).getOptions_id()) && m.getValue().equals(list.get(i).getFeature_id())) {
                        resultcount++;
                    }
                    i++;
                }
                if (resultcount >= list.size()) {
                    Invalid = true;
                    break;
                }
            }
        }
    }

    private void loadData() {

    }

    private void openDialog(Boolean invalid, final Map<String, String> maplist) {
        if (invalid == true) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    MainActivity.this);
            alertDialog.setTitle(R.string.Combination);
            alertDialog.setMessage(R.string.selected_not_available);
            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.select_another_combo), Toast.LENGTH_SHORT)
                                    .show();
                            Invalid = false;
                            dialog.cancel();
                        }
                    });
            alertDialog.show();
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    MainActivity.this);
            alertDialog.setTitle(R.string.Combination);
            alertDialog.setMessage(R.string.selected_combination);
            alertDialog.setPositiveButton(R.string.submit_btn,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            Intent resultsIntent = new Intent(MainActivity.this, SelectedCombination.class);
                            Results obj = Results.getInstance();
                            obj.resultsList = new HashMap<>();
                            obj.featuresList = new ArrayList<>();
                            obj.featuresList = featureResponses;
                            obj.resultsList = maplist;
                            startActivity(resultsIntent);
                            dialog.cancel();
                        }
                    });
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            Toast.makeText(getApplicationContext(),
                                    "", Toast.LENGTH_SHORT)
                                    .show();
                            dialog.cancel();
                        }
                    });
            alertDialog.show();
        }
    }

    private class ActionCallback implements ActionMode.Callback {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.applyItem:
                    openDialog(Invalid, maplist);
                    mode.finish();
                    return true;
            }
            return false;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            featuresAdapter.clearSelection();
            actionMode = null;
        }
    }
}