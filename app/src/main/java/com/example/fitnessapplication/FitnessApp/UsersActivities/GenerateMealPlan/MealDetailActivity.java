package com.example.fitnessapplication.FitnessApp.UsersActivities.GenerateMealPlan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.AddToConsumption;
import com.example.fitnessapplication.FitnessApp.Classes.FoodNutrients;
import com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq.MacrosClass;
import com.example.fitnessapplication.FitnessApp.UsersActivities.HomePage.UserHomepageActivity;
import com.example.fitnessapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MealDetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tvTitle;
    TextView tvServings, tvReadyInMinutes, tvCalories, tvProtein, tvFat, tvCarbs, tvVegetarianGeneratedMeal, tvVeganGeneratedMeal, tvGlutenFree, tvLactoseFree;
    TextView tvProteinUnit, tvFatUnit, tvCarbsUnit;
    TextView tvInstructions;
    TextView tvMoreInfo;
    Button btnAddToConsumed;
    LinearLayout llIngredientLayout;
    List<FoodNutrients> foodNutrientsList;
    List<Ingredient> ingredientList;

    private DatabaseReference myRef;
    private FirebaseUser user;
    MacrosClass macrosClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        imageView = findViewById(R.id.foodImage);
        tvTitle = findViewById(R.id.mealTitle);
        tvServings = findViewById(R.id.servings);
        tvReadyInMinutes = findViewById(R.id.readyInMinutes);
        tvCalories = findViewById(R.id.calories);
        tvProtein = findViewById(R.id.proteinGeneratedMeal);
        tvProteinUnit = findViewById(R.id.proteinUnit);
        tvFat = findViewById(R.id.fatGeneratedMeal);
        tvFatUnit = findViewById(R.id.fatUnit);
        tvCarbs = findViewById(R.id.carbsGeneratedMeal);
        tvCarbsUnit = findViewById(R.id.carbsUnit);
        tvVegetarianGeneratedMeal = findViewById(R.id.vegetarianGeneratedMeal);
        tvVeganGeneratedMeal = findViewById(R.id.vegeanGeneratedMeal);
        tvGlutenFree = findViewById(R.id.glutenFreeGeneratedMeal);
        tvLactoseFree = findViewById(R.id.lactoseFreeGeneratedMeal);
        tvInstructions = findViewById(R.id.instuctions);
        tvMoreInfo = findViewById(R.id.moreInfo);

        foodNutrientsList = new ArrayList<>();
        ingredientList = new ArrayList<>();
        llIngredientLayout = findViewById(R.id.ingredientLayout);

        myRef = FirebaseDatabase.getInstance().getReference("caloriesAndMacros");
        user = FirebaseAuth.getInstance().getCurrentUser();
        macrosClass = new MacrosClass();

        int id = this.getIntent().getExtras().getInt("MEAL_ID");
        String period = this.getIntent().getExtras().getString("PERIOD");
        MealPlanService mealPlanService = new MealPlanService(MealDetailActivity.this);
        mealPlanService.getDataForMeal(id, new MealPlanService.VolleyResponseListenerForMeal() {
            @Override
            public void onError(String message) {
                Toast.makeText(MealDetailActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Meal meal) {
                Picasso.get().load(meal.getImageUrl()).into(imageView);
                tvTitle.setText(meal.getTitle());
                tvServings.setText(String.valueOf(meal.getServings()));
                tvReadyInMinutes.setText((String.valueOf(meal.getReadyInMinutes())));
                foodNutrientsList = meal.getNutrients();
                FoodNutrients cal = findNutrient("Calories", foodNutrientsList);
                tvCalories.setText(String.valueOf(cal.getValue()));
                FoodNutrients protein = findNutrient("Protein", foodNutrientsList);
                tvProtein.setText(String.valueOf(protein.getValue()));
                tvProteinUnit.setText(protein.getNutrientUnit());
                macrosClass.setProtein(protein.getValue());
                FoodNutrients fat = findNutrient("Fat", foodNutrientsList);
                tvFat.setText(String.valueOf(fat.getValue()));
                tvFatUnit.setText(fat.getNutrientUnit());
                macrosClass.setFat(fat.getValue());
                FoodNutrients carbs = findNutrient("Carbohydrates", foodNutrientsList);
                tvCarbs.setText(String.valueOf(carbs.getValue()));
                tvCarbsUnit.setText(carbs.getNutrientUnit());
                macrosClass.setCarbs(carbs.getValue());
                tvVegetarianGeneratedMeal.setText(String.valueOf(meal.isVegetarian()));
                tvVeganGeneratedMeal.setText(String.valueOf(meal.isVegan()));
                tvGlutenFree.setText(String.valueOf(meal.isGlutenFree()));
                tvLactoseFree.setText(String.valueOf(meal.isDiaryFree()));
                ingredientList = meal.getIngredients();
                for(int i=0; i<ingredientList.size(); i++){
                    TextView textView = new TextView(MealDetailActivity.this);
                    textView.setTextColor(Color.BLACK);
                    textView.setText("-" + ingredientList.get(i).getUnit() + "\n");
                    llIngredientLayout.addView(textView);
                }
                tvInstructions.setText(meal.getInstructions());
                tvMoreInfo.setText(meal.getSourceUrl());
                tvMoreInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(meal.getSourceUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });

            }
        });

        btnAddToConsumed = findViewById(R.id.addGeneratedMealToConsumed);
        btnAddToConsumed.setText("Add to " + period);
        AddToConsumption addToConsumption = new AddToConsumption();
        btnAddToConsumed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double energy = Double.parseDouble(tvCalories.getText().toString());
                double carbs = Double.parseDouble(tvCarbs.getText().toString());
                double fat = Double.parseDouble(tvFat.getText().toString());
                double protein = Double.parseDouble(tvProtein.getText().toString());
                addToConsumption.addDataToConsumption(myRef, user, energy, carbs, fat, protein, period, macrosClass, tvTitle, tvCalories, tvProtein, tvFat, tvCarbs);
                Intent intent = new Intent(MealDetailActivity.this, UserHomepageActivity.class);
                startActivity(intent);
            }
        });
    }

    public FoodNutrients findNutrient(String nutrientName, List<FoodNutrients> nutrients){
        for(FoodNutrients nutrient : nutrients) {
            if(nutrient.getNutrientName().equals(nutrientName)){
                return nutrient;
            }
        }
        return null;
    }
}