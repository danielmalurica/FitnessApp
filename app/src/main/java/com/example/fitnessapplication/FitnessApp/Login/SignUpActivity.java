package com.example.fitnessapplication.FitnessApp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.R;

public class SignUpActivity extends AppCompatActivity {
    TextView twUsername, twPassword, twConfirmPassword;
    Button register, alreadyAccount;
    String username, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        twUsername = findViewById(R.id.edtUsername);
        twPassword = findViewById(R.id.edtPassword);
        twConfirmPassword = findViewById(R.id.edtConfirmPassword);
        register = findViewById(R.id.register);
        alreadyAccount = findViewById(R.id.alreadyAccount);

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
                    startActivity(new Intent(SignUpActivity.this, OtherDetailsActivity.class));
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