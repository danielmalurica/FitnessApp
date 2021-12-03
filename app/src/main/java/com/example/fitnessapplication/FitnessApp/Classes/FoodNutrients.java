package com.example.fitnessapplication.FitnessApp.Classes;

public class FoodNutrients {
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
}
