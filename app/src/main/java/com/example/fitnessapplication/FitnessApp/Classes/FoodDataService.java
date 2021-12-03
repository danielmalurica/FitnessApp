package com.example.fitnessapplication.FitnessApp.Classes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fitnessapplication.FitnessApp.UsersActivities.RecycleViewAdapter;

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
        void onResponse(String foodDescripton);
    }

    public void getFoodName(String foodName, VolleyResponseListener volleyResponseListener) {
        //RequestQueue queue = MySingleton.getInstance(FoodListActivity.this).getRequestQueue();

        String url = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=Tv9PgEGWmPcfjKcBtv6TJeWuYOWwHLcfrEFjHuPJ&query=" + foodName;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                   // JSONArray jsonArray = response.getJSONArray("foods");

                   // JSONObject food = jsonArray.getJSONObject(0);

                    foodDescription = response.getString("totalHits");

                    //Toast.makeText(context, foodDescription, Toast.LENGTH_SHORT).show();
                    volleyResponseListener.onResponse(foodDescription);

                    // we are using picasso to load the image from url.
                } catch (JSONException e) {
                    // if we do not extract data from json object properly.
                    // below line of code is use to handle json exception
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            // this is the error listener method which
            // we will call if we get any error from API.
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Error connection", Toast.LENGTH_SHORT).show();
                Log.i("error", error.toString());
                volleyResponseListener.onError("Something went wrong");
            }
        });
        // at last we are adding our json
        // object request to our request
        // queue to fetch all the json data.
        //queue.add(jsonObjectRequest);
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
