package com.example.fitnessapplication.FitnessApp.UsersActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitnessapplication.FitnessApp.Classes.FoodDataService;
import com.example.fitnessapplication.FitnessApp.Classes.FoodModel;
import com.example.fitnessapplication.FitnessApp.Classes.FoodNutrients;
import com.example.fitnessapplication.FitnessApp.Classes.FoodRecyclerViewAdapter;
import com.example.fitnessapplication.FitnessApp.UsersActivities.Fragments.FoodListFragment;
import com.example.fitnessapplication.FitnessApp.UsersActivities.Fragments.Listener;
import com.example.fitnessapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFoodActivity extends AppCompatActivity implements Listener {

    private List<FoodModel> listOfFood;
    Button btnFindFood;
    EditText edtfoodName;
    RecyclerView rvFoodList;
    LinearLayoutManager layoutManager;
    FoodRecyclerViewAdapter foodRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);

        btnFindFood = findViewById(R.id.findFood);
        edtfoodName = findViewById(R.id.searchFood);
        rvFoodList = findViewById(R.id.foodList);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvFoodList.setLayoutManager(layoutManager);


        FoodDataService foodDataService = new FoodDataService(SearchFoodActivity.this);

        btnFindFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodDataService.getFoodByName(edtfoodName.getText().toString(), new FoodDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(SearchFoodActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<FoodModel> food) {
                        foodRecyclerViewAdapter = new FoodRecyclerViewAdapter(food);
                        rvFoodList.setAdapter(foodRecyclerViewAdapter);
                        foodRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    @Override
    public void itemClick(int id) {
        Intent intent = new Intent(getApplicationContext(), FoodDetailsActivity.class);
        startActivity(intent);
    }
}