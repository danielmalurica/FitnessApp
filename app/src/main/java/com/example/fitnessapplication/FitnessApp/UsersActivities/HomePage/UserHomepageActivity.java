package com.example.fitnessapplication.FitnessApp.UsersActivities.HomePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.UsersActivities.BMI.BmiUserDataActivity;
import com.example.fitnessapplication.FitnessApp.UsersActivities.Charts.EvolutionByMonth;
import com.example.fitnessapplication.FitnessApp.UsersActivities.ConsumedCalories.ListOfActivities;
import com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq.ListOfActivititiesActivity;
import com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq.MacrosClass;
import com.example.fitnessapplication.FitnessApp.UsersActivities.SearchAndAddFood.SearchFoodActivity;
import com.example.fitnessapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserHomepageActivity extends AppCompatActivity {

    Button buttonToBmi, buttonJson, btnDailyCal, btnToActivities, btnToChart;
    Button btnAddFoodToBreakfast;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").child(currentDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                }
                else {
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").child(currentDate).child("energy").setValue(0);
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").child(currentDate).child("carbs").setValue(0);
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").child(currentDate).child("fat").setValue(0);
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").child(currentDate).child("protein").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnAddFoodToBreakfast = findViewById(R.id.addFoodBreakfast);
        btnAddFoodToBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepageActivity.this, SearchFoodActivity.class);
                intent.putExtra("PERIOD_OF_DAY", "breakfast");
                startActivity(intent);
                finish();
            }
        });


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

        btnToActivities = findViewById(R.id.btnToActivities);
        btnToActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListOfActivities.class));
            }
        });

        btnToChart = findViewById(R.id.btnToChart);
        btnToChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EvolutionByMonth.class);
                startActivity(intent);
            }
        });

    }


}