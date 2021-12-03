package com.example.fitnessapplication.FitnessApp.UsersActivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapplication.FitnessApp.Classes.FoodModel;
import com.example.fitnessapplication.R;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    List<FoodModel> foodModels;
    Context context;

    public RecycleViewAdapter(List<FoodModel> foodModels, Context context) {
        this.foodModels = foodModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
       ViewHolder viewHolder = new ViewHolder(view);
       return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mealName.setText(foodModels.get(position).getDescription());
        holder.category.setText(foodModels.get(position).getFoodCategory());
        holder.category.setText(foodModels.get(position).getFoodNutrients().get(position).getNutrientName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        TextView category;
        TextView nbOfCal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.nameOfMeal);
            category = itemView.findViewById(R.id.categoryOfMeal);
            nbOfCal = itemView.findViewById(R.id.numberOfCal);
        }
    }
}
