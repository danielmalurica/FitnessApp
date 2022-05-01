package com.example.fitnessapplication.FitnessApp.UsersActivities.HomePage;

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

public class FoodListAdapter extends ArrayAdapter<FoodList> {
    private ArrayList<FoodList> foodLists;
    private Context mContext;

    public FoodListAdapter(@NonNull Context context, ArrayList<FoodList> foodLists) {
        super(context, 0, foodLists);
        this.foodLists = foodLists;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_food, parent, false);
        }
        TextView title = convertView.findViewById(R.id.foodNameAdapter);
        TextView kcal = convertView.findViewById(R.id.foodCategoryAdapter);
        title.setText(foodLists.get(position).getFoodName());
        kcal.setText(String.valueOf(foodLists.get(position).getCal()));
        return convertView;
    }
}
