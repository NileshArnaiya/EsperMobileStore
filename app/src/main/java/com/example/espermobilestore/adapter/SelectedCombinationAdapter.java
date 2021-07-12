package com.example.espermobilestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.espermobilestore.R;
import com.example.espermobilestore.databinding.ResultsRowBinding;
import com.example.espermobilestore.model.FeatureResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectedCombinationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Map<String, String> mapList;
    private ArrayList<FeatureResponse> featuresList;
    private Context cnt;

    public SelectedCombinationAdapter(Context selectedCombination, Map<String, String> resultslist, ArrayList<FeatureResponse> featuresList) {
        this.mapList = resultslist;
        this.cnt = selectedCombination;
        this.featuresList = featuresList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        ResultsRowBinding resultsRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.results_row, parent, false);
        viewHolder = new ResultsVH(resultsRowBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        List featureidlist = new ArrayList<>();
        List optionidlist = new ArrayList<>();
        for (Map.Entry<String, String> pair : mapList.entrySet()) {
            featureidlist.add(pair.getValue());
            optionidlist.add(pair.getKey());
        }
        Integer resultantfeatureidposition = Integer.parseInt((String) featureidlist.get(position));
        int resultantoptionposition = Integer.parseInt((String) optionidlist.get(position));

        final ResultsVH resultsVH = (ResultsVH) holder;
        if (position != 0) resultsVH.resultsRowBinding.ResultsHeading.setVisibility(View.GONE);
        if (resultantfeatureidposition == 2) {
            resultsVH.resultsRowBinding.resultsId.setText(resultantfeatureidposition + "");
            resultsVH.resultsRowBinding.resultsName.setText(featuresList.get(0).getFeatures().get(resultantfeatureidposition - 1).getOptions().get(resultantoptionposition % 2).getName());
            resultsVH.resultsRowBinding.resultsoptionId.setText(featuresList.get(0).getFeatures().get(resultantfeatureidposition - 1).getOptions().get(resultantoptionposition % 2).getId());
            Picasso.get().load(featuresList.get(0).getFeatures().get(resultantfeatureidposition - 1).getOptions().get(resultantoptionposition % 2).getIcon()).into(resultsVH.resultsRowBinding.resultssicon);
        } else if (resultantfeatureidposition == 3) {
            resultsVH.resultsRowBinding.resultsId.setText(resultantfeatureidposition + "");
            resultsVH.resultsRowBinding.resultsName.setText(featuresList.get(0).getFeatures().get(resultantfeatureidposition - 1).getOptions().get(resultantoptionposition % 10).getName());
            resultsVH.resultsRowBinding.resultsoptionId.setText(featuresList.get(0).getFeatures().get(resultantfeatureidposition - 1).getOptions().get(resultantoptionposition % 10).getId());
            Picasso.get().load(featuresList.get(0).getFeatures().get(resultantfeatureidposition - 1).getOptions().get(resultantoptionposition % 10).getIcon()).into(resultsVH.resultsRowBinding.resultssicon);
        } else {
            resultsVH.resultsRowBinding.resultsId.setText(resultantfeatureidposition + "");
            resultsVH.resultsRowBinding.resultsName.setText(featuresList.get(0).getFeatures().get(resultantfeatureidposition - 1).getOptions().get(resultantoptionposition - 1).getName());
            resultsVH.resultsRowBinding.resultsoptionId.setText(featuresList.get(0).getFeatures().get(resultantfeatureidposition - 1).getOptions().get(resultantoptionposition - 1).getId());
            Picasso.get().load(featuresList.get(0).getFeatures().get(resultantfeatureidposition - 1).getOptions().get(resultantoptionposition - 1).getIcon()).into(resultsVH.resultsRowBinding.resultssicon);
        }
    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }

    public class ResultsVH extends RecyclerView.ViewHolder {
        ResultsRowBinding resultsRowBinding;
        private TextView storagename;
        private TextView storageid;
        private ImageView storageicon;
        private TextView optionid;

        public ResultsVH(@NonNull ResultsRowBinding itemView) {
            super(itemView.getRoot());
            resultsRowBinding = itemView;
        }

    }
}
