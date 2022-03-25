package com.example.fitnessapplication.FitnessApp.Classes;

public class GoalValuesModel {
    private String lossWeight;
    private double calory;

    public GoalValuesModel(String lossWeight, double calory) {
        this.lossWeight = lossWeight;
        this.calory = calory;
    }

    public String getLossWeight() {
        return lossWeight;
    }

    public void setLossWeight(String lossWeight) {
        this.lossWeight = lossWeight;
    }

    public double getCalory() {
        return calory;
    }

    public void setCalory(double calory) {
        this.calory = calory;
    }
}
