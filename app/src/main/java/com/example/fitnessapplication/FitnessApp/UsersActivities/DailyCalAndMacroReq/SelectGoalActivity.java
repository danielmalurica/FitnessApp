package com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.CaloriesAndMacrosDataService;
import com.example.fitnessapplication.FitnessApp.Classes.GoalValuesModel;
import com.example.fitnessapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class SelectGoalActivity extends AppCompatActivity {

    RadioGroup group;
    RadioButton radioButton;
    DailyNutrientsClass dailyNutrientsClass;
    Button btnBmr;
    String selectedGoal = "";

    double calories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_goal);

        btnBmr = findViewById(R.id.bmr);
        group = findViewById(R.id.radioGroup);

        Intent intent = getIntent();

        dailyNutrientsClass = (DailyNutrientsClass) intent.getSerializableExtra("USER_DAILY_NUTRIENTS2");


        CaloriesAndMacrosDataService caloriesAndMacrosDataService = new CaloriesAndMacrosDataService(SelectGoalActivity.this);

        caloriesAndMacrosDataService.getDataForGoal(dailyNutrientsClass.getAge(), dailyNutrientsClass.getGender().toLowerCase(Locale.ROOT), dailyNutrientsClass.getHeight(), dailyNutrientsClass.getWeight(), dailyNutrientsClass.getActivityLevel(), new CaloriesAndMacrosDataService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(SelectGoalActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject object) {
                try {
                    String bmr = object.getString("BMR");
                    JSONObject goals = object.getJSONObject("goals");
                    String key = "";
                    Iterator iterator = goals.keys();
                    while (iterator.hasNext()) {
                        for (int i = 0; i < goals.length(); i++) {
                            RadioButton radioButton = new RadioButton(SelectGoalActivity.this);
                            key = (String) iterator.next();
                            radioButton.setText(key + "\n");
                            radioButton.setTextSize(20);
                            radioButton.setGravity(Gravity.CENTER_HORIZONTAL);
                            group.addView(radioButton);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
                switch (group.indexOfChild(radioButton)) {
                    case 0:
                        selectedGoal = "maintain";
                        break;
                    case 1:
                        selectedGoal = "mildlose";
                        break;
                    case 2:
                        selectedGoal = "weightlose";
                        break;
                    case 3:
                        selectedGoal = "extremelose";
                        break;
                    case 4:
                        selectedGoal = "mildgain";
                        break;
                    case 5:
                        selectedGoal = "weightgain";
                        break;
                    case 6:
                        selectedGoal = "extremegain";
                        break;
                }
            }
        });

        btnBmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedGoal.equals("")) {
                    Toast.makeText(SelectGoalActivity.this, "You need to select 1 goal", Toast.LENGTH_SHORT).show();
                } else {
                    caloriesAndMacrosDataService.calculateMacros(dailyNutrientsClass.getAge(), dailyNutrientsClass.getGender().toLowerCase(Locale.ROOT), dailyNutrientsClass.getHeight(), dailyNutrientsClass.getWeight(), (Character.getNumericValue((dailyNutrientsClass.getActivityLevel()).charAt(dailyNutrientsClass.getActivityLevel().length() -1))) +1 , selectedGoal, new CaloriesAndMacrosDataService.VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(SelectGoalActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(JSONObject object) {
                            try {
                                calories = object.getDouble("calorie");
                                JSONObject balanced = object.getJSONObject("balanced");
                                MacrosClass balancedMacros = new MacrosClass(balanced.getInt("protein"), balanced.getInt("fat"), balanced.getInt("carbs"));
                                JSONObject lowFat = object.getJSONObject("lowfat");
                                MacrosClass lowFatMacros = new MacrosClass(lowFat.getInt("protein"), lowFat.getInt("fat"), lowFat.getInt("carbs"));
                                JSONObject lowCarbs = object.getJSONObject("lowcarbs");
                                MacrosClass lowCarbsMacros = new MacrosClass(lowCarbs.getInt("protein"), lowCarbs.getInt("fat"), lowCarbs.getInt("carbs"));
                                JSONObject highProtein = object.getJSONObject("highprotein");
                                MacrosClass highProteinMacros = new MacrosClass(highProtein.getInt("protein"), highProtein.getInt("fat"), highProtein.getInt("carbs"));
                                Intent intent1 = new Intent(SelectGoalActivity.this, ResultActivity.class);
                                intent1.putExtra("CALORIES_RESULT", calories);
                               intent1.putExtra("BALANCED_MACROS", balancedMacros);
                                intent1.putExtra("LOWFAT_MACROS", lowFatMacros);
                                intent1.putExtra("LOWCARBS_MACROS", lowCarbsMacros);
                                intent1.putExtra("HIGHPROTEIN_MACROS", highProteinMacros);
                                startActivity(intent1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }


            }
        });
    }
}