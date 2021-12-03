package com.example.fitnessapplication.FitnessApp.UsersActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.fitnessapplication.FitnessApp.Classes.DatabaseHelper;
import com.example.fitnessapplication.R;

public class BmiCalculatorActivity extends AppCompatActivity {
    int userId;
    double bmi, userWeight, userHeight;
    int userAge;
    String userGender;
    String bmiString;
    DatabaseHelper databaseHelper;
    TextView currentHeight, currentWeight, currentAge;
    ImageView imgIncrementWeight, imgDecrementWeight;
    ImageView imgIncrementAge, imgDecrementAge;
    RelativeLayout rltMale, rltFemale;
    SeekBar heightSeekbar;
    Button calculateBmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        currentWeight = findViewById(R.id.currentWeight);
        currentHeight = findViewById(R.id.currentHeight);
        currentAge = findViewById(R.id.currentAge);
        imgIncrementAge = findViewById(R.id.incrementAge);
        imgDecrementAge = findViewById(R.id.decrementAge);
        imgIncrementWeight = findViewById(R.id.incrementWeight);
        imgDecrementWeight = findViewById(R.id.decrementWeight);
        rltMale = findViewById(R.id.maleLayout);
        rltFemale = findViewById(R.id.femaleLayout);
        heightSeekbar = findViewById(R.id.seekbarForHeight);
        calculateBmi = findViewById(R.id.calculateBmi);

        databaseHelper = new DatabaseHelper(BmiCalculatorActivity.this);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);


        Cursor cursor = databaseHelper.findDataForBmi(userId);
        if(cursor.moveToFirst()) {
            do {
                userAge = cursor.getInt(3);
                userWeight = cursor.getDouble(4);
                userHeight = cursor.getDouble(5);
                userGender = cursor.getString(6);
            } while(cursor.moveToNext());
        }

        if(userGender.equals("Male")){
            rltMale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.maleandfemalefocus));
            rltFemale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.maleandfemalenotfocus));
        } else if(userGender.equals("Female")){
            rltFemale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.maleandfemalefocus));
            rltMale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.maleandfemalenotfocus));
        }

        rltMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rltMale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.maleandfemalefocus));
                rltFemale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.maleandfemalenotfocus));
            }
        });

        rltFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rltFemale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.maleandfemalefocus));
                rltMale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.maleandfemalenotfocus));
            }
        });

        bmi = calculateBmi(userWeight, userHeight, bmi);

        currentHeight.setText(Double.toString(userHeight));
        currentWeight.setText(Double.toString(userWeight));
        currentAge.setText(Integer.toString(userAge));

        heightSeekbar.setMax(250);
        heightSeekbar.setProgress((int)userHeight);
        heightSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                userHeight = progress;
                currentHeight.setText(Double.toString(userHeight));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        imgIncrementWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userWeight += 1;
                currentWeight.setText(Double.toString(userWeight));
            }
        });

        imgDecrementWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userWeight -= 1;
                currentWeight.setText(Double.toString(userWeight));
            }
        });

        imgIncrementAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAge += 1;
                currentAge.setText(Integer.toString(userAge));
            }
        });

        imgDecrementAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAge -= 1;
                currentAge.setText(Integer.toString(userAge));
            }
        });

        calculateBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.updateDataForBmi(userId, userAge, userWeight, userHeight, userGender);
                bmiString = Double.toString(bmi);
                Intent intent = new Intent(getApplicationContext(), BmiResultActivity.class);
                intent.putExtra("bmiResult", bmiString);
                startActivity(intent);
            }
        });

    }


    public double calculateBmi(double weight, double height, double bm) {
        double height2 = height / 100;
        bm = weight / (height2 * height2);
        return bm;
    }
}