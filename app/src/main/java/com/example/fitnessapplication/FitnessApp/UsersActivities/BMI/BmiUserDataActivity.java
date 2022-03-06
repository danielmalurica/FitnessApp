package com.example.fitnessapplication.FitnessApp.UsersActivities.BMI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.User;
import com.example.fitnessapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;

public class BmiUserDataActivity extends AppCompatActivity {

    private static final String TAG = BmiUserDataActivity.class.getName();
    private static final DecimalFormat df = new DecimalFormat("0.00");
    TextView twName, twAge, twHeight, twWeight;
    Button btnCalculateBmi, btnUpdateData;

    double bmi, userWeight, userHeight;
    String bmiString;
    String userId;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;
    User userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_user_data);

        twName = findViewById(R.id.personalName);
        twAge = findViewById(R.id.personalAge);
        twHeight = findViewById(R.id.personalHeight);
        twWeight = findViewById(R.id.personalWeight);
        btnCalculateBmi = findViewById(R.id.calculateBmi);
        btnUpdateData = findViewById(R.id.updateData);

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
                        twName.setText(userData.getName());
                        twAge.setText(Integer.toString(userData.getAge()));
                        twHeight.setText(Double.toString(userData.getHeight()));
                        twWeight.setText(Double.toString(userData.getWeight()));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        btnCalculateBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userHeight = Double.parseDouble(twHeight.getText().toString());
                userWeight = Double.parseDouble(twWeight.getText().toString());
                bmi = calculateBmi(userWeight, userHeight, bmi);
                bmiString = Double.toString(bmi);

                Intent intent = new Intent(getApplicationContext(), BmiResultActivity.class);
                intent.putExtra("bmiResult", bmiString);
                startActivity(intent);
            }
        });

        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BmiUpdateDataActivity.class);
                startActivity(intent);
            }
        });
    }
    public double calculateBmi(double weight, double height, double bm) {
        double height2 = height / 100;
        bm = Double.parseDouble(df.format(weight / (height2 * height2)));
        return bm;
    }
}