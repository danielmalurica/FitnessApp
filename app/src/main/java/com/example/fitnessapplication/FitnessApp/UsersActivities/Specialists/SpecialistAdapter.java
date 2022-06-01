package com.example.fitnessapplication.FitnessApp.UsersActivities.Specialists;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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

public class SpecialistAdapter extends RecyclerView.Adapter<SpecialistAdapter.ViewHolder>{

    private List<Specialist> specialistList;
    private SpecialistAdapter.RecyclerViewClickListener recyclerViewClickListener;

    public SpecialistAdapter(List<Specialist> specialistList, SpecialistAdapter.RecyclerViewClickListener recyclerViewClickListener) {
        this.specialistList = specialistList;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image = specialistList.get(position).getImage();
        String name = specialistList.get(position).getName();
        String typeOfSpecialist = specialistList.get(position).getTypeOfSpecialist();
        String description = specialistList.get(position).getDescription();
        holder.setData(name, typeOfSpecialist, description);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvName;
        private TextView tvTypeOfSpecialist;
        private TextView tvSpecialistDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.specialistName);
            tvTypeOfSpecialist = itemView.findViewById(R.id.typeOfSpecialist);
            tvSpecialistDescription = itemView.findViewById(R.id.specialistDescription);
            itemView.setOnClickListener(this);
        }

        public void setData(String name, String typeOfSpecialist, String specialistDescription) {
            tvName.setText(name);
            tvTypeOfSpecialist.setText(typeOfSpecialist);
            tvSpecialistDescription.setText(specialistDescription);
        }

        @Override
        public void onClick(View v) {
            recyclerViewClickListener.onClick(v, getAdapterPosition());
        }
    }
}
