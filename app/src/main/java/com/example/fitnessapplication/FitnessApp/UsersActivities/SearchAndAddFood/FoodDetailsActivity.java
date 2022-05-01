package com.example.fitnessapplication.FitnessApp.UsersActivities.SearchAndAddFood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.FoodModel;
import com.example.fitnessapplication.FitnessApp.Classes.FoodNutrients;
import com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq.MacrosClass;
import com.example.fitnessapplication.FitnessApp.UsersActivities.HomePage.UserHomepageActivity;
import com.example.fitnessapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FoodDetailsActivity extends AppCompatActivity {

    TextView tvFoodName;
    TextView tvFoodCategory;
    TextView tvFoodIngredients;
    TextView tvEnergy, tvEnergyUnit;
    TextView tvProtein, tvProteinUnit;
    TextView tvFat, tvFatUnit;
    TextView tvCarbs, tvCarbsUnit;
    List<FoodNutrients> foodNutrientsList;
    Button btnAddToDiary;
    MacrosClass macrosClass;

    private DatabaseReference myRef;
    private FirebaseUser user;

    long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        String period = getIntent().getStringExtra("PERIOD");
        macrosClass = new MacrosClass();

        tvFoodName = findViewById(R.id.foodNameTitle);
        tvFoodCategory = findViewById(R.id.foodCategory);
        tvEnergy = findViewById(R.id.energy);
        tvEnergyUnit = findViewById(R.id.energyUnit);
        tvProtein = findViewById(R.id.protein);
        tvProteinUnit = findViewById(R.id.proteinUnit);
        tvFoodIngredients = findViewById(R.id.foodIngredients);
        tvFat = findViewById(R.id.fat);
        tvFatUnit = findViewById(R.id.fatUnit);
        tvCarbs = findViewById(R.id.carbs);
        tvCarbsUnit = findViewById(R.id.carbsUnit);


        foodNutrientsList = new ArrayList<>();



        Bundle extras = getIntent().getExtras();

           FoodModel foodInfo = extras.getParcelable("foodDetails");
           tvFoodName.setText(foodInfo.getDescription());
           tvFoodCategory.setText(foodInfo.getFoodCategory());
           tvFoodIngredients.setText(foodInfo.getIngredients());
           foodNutrientsList = foodInfo.getFoodNutrients();
           FoodNutrients energy = findNutrient("Energy", foodNutrientsList);
           tvEnergy.setText(Double.toString(energy.getValue()));
           tvEnergyUnit.setText(energy.getNutrientUnit());
           FoodNutrients protein = findNutrient("Protein", foodNutrientsList);
           tvProtein.setText(Double.toString(protein.getValue()));
           macrosClass.setProtein(protein.getValue());
           tvProteinUnit.setText(protein.getNutrientUnit());
           FoodNutrients fat = findNutrient("Total lipid (fat)", foodNutrientsList);
           tvFat.setText(Double.toString(fat.getValue()));
           tvFatUnit.setText(fat.getNutrientUnit());
           macrosClass.setFat(fat.getValue());
           FoodNutrients carbs = findNutrient("Carbohydrate, by difference", foodNutrientsList);
           tvCarbs.setText(Double.toString(carbs.getValue()));
           tvCarbsUnit.setText(carbs.getNutrientUnit());
           macrosClass.setCarbs(carbs.getValue());

        myRef = FirebaseDatabase.getInstance().getReference("caloriesAndMacros");
        user = FirebaseAuth.getInstance().getCurrentUser();

        btnAddToDiary = findViewById(R.id.saveToDiary);
        btnAddToDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                myRef.child(user.getUid()).child("consumption").child(currentDate).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.getResult().exists()){
                            double respEnergy = (double) task.getResult().child("energy").getValue(Double.class);
                            double respCarbs = (double) task.getResult().child("carbs").getValue(Double.class);
                            double respFat = (double) task.getResult().child("fat").getValue(Double.class);
                            double respProtein= (double) task.getResult().child("protein").getValue(Double.class);
                            myRef.child(user.getUid()).child("consumption").child(currentDate).child("energy").setValue(respEnergy + energy.getValue());
                            myRef.child(user.getUid()).child("consumption").child(currentDate).child("carbs").setValue(respCarbs + carbs.getValue());
                            myRef.child(user.getUid()).child("consumption").child(currentDate).child("fat").setValue(respFat + fat.getValue());
                            myRef.child(user.getUid()).child("consumption").child(currentDate).child("protein").setValue(respProtein + protein.getValue());

                        } else {
                            myRef.child(user.getUid()).child("consumption").child(currentDate).setValue(macrosClass);
                            myRef.child(user.getUid()).child("consumption").child(currentDate).child("energy").setValue(energy.getValue());

                        }

                    }
                });

                myRef.child(user.getUid()).child("consumption").child(currentDate).child(period).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.getResult().exists()){
                            id = task.getResult().getChildrenCount();
                            Map<String, String> values = new HashMap<>();
                            values.put("foodName", tvFoodName.getText().toString());
                            values.put("kcal", tvEnergy.getText().toString());
                            values.put("protein", tvProtein.getText().toString());
                            values.put("fat", tvFat.getText().toString());
                            values.put("carbs", tvCarbs.getText().toString());
                            myRef.child(user.getUid()).child("consumption").child(currentDate).child(period).child(String.valueOf(id)).setValue(values);
                        } else {
                            Map<String, String> values = new HashMap<>();
                            values.put("foodName", tvFoodName.getText().toString());
                            values.put("kcal", tvEnergy.getText().toString());
                            values.put("protein", tvProtein.getText().toString());
                            values.put("fat", tvFat.getText().toString());
                            values.put("carbs", tvCarbs.getText().toString());
                            myRef.child(user.getUid()).child("consumption").child(currentDate).child(period).child(String.valueOf(id)).setValue(values);
                        }

                    }
                });

                Intent intent = new Intent(getApplicationContext(), UserHomepageActivity.class);
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