package com.example.fitnessapplication.FitnessApp.UsersActivities.ConsumedCalories;

import java.util.Comparator;

public class ActivityModel {
    private String id;
    private String activityTitle;
    private float metValue;
    private String description;
    private int intensityLevel;

    public ActivityModel() {
    }

    public ActivityModel(String id, String activityTitle, float metValue, String description, int intensityLevel) {
        this.id = id;
        this.activityTitle = activityTitle;
        this.metValue = metValue;
        this.description = description;
        this.intensityLevel = intensityLevel;
    }

    public static Comparator<ActivityModel> ActivityAZ = new Comparator<ActivityModel>() {
        @Override
        public int compare(ActivityModel o1, ActivityModel o2) {
            return  o1.getDescription().compareTo(o2.getDescription());
        }
    };

    public static Comparator<ActivityModel> ActivityZA = new Comparator<ActivityModel>() {
        @Override
        public int compare(ActivityModel o1, ActivityModel o2) {
            return  o2.getDescription().compareTo(o1.getDescription());
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public float getMetValue() {
        return metValue;
    }

    public void setMetValue(float metValue) {
        this.metValue = metValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIntensityLevel() {
        return intensityLevel;
    }

    public void setIntensityLevel(int intensityLevel) {
        this.intensityLevel = intensityLevel;
    }
}
