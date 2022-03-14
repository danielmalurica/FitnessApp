package com.example.fitnessapplication.FitnessApp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodNutrients implements Parcelable {
    private String nutrientName;
    private String nutrientUnit;
    private double value;

    public FoodNutrients(String nutrientName, String nutrientUnit, double value) {
        this.nutrientName = nutrientName;
        this.nutrientUnit = nutrientUnit;
        this.value = value;
    }

    public FoodNutrients() {
    }

    protected FoodNutrients(Parcel in) {
        nutrientName = in.readString();
        nutrientUnit = in.readString();
        value = in.readDouble();
    }

    public static final Creator<FoodNutrients> CREATOR = new Creator<FoodNutrients>() {
        @Override
        public FoodNutrients createFromParcel(Parcel in) {
            return new FoodNutrients(in);
        }

        @Override
        public FoodNutrients[] newArray(int size) {
            return new FoodNutrients[size];
        }
    };

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public String getNutrientUnit() {
        return nutrientUnit;
    }

    public void setNutrientUnit(String nutrientUnit) {
        this.nutrientUnit = nutrientUnit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FoodNutrients{" +
                "nutrientName='" + nutrientName + '\'' +
                ", nutrientUnit='" + nutrientUnit + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nutrientName);
        dest.writeString(nutrientUnit);
        dest.writeDouble(value);
    }
}
