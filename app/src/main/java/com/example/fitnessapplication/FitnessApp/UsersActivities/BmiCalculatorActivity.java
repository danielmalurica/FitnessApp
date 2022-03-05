package com.example.fitnessapplication.FitnessApp.UsersActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.DatabaseHelper;
import com.example.fitnessapplication.FitnessApp.Classes.User;
import com.example.fitnessapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BmiCalculatorActivity extends AppCompatActivity {
    private static final String TAG = BmiCalculatorActivity.class.getName();
    double bmi, userWeight, userHeight;
    int userAge;
    String userGender;
    String bmiString;
    TextView currentHeight, currentWeight, currentAge;
    ImageView imgIncrementWeight, imgDecrementWeight;
    ImageView imgIncrementAge, imgDecrementAge;
    RelativeLayout rltMale, rltFemale;
    SeekBar heightSeekbar;
    Button calculateBmi;

    String userId;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;
    User userData;

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


        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        documentReference = firebaseFirestore.collection("users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        userData = documentSnapshot.toObject(User.class);
                        userGender = userData.getGender();

                        if(userGender.equals("Male")){
                            rltMale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.maleandfemalefocus));
                            rltFemale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.maleandfemalenotfocus));
                        } else if(userGender.equals("Female")){
                            rltFemale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.maleandfemalefocus));
                            rltMale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.maleandfemalenotfocus));
                        }

                        currentHeight.setText(Double.toString(userData.getHeight()));
                        currentWeight.setText(Double.toString(userData.getWeight()));
                        currentAge.setText(Integer.toString(userData.getAge()));
                    } else {
                        Log.d(TAG, "No such document");
                        Toast.makeText(BmiCalculatorActivity.this, "No document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(BmiCalculatorActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });



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
                userWeight = Double.parseDouble(currentWeight.getText().toString());
                userWeight+= 1;
                currentWeight.setText(Double.toString(userWeight));
            }
        });

        imgDecrementWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userWeight = Double.parseDouble(currentWeight.getText().toString());
                userWeight -= 1;
                currentWeight.setText(Double.toString(userWeight));
            }
        });

        imgIncrementAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAge = Integer.parseInt(currentAge.getText().toString());
                userAge += 1;
                currentAge.setText(Integer.toString(userAge));
            }
        });

        imgDecrementAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAge = Integer.parseInt(currentAge.getText().toString());
                userAge -= 1;
                currentAge.setText(Integer.toString(userAge));
            }
        });

        calculateBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmiString = Double.toString(bmi);
                Toast.makeText(BmiCalculatorActivity.this, bmiString, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BmiResultActivity.class);
                intent.putExtra("bmiResult", bmiString);
                startActivity(intent);
            }
        });

        userHeight = Double.parseDouble(currentHeight.getText().toString());
        userWeight = Double.parseDouble(currentWeight.getText().toString());
        bmi = calculateBmi(userWeight, userHeight, bmi);

    }


    public double calculateBmi(double weight, double height, double bm) {
        double height2 = height / 100;
        bm = weight / (height2 * height2);
        return bm;
    }
}