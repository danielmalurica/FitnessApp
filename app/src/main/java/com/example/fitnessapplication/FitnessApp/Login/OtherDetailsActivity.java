package com.example.fitnessapplication.FitnessApp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.DatabaseHelper;
import com.example.fitnessapplication.FitnessApp.Classes.User;
import com.example.fitnessapplication.R;

public class OtherDetailsActivity extends AppCompatActivity {
    String username, password;
    TextView textViewAge, textViewWeight, textViewHeight;
    Button saveData;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_details);

        textViewAge = findViewById(R.id.edtAge);
        textViewWeight = findViewById(R.id.edtWeight);
        textViewHeight = findViewById(R.id.edtHeight);
        spinner = findViewById(R.id.spinnerGender);

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

                User user = new User(-1, username, password, Integer.parseInt(textViewAge.getText().toString()), Integer.parseInt(textViewWeight.getText().toString()), Integer.parseInt(textViewHeight.getText().toString()), spinner.getSelectedItem().toString());

                DatabaseHelper databaseHelper = new DatabaseHelper(OtherDetailsActivity.this);
                boolean addedSuccessfully = databaseHelper.addUser(user);
                if(addedSuccessfully){
                    Toast.makeText(OtherDetailsActivity.this, "Informations saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OtherDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(OtherDetailsActivity.this, SignInActivity.class));
            }
        });


    }
}