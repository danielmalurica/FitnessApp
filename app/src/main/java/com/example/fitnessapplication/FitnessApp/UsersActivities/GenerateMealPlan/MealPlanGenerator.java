package com.example.fitnessapplication.FitnessApp.UsersActivities.GenerateMealPlan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MealPlanGenerator extends AppCompatActivity {
    Spinner spinnerGeneratedFreq;
    EditText edtTargetCal, edtDiet, edtExclusion;
    Button btnGenerateMeal;

    private DatabaseReference mDatabase;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan_generator);

        spinnerGeneratedFreq = findViewById(R.id.spinnerPriod);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typeOfGeneratedMeal, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGeneratedFreq.setAdapter(adapter);

        spinnerGeneratedFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK);
                ((TextView) view).setTextSize(25);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtTargetCal = findViewById(R.id.edtCaloriesTarget);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase.child("caloriesAndMacros").child(firebaseUser.getUid()).child("goals").child("calories").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    edtTargetCal.setText(task.getResult().getValue().toString());
                }
            }
        });

        edtDiet = findViewById(R.id.diet);
        edtExclusion = findViewById(R.id.exclusion);

        MealPlanService mealPlanService = new MealPlanService(MealPlanGenerator.this);
        btnGenerateMeal = findViewById(R.id.calculateMealPlan);
        btnGenerateMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealPlanService.getGeneratedMeal(spinnerGeneratedFreq.getSelectedItem().toString().toLowerCase(Locale.ROOT), Integer.parseInt(edtTargetCal.getText().toString()), edtDiet.getText().toString(), edtExclusion.getText().toString(), new MealPlanService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MealPlanGenerator.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(ArrayList<Meal> meals) {
                        Intent intent = new Intent(getApplicationContext(), MealPlanResult.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("MEAL_PLAN_LIST", meals);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}