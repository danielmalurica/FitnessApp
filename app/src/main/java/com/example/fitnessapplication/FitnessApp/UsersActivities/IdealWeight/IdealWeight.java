package com.example.fitnessapplication.FitnessApp.UsersActivities.IdealWeight;

public class IdealWeight {
    private double minWeight;
    private double maxWeight;

    public IdealWeight(double minWeight, double maxWeight) {
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }



    public double getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(double minWeight) {
        this.minWeight = minWeight;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }
}
