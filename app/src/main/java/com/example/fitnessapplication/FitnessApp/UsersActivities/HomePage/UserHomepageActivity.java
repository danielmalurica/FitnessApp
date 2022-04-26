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
    TextView tvCurrentCal, tvCurrentProtein, tvCurrentFat, tvCurrentCarbs;
    TextView tvTargetCalories, tvTargetProtein, tvTargetFat, tvTargetCarbs, tvBurnedCal, tvConsumedCal;
    CircularProgressIndicator cpiCal;
    LinearProgressIndicator lpiProtein;
    LinearProgressIndicator lpiFat;
    LinearProgressIndicator lpiCarbs;

    private DatabaseReference mDatabase;
    MacrosClass macrosClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        macrosClass = new MacrosClass(0, 0, 0);

        tvTargetCalories = findViewById(R.id.targetCalories);
        tvTargetProtein = findViewById(R.id.proteinTarget);
        tvTargetFat = findViewById(R.id.fatTarget);
        tvTargetCarbs = findViewById(R.id.carbsTarget);
        tvBurnedCal = findViewById(R.id.burnedCal);
        tvConsumedCal = findViewById(R.id.consumedCal);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("caloriesAndMacros").child(user.getUid()).child("goals").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(UserHomepageActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
                else {
                    tvTargetCalories.setText(task.getResult().child("calories").getValue().toString());
                    tvTargetProtein.setText(task.getResult().child("proteins").getValue().toString());
                    tvTargetFat.setText(task.getResult().child("fat").getValue().toString());
                    tvTargetCarbs.setText(task.getResult().child("carbs").getValue().toString());
                }
            }
        });

        tvCurrentCal = findViewById(R.id.currentCal);
        tvCurrentProtein = findViewById(R.id.currentProtein);
        tvCurrentFat = findViewById(R.id.currentFat);
        tvCurrentCarbs = findViewById(R.id.currentCarbs);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Calendar mCalendar = Calendar.getInstance();

        mDatabase.child("caloriesAndMacros").child(user.getUid()).child("activities").child(currentDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    final DecimalFormat df = new DecimalFormat("0");
                    tvBurnedCal.setText(snapshot.child("burnedCalories").getValue().toString());

                }
                else {
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("activities").child(currentDate).child("burnedCalories").setValue(0);
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("activities").child(currentDate).child("unit").setValue("calorie");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").child(currentDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    final DecimalFormat df = new DecimalFormat("0.0");
                    double consumedCal = Double.parseDouble(snapshot.child("energy").getValue().toString());
                    double burned = Double.parseDouble(tvBurnedCal.getText().toString());
                    double diferenceCal = consumedCal - burned;
                    if(diferenceCal >= 0){
                        tvCurrentCal.setText(String.valueOf(df.format(diferenceCal)));
                    }
                    tvConsumedCal.setText(snapshot.child("energy").getValue().toString());
                    tvCurrentProtein.setText(snapshot.child("protein").getValue().toString());
                    tvCurrentFat.setText(snapshot.child("fat").getValue().toString());
                    tvCurrentCarbs.setText(snapshot.child("carbs").getValue().toString());
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


        lpiProtein = findViewById(R.id.proteinIndicator);
        setDataToLinearProgressIndicator(tvCurrentProtein, tvTargetProtein, lpiProtein);
        lpiFat = findViewById(R.id.fatIndicator);
        setDataToLinearProgressIndicator(tvCurrentFat, tvTargetFat, lpiFat);
        lpiCarbs = findViewById(R.id.carbsIndicator);
        setDataToLinearProgressIndicator(tvCurrentCarbs, tvTargetCarbs, lpiCarbs);
        cpiCal = findViewById(R.id.circularProgressIndicator);
        setDataToCircularProgressIndicator(tvCurrentCal, tvTargetCalories, cpiCal);

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


    public void setDataToLinearProgressIndicator(TextView current, TextView target, LinearProgressIndicator linearProgressIndicator){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                double result = (Float.parseFloat(current.getText().toString())) / (Float.parseFloat(target.getText().toString()));
                result *= 100;
                linearProgressIndicator.setProgress((int) result);
            }
        }, 2000);
    }

    public void setDataToCircularProgressIndicator(TextView current, TextView target, CircularProgressIndicator circularProgressIndicator){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                double result = (Float.parseFloat(current.getText().toString())) / (Float.parseFloat(target.getText().toString()));
                result *= 100;
                circularProgressIndicator.setProgress((int) result);
            }
        }, 2000);
    }
}