package com.example.fitnessapplication.FitnessApp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    TextView twUsername, twPassword, twConfirmPassword;
    Button register, alreadyAccount;
    String username, password, confirmPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        twUsername = findViewById(R.id.edtUsername);
        twPassword = findViewById(R.id.edtPassword);
        twConfirmPassword = findViewById(R.id.edtConfirmPassword);
        register = findViewById(R.id.register);
        alreadyAccount = findViewById(R.id.alreadyAccount);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.fitnessapplication", Context.MODE_PRIVATE);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = twUsername.getText().toString();
                password = twPassword.getText().toString();
                confirmPassword = twConfirmPassword.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(username.isEmpty()) {
                    twUsername.setError("Username is missing");
                    twUsername.requestFocus();
                    return;
                }
                if(password.isEmpty()) {
                    twPassword.setError("Password is missing");
                    twPassword.requestFocus();
                    return;
                }
                if(confirmPassword.isEmpty()) {
                    twConfirmPassword.setError("Confirm password is missing");
                    twConfirmPassword.requestFocus();
                    return;
                }

                if(!password.equals(confirmPassword)){
                    Toast.makeText(SignUpActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();
                    mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(SignUpActivity.this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();

                               Intent intent = new Intent(getApplicationContext(), OtherDetailsActivity.class);
                               startActivity(intent);
                           }
                           else {
                               Toast.makeText(SignUpActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
                }
            }
        });

        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

    }
}