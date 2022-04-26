package com.example.fitnessapplication.FitnessApp.UsersActivities.SearchAndAddFood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.FoodModel;
import com.example.fitnessapplication.FitnessApp.UsersActivities.Fragments.Listener;
import com.example.fitnessapplication.R;

import java.util.List;

public class SearchFoodActivity extends AppCompatActivity implements Listener {

    SearchView searchView;
    RecyclerView rvFoodList;
    LinearLayoutManager layoutManager;
    FoodRecyclerViewAdapter foodRecyclerViewAdapter;
    FoodRecyclerViewAdapter.RecyclerViewClickListener recyclerViewClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);

        rvFoodList = findViewById(R.id.foodList);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvFoodList.setLayoutManager(layoutManager);

        FoodDataService foodDataService = new FoodDataService(SearchFoodActivity.this);

        searchView = findViewById(R.id.searchViewFood);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                foodDataService.getFoodByName(query, new FoodDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(SearchFoodActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<FoodModel> food) {
                        recyclerViewClickListener = new FoodRecyclerViewAdapter.RecyclerViewClickListener() {
                            @Override
                            public void onClick(View v, int position) {
                                Intent intent = new Intent(getApplicationContext(), FoodDetailsActivity.class);
                                intent.putExtra("foodDetails", food.get(position));
                                startActivity(intent);
                            }
                        };
                        foodRecyclerViewAdapter = new FoodRecyclerViewAdapter(food, recyclerViewClickListener);
                        rvFoodList.setAdapter(foodRecyclerViewAdapter);
                        foodRecyclerViewAdapter.notifyDataSetChanged();
                    }

                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void itemClick(int id) {
        Intent intent = new Intent(getApplicationContext(), FoodDetailsActivity.class);
        startActivity(intent);
    }
}