package com.example.fitnessapplication.FitnessApp.Login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.User;
import com.example.fitnessapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OtherDetailsActivity extends AppCompatActivity {
    String username, password;
    TextView textViewName, textViewAge, textViewWeight, textViewHeight;
    Button saveData;
    Spinner spinner;

    String userId, userEmail;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_details);

        LocalDate localDate = LocalDate.now();

        textViewName = findViewById(R.id.edtName);
        textViewAge = findViewById(R.id.edtAge);
        textViewWeight = findViewById(R.id.edtWeight);
        textViewHeight = findViewById(R.id.edtHeight);
        spinner = findViewById(R.id.spinnerGender);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        userEmail = mAuth.getCurrentUser().getEmail();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK);
                ((TextView) view).setTextSize(25);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.fitnessapplication", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        password = sharedPreferences.getString("password", "");

        saveData = findViewById(R.id.saveData);

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewName.getText().toString().isEmpty()) {
                    textViewName.setError("Name is missing");
                    textViewName.requestFocus();
                    return;
                }

                if(textViewAge.getText().toString().isEmpty()) {
                    textViewAge.setError("Age is missing");
                    textViewAge.requestFocus();
                    return;
                }

                if(textViewWeight.getText().toString().isEmpty()) {
                    textViewWeight.setError("Weight is missing");
                    textViewWeight.requestFocus();
                    return;
                }

                if(textViewHeight.getText().toString().isEmpty()){
                    textViewHeight.setError("Height is missing");
                    textViewHeight.requestFocus();
                    return;
                }

                User user = new User(userId, userEmail, textViewName.getText().toString(), Integer.parseInt(textViewAge.getText().toString()), Integer.parseInt(textViewWeight.getText().toString()), Integer.parseInt(textViewHeight.getText().toString()), spinner.getSelectedItem().toString());
                Map<String, Object> userOtherInfo = new HashMap<>();
                userOtherInfo.put("uid", user.getId());
                userOtherInfo.put("name", user.getName());
                userOtherInfo.put("email", user.getEmail());
                userOtherInfo.put("age", user.getAge());
                userOtherInfo.put("weight", user.getWeight());
                userOtherInfo.put("height", user.getHeight());
                userOtherInfo.put("gender", user.getGender());


                firebaseFirestore.collection("users")
                        .document(userId)
                        .set(userOtherInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(OtherDetailsActivity.this, "Data added!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(OtherDetailsActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


    }
}