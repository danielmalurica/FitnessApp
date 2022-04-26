package com.example.fitnessapplication.FitnessApp.UsersActivities.SearchAndAddFood;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fitnessapplication.FitnessApp.Classes.FoodModel;
import com.example.fitnessapplication.FitnessApp.Classes.FoodNutrients;
import com.example.fitnessapplication.FitnessApp.Classes.MySingleton;
import com.example.fitnessapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodDataService {
    Context context;

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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray foods = response.getJSONArray("foods");
                            for(int i=0; i< foods.length(); i++){
                                List<FoodNutrients> listOfNutrients = new ArrayList<>();
                                JSONObject food = (JSONObject) foods.get(i);
                                FoodModel foodModel = new FoodModel();
                                foodModel.setFoodId(food.getString("fdcId"));
                                foodModel.setDescription(food.getString("description"));
                                if(food.has("ingredients")) {
                                    foodModel.setIngredients(food.getString("ingredients"));

                                }
                                foodModel.setFoodCategory(food.getString("foodCategory"));
                                if(food.has("servingSize")){
                                    foodModel.setServingSize(food.getDouble("servingSize"));
                                }
                                if(food.has("servingSizeUnit")) {
                                    foodModel.setServingSizeUnit(food.getString("servingSizeUnit"));
                                }

                                JSONArray jsonFoodNutrients = food.getJSONArray("foodNutrients");
                                    for (int j = 0; j < jsonFoodNutrients.length(); j++) {
                                        JSONObject foodNutrient = (JSONObject) jsonFoodNutrients.get(j);
                                        FoodNutrients foodNutrients = new FoodNutrients();
                                        foodNutrients.setNutrientName(foodNutrient.getString("nutrientName"));
                                        foodNutrients.setNutrientUnit(foodNutrient.getString("unitName"));
                                        foodNutrients.setValue(foodNutrient.getDouble("value"));
                                        listOfNutrients.add(foodNutrients);
                                        foodModel.setFoodNutrients(listOfNutrients);
                                    }
                                listOfFoods.add(foodModel);
                                }


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
    }
}
