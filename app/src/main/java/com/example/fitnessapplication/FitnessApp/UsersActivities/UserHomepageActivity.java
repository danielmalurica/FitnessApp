package com.example.fitnessapplication.FitnessApp.UsersActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitnessapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserHomepageActivity extends AppCompatActivity {

    Button buttonToBmi, buttonJson;
    TextView textView;
    EditText edtFood;
    String foodName;

    String url = "https://jsonkeeper.com/b/63OH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        edtFood = findViewById(R.id.editTextFood);
        buttonToBmi = findViewById(R.id.btnToBmi);
        buttonToBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BmiCalculatorActivity.class));
            }
        });

        buttonJson = findViewById(R.id.json);
        buttonJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FoodListActivity.class));
            }
        });
        textView = findViewById(R.id.textView3);

    }
}