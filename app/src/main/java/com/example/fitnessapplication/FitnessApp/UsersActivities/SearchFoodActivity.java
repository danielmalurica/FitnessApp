package com.example.fitnessapplication.FitnessApp.UsersActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitnessapplication.FitnessApp.Classes.FoodModel;
import com.example.fitnessapplication.FitnessApp.Classes.FoodNutrients;
import com.example.fitnessapplication.FitnessApp.UsersActivities.Fragments.FoodListFragment;
import com.example.fitnessapplication.FitnessApp.UsersActivities.Fragments.Listener;
import com.example.fitnessapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SearchFoodActivity extends AppCompatActivity implements Listener {

    private List<FoodModel> listOfFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=Tv9PgEGWmPcfjKcBtv6TJeWuYOWwHLcfrEFjHuPJ&query=cake";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listOfFood.clear();
                    JSONArray foodList = response.getJSONArray("foods");


                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        
        queue.add(request);
    }

    @Override
    public void itemClick(int id) {
        Intent intent = new Intent(getApplicationContext(), FoodDetailsActivity.class);
        startActivity(intent);
    }
}