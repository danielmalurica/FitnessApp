package com.example.fitnessapplication.FitnessApp.UsersActivities.ConsumedCalories;

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
