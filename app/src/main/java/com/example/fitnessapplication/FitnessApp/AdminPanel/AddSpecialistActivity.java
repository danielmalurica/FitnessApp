package com.example.fitnessapplication.FitnessApp.AdminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Login.OtherDetailsActivity;
import com.example.fitnessapplication.FitnessApp.Login.SignUpActivity;
import com.example.fitnessapplication.FitnessApp.UsersActivities.BMI.BmiUserDataActivity;
import com.example.fitnessapplication.FitnessApp.UsersActivities.Specialists.Specialist;
import com.example.fitnessapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddSpecialistActivity extends AppCompatActivity {

    EditText edtSpecialistEmail, edtSpecalistPassword, edtSpecialistRePassword;
    Button btnRegisterSpecialist;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_specialist);

        edtSpecialistEmail = findViewById(R.id.specialistEmail);
        edtSpecalistPassword = findViewById(R.id.specialistPassword);
        edtSpecialistRePassword = findViewById(R.id.specialistConfirmPassword);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnRegisterSpecialist = findViewById(R.id.registerSpecialist);
        btnRegisterSpecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtSpecialistEmail.getText().toString();
                String password = edtSpecalistPassword.getText().toString();
                String confirmPassword = edtSpecialistRePassword.getText().toString();

                if(email.isEmpty()) {
                    edtSpecialistEmail.setError("Email is missing");
                    edtSpecialistEmail.requestFocus();
                    return;
                }
                if(password.isEmpty()) {
                    edtSpecalistPassword.setError("Password is missing");
                    edtSpecalistPassword.requestFocus();
                    return;
                }
                if(confirmPassword.isEmpty()) {
                    edtSpecialistRePassword.setError("Confirm password is missing");
                    edtSpecialistRePassword.requestFocus();
                    return;
                }

                if(!password.equals(confirmPassword)){
                    Toast.makeText(AddSpecialistActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(edtSpecialistEmail.getText().toString(), edtSpecalistPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddSpecialistActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                Specialist specialist = new Specialist();
                                userId = mAuth.getCurrentUser().getUid();
                                firebaseFirestore.collection("specialists")
                                        .document(userId)
                                        .set(specialist)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(AddSpecialistActivity.this, "Data added!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), AdminHomepageActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AddSpecialistActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                                switch (errorCode) {

                                    case "ERROR_INVALID_CUSTOM_TOKEN":
                                        Toast.makeText(AddSpecialistActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                        Toast.makeText(AddSpecialistActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_INVALID_CREDENTIAL":
                                        Toast.makeText(AddSpecialistActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_INVALID_EMAIL":
                                        Toast.makeText(AddSpecialistActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_WRONG_PASSWORD":
                                        Toast.makeText(AddSpecialistActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_MISMATCH":
                                        Toast.makeText(AddSpecialistActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_REQUIRES_RECENT_LOGIN":
                                        Toast.makeText(AddSpecialistActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                        Toast.makeText(AddSpecialistActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_EMAIL_ALREADY_IN_USE":
                                        Toast.makeText(AddSpecialistActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                        Toast.makeText(AddSpecialistActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_DISABLED":
                                        Toast.makeText(AddSpecialistActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_TOKEN_EXPIRED":
                                        Toast.makeText(AddSpecialistActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_NOT_FOUND":
                                        Toast.makeText(AddSpecialistActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_INVALID_USER_TOKEN":
                                        Toast.makeText(AddSpecialistActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_OPERATION_NOT_ALLOWED":
                                        Toast.makeText(AddSpecialistActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_WEAK_PASSWORD":
                                        Toast.makeText(AddSpecialistActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}