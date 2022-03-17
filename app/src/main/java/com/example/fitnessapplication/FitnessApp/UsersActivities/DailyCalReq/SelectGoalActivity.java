package com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalReq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.Classes.CaloriesDataService;
import com.example.fitnessapplication.FitnessApp.Classes.CaloriesRequirements;
import com.example.fitnessapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class SelectGoalActivity extends AppCompatActivity {

    TextView tvBmr;
    RadioButton rdMaintain, rdMildWeightLoss, rdWeightLoss, rdExtremeWeightLoss,
            rdMildWeightGain, rdWeightGain, rdExtremeWeightGain;

    List<Integer> calories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_goal);

        tvBmr = findViewById(R.id.bmr);
        rdMaintain = findViewById(R.id.maintaintWeight);
        rdMildWeightLoss = findViewById(R.id.mildWeightLoss);
        rdWeightLoss = findViewById(R.id.weightLoss);
        rdExtremeWeightLoss = findViewById(R.id.extremeWeightLoss);
        rdMildWeightGain = findViewById(R.id.mildWeightGain);
        rdWeightGain = findViewById(R.id.weightGain);
        rdExtremeWeightGain = findViewById(R.id.extremeWeightGain);

        Intent intent = getIntent();
        int userAge = intent.getIntExtra("USER_AGE", 0);
        String userGender = intent.getStringExtra("USER_GENDER");
        int userHeight = intent.getIntExtra("USER_HEIGHT", 0);
        int userWeight = intent.getIntExtra("USER_WEIGHT", 0);
        String userActivityLevel = intent.getStringExtra("ACTIVITY_LEVEL");

        Log.i("Extras", userAge+"/"+userGender.toLowerCase(Locale.ROOT)+"/"+userHeight+"/"+userWeight+"/"+userActivityLevel);

        CaloriesDataService caloriesDataService = new CaloriesDataService(SelectGoalActivity.this);

        caloriesDataService.calculateCalories(userAge, userGender.toLowerCase(Locale.ROOT),userHeight,userWeight,userActivityLevel, new CaloriesDataService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(SelectGoalActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject object) {
                try {
                    String bmr = object.getString("BMR");
                    tvBmr.setText(bmr);
                    JSONObject goals = object.getJSONObject("goals");
                    String maintainWeight = goals.getString("maintain weight");
                    JSONObject mwlObject = goals.getJSONObject("Mild weight loss");
                    rdMildWeightLoss.append(" -" + mwlObject.getString("loss weight") + "/week");
                    JSONObject wlObject = goals.getJSONObject("Weight loss");
                    rdWeightLoss.append(" -" + wlObject.getString("loss weight") + " /week");
                    JSONObject ewlObject = goals.getJSONObject("Extreme weight loss");
                    rdExtremeWeightLoss.append(" -" + ewlObject.getString("loss weight") + " /week");
                    JSONObject mwgObject = goals.getJSONObject("Mild weight gain");
                    rdMildWeightGain.append(" +" + mwgObject.getString("gain weight") + " /week");
                    JSONObject wgObject = goals.getJSONObject("Weight gain");
                    rdWeightGain.append(" +" + wgObject.getString("gain weight") + " /week");
                    JSONObject ewgObject = goals.getJSONObject("Extreme weight gain");
                    rdExtremeWeightGain.append(" +" + ewgObject.getString("gain weight") + " /week");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}