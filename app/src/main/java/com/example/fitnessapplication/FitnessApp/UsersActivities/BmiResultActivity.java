package com.example.fitnessapplication.FitnessApp.UsersActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessapplication.R;

public class BmiResultActivity extends AppCompatActivity {
    String bmiResultString;
    double bmiResult;
    TextView twBmiResult;
    ImageView imgResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_result);

        twBmiResult = findViewById(R.id.bmiResult);
        imgResult = findViewById(R.id.imgViewResult);

        bmiResultString = getIntent().getStringExtra("bmiResult");
        bmiResult = Double.parseDouble(bmiResultString);

        if (bmiResult < 16) {
            twBmiResult.setText("Severe");
            imgResult.setImageResource(R.drawable.crosss);
        } else if (bmiResult > 16 && bmiResult < 16.9) {
            twBmiResult.setText("Moderate Thinness");
            imgResult.setImageResource(R.drawable.warning);
        } else if (bmiResult > 17 && bmiResult < 18.4) {
            twBmiResult.setText("Mild Thinness");
            imgResult.setImageResource(R.drawable.warning);
        } else if (bmiResult > 18.5 && bmiResult < 25) {
            twBmiResult.setText("Normal");
            imgResult.setImageResource(R.drawable.ok);
        } else if (bmiResult > 25 && bmiResult < 29.4) {
            twBmiResult.setText("Overweight");
            imgResult.setImageResource(R.drawable.warning);
        }
    }
}