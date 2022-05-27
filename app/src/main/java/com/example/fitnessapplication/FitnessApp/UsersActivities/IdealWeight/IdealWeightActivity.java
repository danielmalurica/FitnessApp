package com.example.fitnessapplication.FitnessApp.UsersActivities.IdealWeight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.User;
import com.example.fitnessapplication.FitnessApp.UsersActivities.BMI.BmiUserDataActivity;
import com.example.fitnessapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;

public class IdealWeightActivity extends AppCompatActivity {

    TextView tvUserWeight;
    TextView tvIdealMin;
    TextView tvIdealMax;
    TextView tvModifiedWeightText;
    TextView tvModifiedWeight;

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final String TAG = BmiUserDataActivity.class.getName();
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;
    User userData;
    String userId;
    String userGender;
    int userHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideal_weight);

        tvUserWeight = findViewById(R.id.userWeight);
        tvIdealMin = findViewById(R.id.minIdealWeight);
        tvIdealMax = findViewById(R.id.maxIdealWeight);
        tvModifiedWeightText = findViewById(R.id.modifiedWeightText);
        tvModifiedWeight = findViewById(R.id.modifiedWeight);

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
                        tvUserWeight.setText(String.valueOf(userData.getWeight()));
                        userGender = userData.getGender();
                        userHeight = userData.getHeight();
                        Toast.makeText(IdealWeightActivity.this, userGender.toLowerCase(), Toast.LENGTH_SHORT).show();
                        IdealWeightDataService idealWeightDataService = new IdealWeightDataService(IdealWeightActivity.this);
                        idealWeightDataService.calculateIdealWeight(userGender.toLowerCase(), userHeight, new IdealWeightDataService.VolleyResponseListener() {
                            @Override
                            public void onError(String message) {
                                Toast.makeText(IdealWeightActivity.this, message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(IdealWeight idealWeight) {
                                tvIdealMin.setText(String.valueOf(df.format(idealWeight.getMinWeight())));
                                tvIdealMax.setText(String.valueOf(df.format(idealWeight.getMaxWeight())));
                                if(Double.parseDouble(tvUserWeight.getText().toString()) < Double.parseDouble(tvIdealMin.getText().toString())){
                                    double modifiedWeight = Double.parseDouble(tvIdealMin.getText().toString()) - Double.parseDouble(tvUserWeight.getText().toString());
                                    tvModifiedWeightText.setText("You need to gain ");
                                    tvModifiedWeight.setText(df.format(modifiedWeight));
                                } else if(Double.parseDouble(tvUserWeight.getText().toString()) > Double.parseDouble(tvIdealMax.getText().toString())){
                                    double modifiedWeight = Double.parseDouble(tvUserWeight.getText().toString()) - Double.parseDouble(tvIdealMax.getText().toString());
                                    tvModifiedWeightText.setText("You need to lose ");
                                    tvModifiedWeight.setText(String.valueOf(df.format(modifiedWeight)));
                                }
                            }
                        });
                    } else {
                        Toast.makeText(IdealWeightActivity.this, "No data for current user!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}