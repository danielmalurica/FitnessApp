package com.example.fitnessapplication.FitnessApp.UsersActivities.SearchAndAddFood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapplication.FitnessApp.Classes.FoodModel;
import com.example.fitnessapplication.R;

import java.util.List;

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.ViewHolder> {

    private List<FoodModel> foodList;
    private RecyclerViewClickListener recyclerViewClickListener;

    public FoodRecyclerViewAdapter(List<FoodModel> foodList, RecyclerViewClickListener listener) {
        this.foodList = foodList;
        this.recyclerViewClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String description = foodList.get(position).getDescription();
        String category = foodList.get(position).getFoodCategory();
        double nbOfCal=0;
        for(int i=0; i<foodList.get(position).getFoodNutrients().size(); i++) {
            if (foodList.get(position).getFoodNutrients().get(i).getNutrientName().equals("Protein")) {
                nbOfCal = foodList.get(position).getFoodNutrients().get(i).getValue();

            }
        }
        holder.setData(description,category, nbOfCal);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvDescription;
        private TextView tvCategory;
        private TextView tvNbCal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.nameOfMeal);
            tvCategory = itemView.findViewById(R.id.categoryOfMeal);
            tvNbCal = itemView.findViewById(R.id.numberOfCal);
            itemView.setOnClickListener(this);
        }

        public void setData(String description, String category, double nbOfCal) {
            tvDescription.setText(description);
            tvCategory.setText(category);
            tvNbCal.setText(Double.toString(nbOfCal));
        }

        @Override
        public void onClick(View v) {
            recyclerViewClickListener.onClick(v, getAdapterPosition());
        }
    }
}
