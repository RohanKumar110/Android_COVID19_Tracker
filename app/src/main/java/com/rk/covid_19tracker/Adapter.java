package com.rk.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<CountryModel> {

    Context context;
    List<CountryModel> modelList;
    List<CountryModel> modelListfiltered;

    public Adapter(Context context, List<CountryModel> modelList) {
        super(context, R.layout.custom_list,modelList);
        this.context = context;
        this.modelList = modelList;
        modelListfiltered = modelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_list,null,true);
        TextView txtCountry = view.findViewById(R.id.txtCountry);
        ImageView imageView = view.findViewById(R.id.imageview);

        txtCountry.setText(modelListfiltered.get(position).getCountry());
        Glide.with(context).load(modelListfiltered.get(position).getFlag()).into(imageView);

        return view;
    }

    @Override
    public int getCount() {
        return modelListfiltered.size();
    }

    @Nullable
    @Override
    public CountryModel getItem(int position) {
        return modelListfiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count =  modelList.size();
                    filterResults.values = modelList;
                }
                else
                {
                    List<CountryModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(CountryModel model:modelList)
                        if(model.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(model);
                        }
                    filterResults.count = resultsModel.size();
                    filterResults.values = resultsModel;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                modelListfiltered = (List<CountryModel>) results.values;
                AffectiveCountries.modelList = (List<CountryModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
