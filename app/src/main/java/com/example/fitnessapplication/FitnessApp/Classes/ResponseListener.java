package com.example.fitnessapplication.FitnessApp.Classes;

import com.example.fitnessapplication.FitnessApp.UsersActivities.ConsumedCalories.ActivityModel;

import java.util.ArrayList;

public interface ResponseListener {
    void onResponseReceived(ArrayList<ActivityModel> activityModels);
}
