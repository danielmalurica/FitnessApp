package com.example.fitnessapplication.FitnessApp.UsersActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitnessapplication.FitnessApp.Classes.FoodDataService;
import com.example.fitnessapplication.FitnessApp.Classes.FoodListAdapter;
import com.example.fitnessapplication.FitnessApp.Classes.FoodModel;
import com.example.fitnessapplication.FitnessApp.Classes.FoodNutrients;
import com.example.fitnessapplication.FitnessApp.Classes.MySingleton;
import com.example.fitnessapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodListActivity extends AppCompatActivity {

    EditText edtFood;
    Button btnFindFood;
    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<FoodModel> listOfFood;
    private List<FoodNutrients> foodNutrientsList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);


        recyclerView = findViewById(R.id.recyclerView);
        listOfFood = new ArrayList<>();
        foodNutrientsList = new ArrayList<>();
        adapter = new FoodListAdapter(getApplicationContext(), listOfFood);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        edtFood = findViewById(R.id.edtFind);


        btnFindFood = findViewById(R.id.search_btn);
        btnFindFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfoFoodByName(edtFood.getText().toString());
                Toast.makeText(getApplicationContext(), edtFood.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getInfoFoodByName(String foodName){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=Tv9PgEGWmPcfjKcBtv6TJeWuYOWwHLcfrEFjHuPJ&query=" +foodName;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listOfFood.clear();
                    JSONArray foodList = response.getJSONArray("foods");
                    for(int i=0; i < foodList.length(); i++){
                        JSONObject food = (JSONObject) foodList.get(i);
                        FoodModel foodModel = new FoodModel();
                        foodModel.setDescription(food.getString("description"));
                        //foodModel.setIngredients(food.getString("ingredients"));
                        foodModel.setFoodCategory(food.getString("foodCategory"));
                        //foodModel.setServingSize(food.getInt("servingSize"));
                        //foodModel.setServingSizeUnit(food.getString("servingSizeUnit"));

                        JSONArray nutrientsList = food.getJSONArray("foodNutrients");
                        for(int j=0; j<nutrientsList.length(); j++){
                            JSONObject nutrient = (JSONObject) nutrientsList.get(j);
                            FoodNutrients foodNutrients = new FoodNutrients();

                            foodNutrients.setNutrientName(nutrient.getString("nutrientName"));
                            foodNutrients.setNutrientUnit(nutrient.getString("unitName"));
                            foodNutrients.setValue(nutrient.getDouble("value"));
                            foodNutrientsList.add(foodNutrients);
                        }

                        foodModel.setFoodNutrients(foodNutrientsList);

                        listOfFood.add(foodModel);
                        Toast.makeText(getApplicationContext(), foodNutrientsList.toString(), Toast.LENGTH_SHORT).show();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FoodListActivity.this, "Error loading data...", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(FoodListActivity.this).addToRequestQueue(request);
    }
}