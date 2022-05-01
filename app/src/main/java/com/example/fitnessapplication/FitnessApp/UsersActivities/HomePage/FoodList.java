package com.example.fitnessapplication.FitnessApp.UsersActivities.HomePage;

public class FoodList {
    private String foodName;
    private String cal;
    private double protein;
    private double fat;
    private double carbs;

    public FoodList() {
    }

    public FoodList(String foodName, String cal, double protein, double fat, double carbs) {
        this.foodName = foodName;
        this.cal = cal;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
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
}
