package com.example.fitnessapplication.FitnessApp.UsersActivities.GenerateMealPlan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealPlanResult extends AppCompatActivity {
    TextView tvGeneratedBreakfast, tvGeneratedLunch, tvGeneratedDinner;
    TextView minGeneratedBreakfast, minGeneratedLunch, minGeneratedDinner;
    Button btnMoreInfoBreakfast, btnMoreInfoLunch, btnMoreInfoDinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan_result);

        ArrayList<Meal> resultedList = new ArrayList<>();
        resultedList = this.getIntent().getExtras().getParcelableArrayList("MEAL_PLAN_LIST");
        Log.i("ImageURL", resultedList.get(0).getSourceUrl());

        tvGeneratedBreakfast = findViewById(R.id.generatedBreakfast);
        tvGeneratedBreakfast.setText(resultedList.get(0).getTitle());
        minGeneratedBreakfast = findViewById(R.id.minBreakfast);
        minGeneratedBreakfast.setText(String.valueOf(resultedList.get(0).getReadyInMinutes()));
        btnMoreInfoBreakfast = findViewById(R.id.moreInfoBreakfast);
        int breakfastId = resultedList.get(0).getId();
        btnMoreInfoBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("MEAL_ID", breakfastId);
                bundle.putString("PERIOD", "breakfast");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tvGeneratedLunch = findViewById(R.id.generatedLunch);
        tvGeneratedLunch.setText(resultedList.get(1).getTitle());
        minGeneratedLunch= findViewById(R.id.minLunch);
        minGeneratedLunch.setText(String.valueOf(resultedList.get(1).getReadyInMinutes()));
        btnMoreInfoLunch = findViewById(R.id.moreInfoLunch);
        int lunchId = resultedList.get(1).getId();
        btnMoreInfoLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("MEAL_ID", lunchId);
                bundle.putString("PERIOD", "lunch");

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tvGeneratedDinner = findViewById(R.id.generatedDinner);
        tvGeneratedDinner.setText(resultedList.get(2).getTitle());
        minGeneratedDinner = findViewById(R.id.minDinner);
        minGeneratedDinner.setText(String.valueOf(resultedList.get(2).getReadyInMinutes()));
        btnMoreInfoDinner = findViewById(R.id.moreInfoDinner);
        int dinnerId = resultedList.get(0).getId();
        btnMoreInfoDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("MEAL_ID", dinnerId);
                bundle.putString("PERIOD", "dinner");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}