package com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MacrosClass implements Serializable {
    private double protein;
    private double fat;
    private double carbs;

    public MacrosClass() {
    }

    public MacrosClass(double protein, double fat, double carbs) {
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    @Override
    public String toString() {
        return "MacrosClass{" +
                "protein=" + protein +
                ", fat=" + fat +
                ", carbs=" + carbs +
                '}';
    }

}
