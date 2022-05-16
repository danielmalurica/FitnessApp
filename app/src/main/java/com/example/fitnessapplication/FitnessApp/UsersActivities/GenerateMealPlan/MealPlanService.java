package com.example.fitnessapplication.FitnessApp.UsersActivities.GenerateMealPlan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fitnessapplication.FitnessApp.Classes.FoodNutrients;
import com.example.fitnessapplication.FitnessApp.Classes.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealPlanService {
    Context context;

    public MealPlanService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(ArrayList<Meal> meals);
    }

    public interface VolleyResponseListenerForMeal {
        void onError(String message);
        void onResponse(Meal meal);
    }

    public void getGeneratedMeal(String period, int calories, String diet, String exclusion, VolleyResponseListener volleyResponseListener){
        String url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/mealplans/generate?timeFrame="+ period +"&targetCalories="+ calories +"&diet="+ diet +"&exclude=" +exclusion;

        ArrayList<Meal> mealList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray meals = response.getJSONArray("meals");
                            for (int i = 0; i < meals.length(); i++) {
                                JSONObject meal = (JSONObject) meals.get(i);
                                Meal mealModel = new Meal();
                                mealModel.setId(meal.getInt("id"));
                                mealModel.setTitle(meal.getString("title"));
                                mealModel.setReadyInMinutes(meal.getInt("readyInMinutes"));
                                mealModel.setServings(meal.getInt("servings"));
                                mealModel.setSourceUrl(meal.getString("sourceUrl"));
                                mealList.add(mealModel);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(mealList);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error "+ error.toString(), Toast.LENGTH_SHORT).show();
                        volleyResponseListener.onError("Something wrong!");
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com");
                params.put("X-RapidAPI-Key", "77d3a01ee9msh48590f165204cadp191ad0jsn682012a648d9");

                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void getDataForMeal(int id, VolleyResponseListenerForMeal volleyResponseListenerForMeal){
        String url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/"+id+"/information?includeNutrition=true";

        Meal meal = new Meal();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("Response json", response.toString());
                            if(response.has("vegetarian")){
                                meal.setVegetarian(response.getBoolean("vegetarian"));

                            }
                            if(response.has("vegan")){
                                meal.setVegan(response.getBoolean("vegan"));

                            }
                            if(response.has("glutenFree")){
                                meal.setGlutenFree(response.getBoolean("glutenFree"));

                            }
                            if(response.has("diaryFree")){
                                meal.setDiaryFree(response.getBoolean("diaryFree"));

                            }
                            meal.setId(response.getInt("id"));
                            meal.setTitle(response.getString("title"));
                            meal.setReadyInMinutes(response.getInt("readyInMinutes"));
                            meal.setServings(response.getInt("servings"));
                            meal.setSourceUrl(response.getString("sourceUrl"));
                            meal.setImageUrl(response.getString("image"));
                            List<FoodNutrients> listOfNutrients = new ArrayList<>();
                            JSONObject jsonObjectNutrition = response.getJSONObject("nutrition");
                            JSONArray jsonArrayNutrients = jsonObjectNutrition.getJSONArray("nutrients");
                            for (int i = 0; i<jsonArrayNutrients.length(); i++){
                                JSONObject nutrient = (JSONObject) jsonArrayNutrients.get(i);
                                FoodNutrients foodNutrients = new FoodNutrients();
                                foodNutrients.setNutrientName(nutrient.getString("name"));
                                foodNutrients.setValue(nutrient.getDouble("amount"));
                                foodNutrients.setNutrientUnit(nutrient.getString("unit"));
                                listOfNutrients.add(foodNutrients);
                            }
                            meal.setNutrients(listOfNutrients);
                            List<Ingredient> listOfIngredients = new ArrayList<>();

                            JSONArray jsonArrayIngredients = response.getJSONArray("extendedIngredients");
                            for (int i = 0; i<jsonArrayIngredients.length(); i++){
                                JSONObject ingredients = (JSONObject) jsonArrayIngredients.get(i);
                                Ingredient ingredient = new Ingredient();
                                ingredient.setName(ingredients.getString("name"));
                                if(ingredients.has("original")){
                                    ingredient.setUnit(ingredients.getString("original"));
                                }
                                listOfIngredients.add(ingredient);
                            }
                            meal.setIngredients(listOfIngredients);
                            meal.setSummary(response.getString("summary"));
                            meal.setInstructions(response.getString("instructions"));
                            volleyResponseListenerForMeal.onResponse(meal);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListenerForMeal.onError(error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com");
                params.put("X-RapidAPI-Key", "77d3a01ee9msh48590f165204cadp191ad0jsn682012a648d9");

                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
