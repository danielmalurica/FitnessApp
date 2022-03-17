package com.example.fitnessapplication.FitnessApp.Classes;

public class CaloriesRequirements {
    private String lossWeight;
    private int calory;

    public CaloriesRequirements() {
    }

    public CaloriesRequirements(String lossWeight, int calory) {
        this.lossWeight = lossWeight;
        this.calory = calory;
    }

    public String getLossWeight() {
        return lossWeight;
    }

    public void setLossWeight(String lossWeight) {
        this.lossWeight = lossWeight;
    }

    public int getCalory() {
        return calory;
    }

    public void setCalory(int calory) {
        this.calory = calory;
    }
}
