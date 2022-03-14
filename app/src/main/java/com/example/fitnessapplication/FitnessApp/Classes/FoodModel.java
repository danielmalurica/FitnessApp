package com.example.fitnessapplication.FitnessApp.Classes;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FoodModel implements Parcelable {
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

    protected FoodModel(Parcel in) {
        foodId = in.readString();
        description = in.readString();
        ingredients = in.readString();
        foodCategory = in.readString();
        servingSize = in.readDouble();
        servingSizeUnit = in.readString();
        foodNutrients = new ArrayList<FoodNutrients>();
        in.readTypedList(foodNutrients, FoodNutrients.CREATOR);
    }

    public static final Creator<FoodModel> CREATOR = new Creator<FoodModel>() {
        @Override
        public FoodModel createFromParcel(Parcel in) {
            return new FoodModel(in);
        }

        @Override
        public FoodModel[] newArray(int size) {
            return new FoodModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(foodId);
        dest.writeString(description);
        dest.writeString(ingredients);
        dest.writeString(foodCategory);
        dest.writeDouble(servingSize);
        dest.writeString(servingSizeUnit);
        dest.writeTypedList(foodNutrients);
    }
}
