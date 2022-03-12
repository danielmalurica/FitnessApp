package com.example.fitnessapplication.FitnessApp.Classes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitnessapplication.FitnessApp.UsersActivities.RecycleViewAdapter;
import com.example.fitnessapplication.FitnessApp.UsersActivities.SearchFoodActivity;
import com.example.fitnessapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodDataService {
    Context context;
    String foodDescription;


    public FoodDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(List<FoodModel> food);
    }

    public void getFoodByName(String foodName, VolleyResponseListener volleyResponseListener) {
        String url = context.getString(R.string.URL_FOR_FOOD) + foodName;

        List<FoodModel> listOfFoods = new ArrayList<>();
        List<FoodNutrients> listOfNutrients = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray foods = response.getJSONArray("foods");
                            for(int i=0; i< foods.length(); i++){
                                JSONObject food = (JSONObject) foods.get(i);
                                FoodModel foodModel = new FoodModel();
                                foodModel.setFoodId(food.getString("fdcId"));
                                foodModel.setDescription(food.getString("description"));
                               /* if(food.has("ingredients")) {
                                    foodModel.setIngredients(food.getString("ingredients"));

                                }*/
                                foodModel.setFoodCategory(food.getString("foodCategory"));
                                if(food.has("servingSize")){
                                    foodModel.setServingSize(food.getDouble("servingSize"));
                                }
                                if(food.has("servingSizeUnit")) {
                                    foodModel.setServingSizeUnit(food.getString("servingSizeUnit"));
                                }
                                if(food.has("foodNutrients")) {

                                    JSONArray jsonFoodNutrients = food.getJSONArray("foodNutrients");
                                    for (int j = 0; j < 2; j++) {
                                        JSONObject foodNutrient = (JSONObject) jsonFoodNutrients.get(j);
                                        FoodNutrients foodNutrients = new FoodNutrients();
                                        foodNutrients.setNutrientName(foodNutrient.getString("nutrientName"));
                                        foodNutrients.setNutrientUnit(foodNutrient.getString("unitName"));
                                        foodNutrients.setValue(foodNutrient.getDouble("value"));
                                        listOfNutrients.add(foodNutrients);
                                    }
                                    foodModel.setFoodNutrients(listOfNutrients);
                                }
                                listOfFoods.add(foodModel);
                            }
                            Log.i("listoffoods", listOfFoods.get(2).toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(listOfFoods);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        volleyResponseListener.onError("Something wrong!");
                    }
                });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        //return foodDescription;
    }

    public interface FoodById {
        void onError(String message);
        void onResponse(List<FoodModel> listOfFoods);
    }

    public void getInfoFoodByName(String foodName, FoodById foodById){
        String url = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=Tv9PgEGWmPcfjKcBtv6TJeWuYOWwHLcfrEFjHuPJ&query=" + foodName;

        List<FoodModel>  foods = new ArrayList<>();
        List<FoodNutrients> listOfNutrients = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
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
                            listOfNutrients.add(foodNutrients);

                        }
                        foodModel.setFoodNutrients(listOfNutrients);
                        //Toast.makeText(context, foodModel.toString(), Toast.LENGTH_SHORT).show();
                        foods.add(foodModel);
                    }
                    
                    foodById.onResponse(foods);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error loading data...", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
