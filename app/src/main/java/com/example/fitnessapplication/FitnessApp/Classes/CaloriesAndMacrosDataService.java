package com.example.fitnessapplication.FitnessApp.Classes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaloriesAndMacrosDataService {
    Context context;

    public CaloriesAndMacrosDataService(Context context) {
        this.context = context;
    }



    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(JSONObject object);
    }

    public void getDataForGoal(int age, String gender, double height, double weight, String activityLevel, VolleyResponseListener volleyResponseListener){
        String url = "https://fitness-calculator.p.rapidapi.com/dailycalorie?age=" + age + "&gender=" + gender + "&height=" + height + "&weight=" + weight
                + "&activitylevel=" + activityLevel;

        CaloriesRequirements requirements = new CaloriesRequirements();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            volleyResponseListener.onResponse(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error:" +error.toString(), Toast.LENGTH_SHORT).show();
                        volleyResponseListener.onError(error.toString());
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

    public void calculateCalories(int age, String gender, double height, double weight, String activityLevel, VolleyResponseListener volleyResponseListener){
        String url = "https://fitness-calculator.p.rapidapi.com/dailycalorie?age=" + age + "&gender=" + gender + "&height=" + height + "&weight=" + weight
                + "&activitylevel=" + activityLevel;

        CaloriesRequirements requirements = new CaloriesRequirements();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            volleyResponseListener.onResponse(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(context,
                                    "Timeout",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(context, "AuthFailureError", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(context, "Parse error", Toast.LENGTH_SHORT).show();
                        }
                        volleyResponseListener.onError(error.toString());
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

    public void calculateMacros(int age, String gender, double height, double weight, int activityLevel, String goal, VolleyResponseListener volleyResponseListener){
        String url = "https://fitness-calculator.p.rapidapi.com/macrocalculator?age=" + age
                +"&gender="+gender
                +"&height="+height
                +"&weight="+weight
                +"&activitylevel="+activityLevel
                +"&goal="+goal;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            volleyResponseListener.onResponse(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error:" +error.toString(), Toast.LENGTH_SHORT).show();
                        volleyResponseListener.onError(error.toString());
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
