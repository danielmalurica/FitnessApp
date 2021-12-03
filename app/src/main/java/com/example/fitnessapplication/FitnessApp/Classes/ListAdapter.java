package com.example.fitnessapplication.FitnessApp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fitnessapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<FoodModel> {
    public ListAdapter(Context context, List<FoodModel> list){
        super(context, R.layout.list_item, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FoodModel foodModel = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView mealName = convertView.findViewById(R.id.nameOfMeal);
        TextView mealCategory = convertView.findViewById(R.id.categoryOfMeal);
        TextView noOfCal = convertView.findViewById(R.id.numberOfCal);

        mealName.setText(foodModel.getDescription());
        mealCategory.setText(foodModel.getFoodCategory());
       // noOfCal.setText(foodModel.get);

        return super.getView(position, convertView, parent);
    }
}
