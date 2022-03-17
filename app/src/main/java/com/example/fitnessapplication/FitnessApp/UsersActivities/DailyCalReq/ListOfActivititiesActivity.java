package com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalReq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.example.fitnessapplication.FitnessApp.Classes.CaloriesDataService;
import com.example.fitnessapplication.FitnessApp.Classes.CaloriesRequirements;
import com.example.fitnessapplication.FitnessApp.Classes.User;
import com.example.fitnessapplication.FitnessApp.UsersActivities.BMI.BmiUpdateDataActivity;
import com.example.fitnessapplication.FitnessApp.UsersActivities.BMI.BmiUserDataActivity;
import com.example.fitnessapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;

public class ListOfActivititiesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = ListOfActivititiesActivity.class.getName();
    TextView tvBmrName, tvBmrAge, tvBmrHeight, tvBmrWeight;
    Spinner spinnerActivities;
    String selectedActivityLevel;
    Button btnBmrCalculateCalories, btnBmrUpdateData;

    String userId;
    User userData;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_activitities);

        tvBmrName = findViewById(R.id.bmrName);
        tvBmrAge = findViewById(R.id.bmrAge);
        tvBmrHeight = findViewById(R.id.bmrHeight);
        tvBmrWeight = findViewById(R.id.bmrWeight);

        spinnerActivities = findViewById(R.id.bmrActivities);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.activities, android.R.layout.simple_spinner_dropdown_item);
        spinnerActivities.setAdapter(adapter);
        spinnerActivities.setOnItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userData = new User();
        userId = mAuth.getCurrentUser().getUid();

        documentReference = firebaseFirestore.collection("users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        userData = documentSnapshot.toObject(User.class);
                        tvBmrName.setText(userData.getName());
                        tvBmrAge.setText(Integer.toString(userData.getAge()));
                        tvBmrHeight.setText(Double.toString(userData.getHeight()));
                        tvBmrWeight.setText(Double.toString(userData.getWeight()));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        btnBmrCalculateCalories = findViewById(R.id.calculateDalyCalories);
        CaloriesDataService caloriesDataService = new CaloriesDataService(ListOfActivititiesActivity.this);
        btnBmrCalculateCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectGoalActivity.class);
                intent.putExtra("USER_AGE", userData.getAge());
                intent.putExtra("USER_GENDER", userData.getGender());
                intent.putExtra("USER_HEIGHT", userData.getHeight());
                intent.putExtra("USER_WEIGHT", userData.getWeight());
                intent.putExtra("ACTIVITY_LEVEL", selectedActivityLevel);
                startActivity(intent);
            }
        });

        btnBmrUpdateData =findViewById(R.id.bmrupdateData);
        btnBmrUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BmiUpdateDataActivity.class));
            }
        });

       /* KAlertDialog resultDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        resultDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        resultDialog.setTitleText("Loading");
        resultDialog.setCancelable(false);
        resultDialog.show();*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = parent.getItemAtPosition(position).toString();
        if(selection.equals("Sedentary: little or no exercise")){
            selectedActivityLevel = "level_1";
        } else if(selection.equals("Exercise 1-3 times/week")){
            selectedActivityLevel = "level_2";
        } else if(selection.equals("Exercise 4-5 times/week")) {
            selectedActivityLevel = "level_3";
        } else if(selection.equals("Daily exercise or intense exercise 3-4 times/week")) {
            selectedActivityLevel = "level_4";
        } else if(selection.equals("Intense exercise 6-7 times/week")) {
            selectedActivityLevel = "level_5";
        } else if(selection.equals("Very intense exercise daily, or physical job")) {
            selectedActivityLevel = "level_6";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}