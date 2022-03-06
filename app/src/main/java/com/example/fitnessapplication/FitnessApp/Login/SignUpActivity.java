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
import com.google.firebase.auth.FirebaseAuthException;
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
                               String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                               switch (errorCode) {

                                   case "ERROR_INVALID_CUSTOM_TOKEN":
                                       Toast.makeText(SignUpActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                       Toast.makeText(SignUpActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_INVALID_CREDENTIAL":
                                       Toast.makeText(SignUpActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_INVALID_EMAIL":
                                       Toast.makeText(SignUpActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_WRONG_PASSWORD":
                                       Toast.makeText(SignUpActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_USER_MISMATCH":
                                       Toast.makeText(SignUpActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_REQUIRES_RECENT_LOGIN":
                                       Toast.makeText(SignUpActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                       Toast.makeText(SignUpActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_EMAIL_ALREADY_IN_USE":
                                       Toast.makeText(SignUpActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                       Toast.makeText(SignUpActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_USER_DISABLED":
                                       Toast.makeText(SignUpActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_USER_TOKEN_EXPIRED":
                                       Toast.makeText(SignUpActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_USER_NOT_FOUND":
                                       Toast.makeText(SignUpActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_INVALID_USER_TOKEN":
                                       Toast.makeText(SignUpActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_OPERATION_NOT_ALLOWED":
                                       Toast.makeText(SignUpActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                       break;

                                   case "ERROR_WEAK_PASSWORD":
                                       Toast.makeText(SignUpActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                       break;
                               }
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