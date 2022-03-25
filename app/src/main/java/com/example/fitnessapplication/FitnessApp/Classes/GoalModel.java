package com.example.fitnessapplication.FitnessApp.Classes;

import com.google.gson.annotations.SerializedName;

public class GoalModel {
    private String typeOfGoal;
    private GoalValuesModel values;

    public GoalModel(String typeOfGoal, GoalValuesModel values) {
        this.typeOfGoal = typeOfGoal;
        this.values = values;
    }

    public String getTypeOfGoal() {
        return typeOfGoal;
    }

    public void setTypeOfGoal(String typeOfGoal) {
        this.typeOfGoal = typeOfGoal;
    }

    public GoalValuesModel getValues() {
        return values;
    }

    public void setValues(GoalValuesModel values) {
        this.values = values;
    }
}
