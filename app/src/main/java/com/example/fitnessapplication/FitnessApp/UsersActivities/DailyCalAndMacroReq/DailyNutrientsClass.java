package com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq;

import java.io.Serializable;

public class DailyNutrientsClass implements Serializable {
    private int age;
    private String gender;
    private double height;
    private double weight;
    private String activityLevel;
    private String goal;

    public DailyNutrientsClass(int age, String gender, double height, double weight, String activityLevel, String goal) {
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.goal = goal;
    }

    public DailyNutrientsClass() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
