package com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.User;
import com.example.fitnessapplication.FitnessApp.UsersActivities.BMI.BmiUpdateDataActivity;
import com.example.fitnessapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ListOfActivititiesActivity extends AppCompatActivity {

    private static final String TAG = ListOfActivititiesActivity.class.getName();
    TextView tvBmrGender, tvBmrAge, tvBmrHeight, tvBmrWeight;
    String selectedActivityLevel;
    Button btnBmrCalculateCalories, btnBmrUpdateData;

    String userId;
    User userData;
    DailyNutrientsClass dailyNutrientsClass;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_activitities);

        tvBmrGender = findViewById(R.id.bmrGender);
        tvBmrAge = findViewById(R.id.bmrAge);
        tvBmrHeight = findViewById(R.id.bmrHeight);
        tvBmrWeight = findViewById(R.id.bmrWeight);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userData = new User();
        dailyNutrientsClass = new DailyNutrientsClass();
        userId = mAuth.getCurrentUser().getUid();

        documentReference = firebaseFirestore.collection("users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        userData = documentSnapshot.toObject(User.class);
                        tvBmrGender.setText(userData.getGender());
                        tvBmrAge.setText(Integer.toString(userData.getAge()));
                        tvBmrHeight.setText(Double.toString(userData.getHeight()));
                        tvBmrWeight.setText(Double.toString(userData.getWeight()));
                    } else {
                        Toast.makeText(ListOfActivititiesActivity.this, "No info about you!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ListOfActivititiesActivity.this, "Please complete in 'UPDATE DATA ABOUT ME' section", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ListOfActivititiesActivity.this, "get failed with "+ task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBmrCalculateCalories = findViewById(R.id.goToActivityLevel);
        btnBmrCalculateCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityLevel.class);
                dailyNutrientsClass.setAge(Integer.parseInt(tvBmrAge.getText().toString()));
                dailyNutrientsClass.setGender(tvBmrGender.getText().toString());
                dailyNutrientsClass.setHeight(Double.parseDouble(tvBmrHeight.getText().toString()));
                dailyNutrientsClass.setWeight(Double.parseDouble(tvBmrWeight.getText().toString()));
                intent.putExtra("USER_DAILY_NUTRIENTS", dailyNutrientsClass);
                startActivity(intent);
            }
        });

        btnBmrUpdateData = findViewById(R.id.bmrupdateData);
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

}