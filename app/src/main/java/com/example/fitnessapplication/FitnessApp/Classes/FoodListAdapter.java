package com.example.fitnessapplication.FitnessApp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapplication.R;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    private Context context;
    private List<FoodModel> foodModelList;

    public FoodListAdapter(Context context, List<FoodModel> list) {
        this.context = context;
        this.foodModelList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodModel food = foodModelList.get(position);

        holder.mealName.setText(String.valueOf(food.getDescription()));
        holder.mealCategory.setText(String.valueOf(food.getFoodCategory()));
        holder.noOfCal.setText(String.valueOf(0));

    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealName, mealCategory, noOfCal;
        public ViewHolder(View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.nameOfMeal);
            mealCategory = itemView.findViewById(R.id.categoryOfMeal);
            noOfCal = itemView.findViewById(R.id.numberOfCal);
        }
    }

}
