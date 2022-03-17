package com.example.fitnessapplication.FitnessApp.UsersActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fitnessapplication.FitnessApp.UsersActivities.BMI.BmiUpdateDataActivity;
import com.example.fitnessapplication.FitnessApp.UsersActivities.BMI.BmiUserDataActivity;
import com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalReq.ListOfActivititiesActivity;
import com.example.fitnessapplication.R;

public class UserHomepageActivity extends AppCompatActivity {

    Button buttonToBmi, buttonJson, btnDailyCal;
    TextView textView;
    EditText edtFood;
    String foodName;

    String url = "https://jsonkeeper.com/b/63OH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        buttonToBmi = findViewById(R.id.btnToBmi);
        buttonToBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BmiUserDataActivity.class));
            }
        });

        buttonJson = findViewById(R.id.json);
        buttonJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchFoodActivity.class));
            }
        });

        btnDailyCal = findViewById(R.id.toDailyCal);
        btnDailyCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListOfActivititiesActivity.class));
            }
        });

    }
}