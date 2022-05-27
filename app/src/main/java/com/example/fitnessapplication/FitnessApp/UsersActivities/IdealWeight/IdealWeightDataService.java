package com.example.fitnessapplication.FitnessApp.UsersActivities.IdealWeight;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fitnessapplication.FitnessApp.Classes.MySingleton;
import com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq.CaloriesAndMacrosDataService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IdealWeightDataService {

    Context context;

    public IdealWeightDataService(Context context) {
        this.context = context;
    }



    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(IdealWeight idealWeight);
    }
    public void calculateIdealWeight(String gender, int height, IdealWeightDataService.VolleyResponseListener volleyResponseListener){
        String url = "https://fitness-calculator.p.rapidapi.com/idealweight?gender=" + gender + "&height=" + height;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            IdealWeight idealWeight = new IdealWeight(data.getDouble("Robinson"), data.getDouble("Hamwi"));
                            volleyResponseListener.onResponse(idealWeight);
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
