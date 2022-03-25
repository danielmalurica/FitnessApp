package com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MacrosClass implements Serializable {
    private int protein;
    private int fat;
    private int carbs;

    public MacrosClass() {
    }

    public MacrosClass(int protein, int fat, int carbs) {
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
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
