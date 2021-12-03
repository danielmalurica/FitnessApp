package com.example.fitnessapplication.FitnessApp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.AdminPanel.AdminHomepageActivity;
import com.example.fitnessapplication.FitnessApp.Classes.DatabaseHelper;
import com.example.fitnessapplication.FitnessApp.Classes.User;
import com.example.fitnessapplication.FitnessApp.UsersActivities.UserHomepageActivity;
import com.example.fitnessapplication.R;

public class SignInActivity extends AppCompatActivity {

    EditText username, password;
    Button buttonSignIn;
    DatabaseHelper databaseHelper;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = findViewById(R.id.edtUsername);
        password = findViewById(R.id.edtPassword);
        buttonSignIn = findViewById(R.id.signIn);
        databaseHelper = new DatabaseHelper(SignInActivity.this);

        SharedPreferences sharedPreferences=this.getSharedPreferences("userDetails", Context.MODE_PRIVATE);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String passw = password.getText().toString().trim();

                if(user.isEmpty()) {
                    username.setError("Username is missing");
                    username.requestFocus();
                    return;
                }

                if(passw.isEmpty()) {
                    password.setError("Password is missing");
                    password.requestFocus();
                    return;
                }

                Boolean checkUserAndPassword = databaseHelper.findUser(user, passw);
                if(checkUserAndPassword == true) {
                    if(user.equals(getString(R.string.admin_username)) && passw.equals(getString(R.string.admin_password))){
                        startActivity(new Intent(SignInActivity.this, AdminHomepageActivity.class));

                    } else {
                        startActivity(new Intent(SignInActivity.this, UserHomepageActivity.class));
                    }
                    userId = databaseHelper.getIdOfUser(user, passw);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userId", userId);
                    editor.commit();
                    Toast.makeText(SignInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}