package com.example.fitnessapplication.FitnessApp.UsersActivities.ConsumedCalories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivityDetails extends AppCompatActivity {

    TextView activityDescription;
    EditText nbOfMin, weight;
    Button calculateCal;

    private DatabaseReference myRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        activityDescription = findViewById(R.id.actDescription);
        nbOfMin = findViewById(R.id.nbOfMin);
        weight = findViewById(R.id.weight);
        calculateCal = findViewById(R.id.calculateCalories);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("ACTIVITY_ID");
        String description = extras.getString("ACTIVITY_DESCRIPTION");
        activityDescription.setText(description);
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        myRef = FirebaseDatabase.getInstance().getReference("caloriesAndMacros");
        user = FirebaseAuth.getInstance().getCurrentUser();

        calculateCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityDataService activityDataService = new ActivityDataService(ActivityDetails.this);
                activityDataService.getBurnedCaloriesFromActivity(id, Integer.parseInt(nbOfMin.getText().toString()), Integer.parseInt(weight.getText().toString()), new ActivityDataService.VolleyResponseListenerForConsumedCal() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(ActivityDetails.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String burnedCal, String unit) {
                        Toast.makeText(ActivityDetails.this, "Burned cal " + burnedCal + "Unit" + unit, Toast.LENGTH_SHORT).show();
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        myRef.child(user.getUid()).child("activities").child(currentDate).child("burnedCalories").setValue(burnedCal);
                        myRef.child(user.getUid()).child("activities").child(currentDate).child("unit").setValue(unit);
                    }
                });
            }
        });

    }
}