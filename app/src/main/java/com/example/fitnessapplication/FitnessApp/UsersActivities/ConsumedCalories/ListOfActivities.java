package com.example.fitnessapplication.FitnessApp.UsersActivities.ConsumedCalories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.ResponseListener;
import com.example.fitnessapplication.FitnessApp.UsersActivities.SearchAndAddFood.FoodRecyclerViewAdapter;
import com.example.fitnessapplication.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListOfActivities extends AppCompatActivity {

    Spinner spActLevel;
    Button btnShowData;
    Button btnSortAZ, btnSortZA, btnShowAllActivities;
    EditText edtFilter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ActivitiesRecyclerViewAdapter activitiesRecyclerViewAdapter;
    ActivitiesRecyclerViewAdapter.RecyclerViewClickListener recyclerViewClickListener;

    List<ActivityModel> listOfAllActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_activities);

        listOfAllActivities = new ArrayList<>();

        spActLevel = findViewById(R.id.spinnerActLevel);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.intensityActivityLevel, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spActLevel.setAdapter(arrayAdapter);

        recyclerView = findViewById(R.id.activitiesList);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        btnSortAZ = findViewById(R.id.sortAZ);
        btnSortZA = findViewById(R.id.sortZA);
        btnShowAllActivities = findViewById(R.id.showAllActivities);

        btnShowAllActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityDataService activityDataService = new ActivityDataService(ListOfActivities.this);
                activityDataService.getAllListsOfActivities(new ActivityDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(List<ActivityModel> listOfActivities) {
                        listOfAllActivities = listOfActivities;
                        populateList(listOfAllActivities);
                    }
                });
            }
        });

        btnShowData = findViewById(R.id.showData);
        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityDataService activityDataService = new ActivityDataService(ListOfActivities.this);
                activityDataService.getListOfActivities(Integer.parseInt(spActLevel.getSelectedItem().toString()), new ActivityDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(ListOfActivities.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<ActivityModel> listOfActivities) {
                        populateList(listOfActivities);
                    }
                });
            }
        });

        edtFilter = findViewById(R.id.filter);
        edtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String text) {
        ArrayList<ActivityModel> filteredList = new ArrayList<>();
        for(ActivityModel item : listOfAllActivities) {
            if(item.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        recyclerViewClickListener = new ActivitiesRecyclerViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        };
        activitiesRecyclerViewAdapter = new ActivitiesRecyclerViewAdapter(filteredList, recyclerViewClickListener);
        recyclerView.setAdapter(activitiesRecyclerViewAdapter);
        activitiesRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void populateList(List<ActivityModel> listOfActivities){
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
        btnSortAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(listOfActivities, ActivityModel.ActivityAZ);
                activitiesRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        btnSortZA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(listOfActivities, ActivityModel.ActivityZA);
                activitiesRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        activitiesRecyclerViewAdapter.notifyDataSetChanged();
    }
}