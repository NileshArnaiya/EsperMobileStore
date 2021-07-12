package com.example.espermobilestore.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.espermobilestore.R;
import com.example.espermobilestore.Results;
import com.example.espermobilestore.adapter.SelectedCombinationAdapter;
import com.example.espermobilestore.databinding.ActivitySelectedCombinationBinding;
import com.example.espermobilestore.model.FeatureResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class SelectedCombination extends AppCompatActivity {
    Call<FeatureResponse> featuresListcall;
    ActivitySelectedCombinationBinding activitySelectedCombinationBinding;
    private Map<String, String> maplist;
    private ArrayList<FeatureResponse> featuresList;
    private ArrayList checkMobile;
    private SelectedCombinationAdapter selectedCombinationAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySelectedCombinationBinding = DataBindingUtil.setContentView(this, R.layout.activity_selected_combination);

        recyclerView = activitySelectedCombinationBinding.resultsrecyclerview;
        maplist = new HashMap<String, String>();
        featuresList = new ArrayList<>();
        checkMobile = new ArrayList();
        Results ob = Results.getInstance();
        featuresList = ob.featuresList;
        maplist = ob.resultsList;
        checkMobile.addAll(maplist.values());

        if (checkMobile.contains("1")) {
            selectedCombinationAdapter = new SelectedCombinationAdapter(SelectedCombination.this, maplist, featuresList);
            recyclerView.setLayoutManager(new LinearLayoutManager(SelectedCombination.this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(selectedCombinationAdapter);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    SelectedCombination.this);
            alertDialog.setTitle("SELECT");
            alertDialog.setMessage(R.string.select_one_mobile);
            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.select_one_mobile), Toast.LENGTH_SHORT)
                                    .show();
                            dialog.cancel();
                            finish();
                        }
                    });
            alertDialog.show();
        }

    }
}