package com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fitnessapplication.R;

public class ActivityLevel extends AppCompatActivity {

    Button btnToGoal;
    RadioGroup rgActivityLevel;
    RadioButton radioButton;
    String selectedActivityLevel = "level_1";
    DailyNutrientsClass dailyNutrientsClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);


        Intent intent = getIntent();
        dailyNutrientsClass = (DailyNutrientsClass) intent.getSerializableExtra("USER_DAILY_NUTRIENTS");

        rgActivityLevel = findViewById(R.id.activityLevel);
        rgActivityLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.sedentaryLevel:
                    selectedActivityLevel = "level_1";
                    break;

                   case R.id.oneThreeTimes:
                       selectedActivityLevel = "level_2";
                       break;

                   case R.id.fourFiveTimes:
                       selectedActivityLevel = "level_3";
                       break;

                   case R.id.daily:
                       selectedActivityLevel = "level_4";
                       break;

                   case R.id.intense:
                       selectedActivityLevel = "level_5";
                       break;

                   case R.id.veryIntense:
                       selectedActivityLevel = "level_6";
                       break;
               }
            }
        });
        btnToGoal = findViewById(R.id.goToGoal);

        btnToGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rgActivityLevel.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);

                dailyNutrientsClass.setActivityLevel(selectedActivityLevel);

                Intent intent = new Intent(getApplicationContext(), SelectGoalActivity.class);
                intent.putExtra("USER_DAILY_NUTRIENTS2", dailyNutrientsClass);
                startActivity(intent);
            }
        });

    }
}