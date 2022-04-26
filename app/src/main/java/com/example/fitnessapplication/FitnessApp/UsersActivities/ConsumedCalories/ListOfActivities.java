package com.example.fitnessapplication.FitnessApp.UsersActivities.ConsumedCalories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.UsersActivities.SearchAndAddFood.FoodRecyclerViewAdapter;
import com.example.fitnessapplication.R;

import org.json.JSONObject;

import java.util.List;

public class ListOfActivities extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ActivitiesRecyclerViewAdapter activitiesRecyclerViewAdapter;
    ActivitiesRecyclerViewAdapter.RecyclerViewClickListener recyclerViewClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_activities);

        recyclerView = findViewById(R.id.activitiesList);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        ActivityDataService activityDataService = new ActivityDataService(ListOfActivities.this);
        activityDataService.getListOfActivities(1, new ActivityDataService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(ListOfActivities.this, "Something wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<ActivityModel> listOfActivities) {
                recyclerViewClickListener = new ActivitiesRecyclerViewAdapter.RecyclerViewClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Intent intent = new Intent(getApplicationContext(), ActivityDetails.class);
                        intent.putExtra("ACTIVITY_ID", listOfActivities.get(position).getId());
                        intent.putExtra("ACTIVITY_DESCRIPTION", listOfActivities.get(position).getDescription());
                        startActivity(intent);
                    }
                };
                activitiesRecyclerViewAdapter = new ActivitiesRecyclerViewAdapter(listOfActivities, recyclerViewClickListener);
                recyclerView.setAdapter(activitiesRecyclerViewAdapter);
                activitiesRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }
}