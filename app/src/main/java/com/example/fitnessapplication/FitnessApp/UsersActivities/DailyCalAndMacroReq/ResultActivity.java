package com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResultActivity extends AppCompatActivity {

    TextView tvResultCal;
    TextView tvBalancedProtein, tvBalancedFat, tvBalancedCarbs;
    TextView tvLowFatProtein, tvLowFatFat, tvLowFatCarbs;
    TextView tvLowCarbsProtein, tvLowCarbsFat, tvLowCarbsCarbs;
    TextView tvHighProteinProtein, tvHighProteinFat, tvHighProteinCarbs;
    RadioGroup radioGroup;
    RadioButton rbBalanced, rbLowFat, rbLowCarbs, rbHighProtein;
    double calories;
    MacrosClass balanced, lowFat, lowCarbs, highProtein;
    Button btnSaveAllData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        rbBalanced = findViewById(R.id.balanced);
        rbLowFat = findViewById(R.id.lowFat);
        rbLowCarbs = findViewById(R.id.lowCarbs);
        rbHighProtein = findViewById(R.id.highProtein);

        tvResultCal = findViewById(R.id.resultCalories);

        Intent intent = getIntent();
        calories = intent.getDoubleExtra("CALORIES_RESULT", 100);
        balanced = (MacrosClass) intent.getSerializableExtra("BALANCED_MACROS");
        tvBalancedProtein = findViewById(R.id.balancedProtein);
        tvBalancedProtein.setText(String.valueOf(balanced.getProtein()));
        tvBalancedFat = findViewById(R.id.balancedFat);
        tvBalancedFat.setText(String.valueOf(balanced.getFat()));
        tvBalancedCarbs = findViewById(R.id.balancedCarbs);
        tvBalancedCarbs.setText(String.valueOf(balanced.getCarbs()));

        lowFat = (MacrosClass) intent.getSerializableExtra("LOWFAT_MACROS");
        tvLowFatProtein = findViewById(R.id.lowFatProtein);
        tvLowFatProtein.setText(String.valueOf(lowFat.getProtein()));
        tvLowFatFat = findViewById(R.id.lowFatFat);
        tvLowFatFat.setText(String.valueOf(lowFat.getFat()));
        tvLowFatCarbs = findViewById(R.id.lowFatCarbs);
        tvLowFatCarbs.setText(String.valueOf(lowFat.getCarbs()));

        lowCarbs = (MacrosClass) intent.getSerializableExtra("LOWCARBS_MACROS");
        tvLowCarbsProtein = findViewById(R.id.lowCarbsProtein);
        tvLowCarbsProtein.setText(String.valueOf(lowCarbs.getProtein()));
        tvLowCarbsFat = findViewById(R.id.lowCarbsFat);
        tvLowCarbsFat.setText(String.valueOf(lowCarbs.getFat()));
        tvLowCarbsCarbs = findViewById(R.id.lowCarbsCarbs);
        tvLowCarbsCarbs.setText(String.valueOf(lowCarbs.getCarbs()));

        highProtein = (MacrosClass) intent.getSerializableExtra("HIGHPROTEIN_MACROS");
        tvHighProteinProtein = findViewById(R.id.highProteinProtein);
        tvHighProteinProtein.setText(String.valueOf(highProtein.getProtein()));
        tvHighProteinFat = findViewById(R.id.highProteinFat);
        tvHighProteinFat.setText(String.valueOf(highProtein.getFat()));
        tvHighProteinCarbs = findViewById(R.id.highProteinCarbs);
        tvHighProteinCarbs.setText(String.valueOf(highProtein.getCarbs()));

        Log.i("balanced", balanced.toString());
        tvResultCal.setText(String.valueOf((int)calories));

        radioGroup = findViewById(R.id.radioGroup);

        btnSaveAllData = findViewById(R.id.saveAllData);
        btnSaveAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("caloriesAndMacros");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                myRef.child(user.getUid()).child("goals").child("calories").setValue(calories);

                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                View button = radioGroup.findViewById(radioButtonId);
                int index = radioGroup.indexOfChild(button);

                RadioButton r = (RadioButton) radioGroup.getChildAt(index);
                String selectedText = r.getText().toString();
                switch (selectedText) {
                    case "Balanced":
                        myRef.child(user.getUid()).child("goals").child("proteins").setValue(balanced.getProtein());
                        myRef.child(user.getUid()).child("goals").child("fat").setValue(balanced.getFat());
                        myRef.child(user.getUid()).child("goals").child("carbs").setValue(balanced.getCarbs());
                        break;

                    case "Low Fat":
                        myRef.child(user.getUid()).child("goals").child("proteins").setValue(lowFat.getProtein());
                        myRef.child(user.getUid()).child("goals").child("fat").setValue(lowFat.getFat());
                        myRef.child(user.getUid()).child("goals").child("carbs").setValue(lowFat.getCarbs());
                        break;
                    case "Low Carbs":
                        myRef.child(user.getUid()).child("goals").child("proteins").setValue(lowCarbs.getProtein());
                        myRef.child(user.getUid()).child("goals").child("fat").setValue(lowCarbs.getFat());
                        myRef.child(user.getUid()).child("goals").child("carbs").setValue(lowCarbs.getCarbs());
                        break;
                    case "High Protein":
                        myRef.child(user.getUid()).child("goals").child("proteins").setValue(highProtein.getProtein());
                        myRef.child(user.getUid()).child("goals").child("fat").setValue(highProtein.getFat());
                        myRef.child(user.getUid()).child("goals").child("carbs").setValue(highProtein.getCarbs());
                        break;
                }

                Toast.makeText(ResultActivity.this, "Data saved!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}