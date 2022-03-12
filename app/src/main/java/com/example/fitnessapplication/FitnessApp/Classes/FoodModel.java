package com.example.fitnessapplication.FitnessApp.Classes;


import java.util.List;

public class FoodModel {
    private String foodId;
    private String description;
    private String ingredients;
    private String foodCategory;
    private double servingSize;
    private String servingSizeUnit;
    private List<FoodNutrients> foodNutrients;

    public FoodModel(String foodId,String description, String ingredients, String foodCategory, double servingSize, String servingSizeUnit, List<FoodNutrients> foodNutrients) {
        this.foodId = foodId;
        this.description = description;
        this.ingredients = ingredients;
        this.foodCategory = foodCategory;
        this.servingSize = servingSize;
        this.servingSizeUnit = servingSizeUnit;
        this.foodNutrients = foodNutrients;
    }

    public FoodModel() {
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public double getServingSize() {
        return servingSize;
    }

    public void setServingSize(double servingSize) {
        this.servingSize = servingSize;
    }

    public String getServingSizeUnit() {
        return servingSizeUnit;
    }

    public void setServingSizeUnit(String servingSizeUnit) {
        this.servingSizeUnit = servingSizeUnit;
    }

    public List<FoodNutrients> getFoodNutrients() {
        return foodNutrients;
    }

    public void setFoodNutrients(List<FoodNutrients> foodNutrients) {
        this.foodNutrients = foodNutrients;
    }

    @Override
    public String toString() {
        return "FoodModel{" +
                "description='" + description + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", foodCategory='" + foodCategory + '\'' +
                ", servingSize=" + servingSize +
                ", servingSizeUnit='" + servingSizeUnit + '\'' +
                ", foodNutrients=" + foodNutrients +
                '}';
    }
}
