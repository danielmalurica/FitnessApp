package com.example.fitnessapplication.FitnessApp.UsersActivities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.FoodModel;
import com.example.fitnessapplication.FitnessApp.Classes.FoodNutrients;
import com.example.fitnessapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailsActivity extends AppCompatActivity {

    TextView tvFoodName;
    TextView tvFoodCategory;
    TextView tvFoodIngredients;
    TextView tvProtein, tvProteinUnit;
    List<FoodNutrients> foodNutrientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        tvFoodName = findViewById(R.id.foodNameTitle);
        tvFoodCategory = findViewById(R.id.foodCategory);
        tvProtein = findViewById(R.id.protein);
        tvProteinUnit = findViewById(R.id.proteinUnit);
        tvFoodIngredients = findViewById(R.id.foodIngredients);

        foodNutrientsList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if(extras!= null){
           FoodModel foodInfo = extras.getParcelable("foodDetails");
           tvFoodName.setText(foodInfo.getDescription());
           tvFoodCategory.setText(foodInfo.getFoodCategory());
           tvFoodIngredients.setText(foodInfo.getIngredients());
           foodNutrientsList = foodInfo.getFoodNutrients();
           FoodNutrients protein = findNutrient("Protein", foodNutrientsList);
           tvProtein.setText(Double.toString(protein.getValue()));
           tvProteinUnit.setText(protein.getNutrientUnit());
        }

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