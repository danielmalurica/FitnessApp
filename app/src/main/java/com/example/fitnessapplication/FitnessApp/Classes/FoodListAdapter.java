
package com.example.fitnessapplication.FitnessApp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapplication.R;

import java.util.List;

public class FoodListAdapter{

    private Context context;
    private List<FoodModel> foodModelList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public FoodListAdapter(Context context, List<FoodModel> list, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.foodModelList = list;
        this.onItemClickListener = onItemClickListener;
    }

   /* @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodModel food = foodModelList.get(position);

        holder.mealName.setText(String.valueOf(food.getDescription()));
        holder.mealCategory.setText(String.valueOf(food.getFoodCategory()));
        holder.noOfCal.setText(String.valueOf(food.getFoodNutrients().get(position)));

    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mealName, mealCategory, noOfCal;
        OnItemClickListener onItemClickListener;

        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            mealName = itemView.findViewById(R.id.nameOfMeal);
            mealCategory = itemView.findViewById(R.id.categoryOfMeal);
            noOfCal = itemView.findViewById(R.id.numberOfCal);
            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

}

