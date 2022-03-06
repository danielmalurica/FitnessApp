package com.example.fitnessapplication.FitnessApp.UsersActivities.BMI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BmiResultActivity extends AppCompatActivity {

    String bmiResultString;
    double bmiResult;
    TextView twBmiResult, twBmiNumber;
    ImageView imgResult;

    String userId;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_result);

        twBmiNumber =findViewById(R.id.resultBmi);
        twBmiResult = findViewById(R.id.bmiResult);
        imgResult = findViewById(R.id.imgViewResult);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        bmiResultString = getIntent().getStringExtra("bmiResult");
        twBmiNumber.setText(bmiResultString);
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
        } else if (bmiResult > 25 && bmiResult < 30) {
            twBmiResult.setText("Overweight");
            imgResult.setImageResource(R.drawable.warning);
        } else if (bmiResult > 30 && bmiResult < 35) {
            twBmiResult.setText("Obese Class I");
            imgResult.setImageResource(R.drawable.crosss);
        } else if (bmiResult > 35 && bmiResult < 40) {
            twBmiResult.setText("Obese Class II");
            imgResult.setImageResource(R.drawable.crosss);
        } else if (bmiResult > 40) {
            twBmiResult.setText("Obese Class III");
            imgResult.setImageResource(R.drawable.crosss);
        }

    }
}