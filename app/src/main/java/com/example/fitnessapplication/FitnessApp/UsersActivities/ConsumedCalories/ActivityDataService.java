package com.example.fitnessapplication.FitnessApp.UsersActivities.ConsumedCalories;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fitnessapplication.FitnessApp.Classes.FoodModel;
import com.example.fitnessapplication.FitnessApp.Classes.FoodNutrients;
import com.example.fitnessapplication.FitnessApp.Classes.MySingleton;
import com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq.CaloriesAndMacrosDataService;
import com.example.fitnessapplication.FitnessApp.UsersActivities.SearchAndAddFood.FoodDataService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityDataService {
    Context context;

    public ActivityDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(List<ActivityModel> listOfActivities);
    }

    public void getListOfActivities(int intensityLevel, VolleyResponseListener volleyResponseListener){
        String url = "https://fitness-calculator.p.rapidapi.com/activities?intensitylevel=" + intensityLevel;
        List<ActivityModel> listOfActivities = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            for(int i=0; i<data.length(); i++){
                                JSONObject activity = (JSONObject) data.get(i);
                                ActivityModel activityModel = new ActivityModel();
                                activityModel.setId(activity.getString("id"));
                                activityModel.setActivityTitle(activity.getString("activity"));
                                activityModel.setMetValue(activity.getInt("metValue"));
                                activityModel.setDescription(activity.getString("description"));
                                activityModel.setIntensityLevel(activity.getInt("intensityLevel"));
                                listOfActivities.add(activityModel);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(listOfActivities);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        volleyResponseListener.onError("Something wrong!");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-rapidapi-host", "fitness-calculator.p.rapidapi.com");
                params.put("x-rapidapi-key", "77d3a01ee9msh48590f165204cadp191ad0jsn682012a648d9");

                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void getAllListsOfActivities(VolleyResponseListener volleyResponseListener){
        List<ActivityModel> listOfActivities = new ArrayList<>();
        for(int i=1; i<=7; i++){
            String url = "https://fitness-calculator.p.rapidapi.com/activities?intensitylevel=" + i;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET,  url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray data = response.getJSONArray("data");
                                for(int i=0; i<data.length(); i++){
                                    JSONObject activity = (JSONObject) data.get(i);
                                    ActivityModel activityModel = new ActivityModel();
                                    activityModel.setId(activity.getString("id"));
                                    activityModel.setActivityTitle(activity.getString("activity"));
                                    activityModel.setMetValue(activity.getInt("metValue"));
                                    activityModel.setDescription(activity.getString("description"));
                                    activityModel.setIntensityLevel(activity.getInt("intensityLevel"));
                                    listOfActivities.add(activityModel);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            volleyResponseListener.onResponse(listOfActivities);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            volleyResponseListener.onError("Something wrong!");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("x-rapidapi-host", "fitness-calculator.p.rapidapi.com");
                    params.put("x-rapidapi-key", "77d3a01ee9msh48590f165204cadp191ad0jsn682012a648d9");

                    return params;
                }
            };
            MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        }
    }


    public interface VolleyResponseListenerForConsumedCal {
        void onError(String message);
        void onResponse(String burnedCal, String unit);
    }

    public void getBurnedCaloriesFromActivity(String id, int activityMin, int weight, VolleyResponseListenerForConsumedCal volleyResponseListener){
        String url = "https://fitness-calculator.p.rapidapi.com/burnedcalorie?activityid=" + id + "&activitymin=" + activityMin + "&weight=" + weight;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  url, null, new Response.Listener<JSONObject>() {
                    String burnedCal, unit;

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            burnedCal = data.getString("burnedCalorie");
                            unit = data.getString("unit");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(burnedCal, unit);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        volleyResponseListener.onError("Something wrong!");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-rapidapi-host", "fitness-calculator.p.rapidapi.com");
                params.put("x-rapidapi-key", "77d3a01ee9msh48590f165204cadp191ad0jsn682012a648d9");

                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
