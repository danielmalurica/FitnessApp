package com.example.fitnessapplication.FitnessApp.UsersActivities.ConsumedCalories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapplication.FitnessApp.Classes.FoodModel;
import com.example.fitnessapplication.FitnessApp.UsersActivities.SearchAndAddFood.FoodRecyclerViewAdapter;
import com.example.fitnessapplication.R;

import java.util.List;

public class ActivitiesRecyclerViewAdapter extends RecyclerView.Adapter<ActivitiesRecyclerViewAdapter.ViewHolder>{

    private List<ActivityModel> activityList;
    private ActivitiesRecyclerViewAdapter.RecyclerViewClickListener recyclerViewClickListener;

    public ActivitiesRecyclerViewAdapter(List<ActivityModel> activityList, RecyclerViewClickListener recyclerViewClickListener) {
        this.activityList = activityList;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    public ActivitiesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_activities, parent, false);
        return new ActivitiesRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String id = activityList.get(position).getId();
        String activityTitle = activityList.get(position).getActivityTitle();
        float metValue = activityList.get(position).getMetValue();
        String description = activityList.get(position).getDescription();
        int intensityLevel = activityList.get(position).getIntensityLevel();
        holder.setData(activityTitle, description);
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }


    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.activityDescription);
            itemView.setOnClickListener(this);
        }

        public void setData(String activityTitle, String description) {
            tvDescription.setText(description);
        }

        @Override
        public void onClick(View v) {
            recyclerViewClickListener.onClick(v, getAdapterPosition());
        }
    }
}
