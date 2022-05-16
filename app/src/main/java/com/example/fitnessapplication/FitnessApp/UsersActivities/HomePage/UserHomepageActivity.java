package com.example.fitnessapplication.FitnessApp.UsersActivities.HomePage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.UsersActivities.BMI.BmiUserDataActivity;
import com.example.fitnessapplication.FitnessApp.UsersActivities.Charts.EvolutionByMonth;
import com.example.fitnessapplication.FitnessApp.UsersActivities.ConsumedCalories.ListOfActivities;
import com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq.ListOfActivititiesActivity;
import com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq.MacrosClass;
import com.example.fitnessapplication.FitnessApp.UsersActivities.GenerateMealPlan.MealPlanGenerator;
import com.example.fitnessapplication.FitnessApp.UsersActivities.SearchAndAddFood.FoodDetailsActivity;
import com.example.fitnessapplication.FitnessApp.UsersActivities.SearchAndAddFood.SearchFoodActivity;
import com.example.fitnessapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserHomepageActivity extends AppCompatActivity {

    Button buttonToBmi, btnDailyCal, btnToActivities, btnToChart, btnToGenPlan;
    Button btnAddFoodToBreakfast, btnAddFoodToLunch, btnAddFoodToDinner, btnAddFoodToSnack;
    ListView lvBreakfast, lvLunch, lvDinner, lvSnack;
    ArrayList<FoodList> breakfastList, lunchList, dinnerList, snackList;
    FoodListAdapter foodListAdapter;
    private DatabaseReference mDatabase, myRef;
    int number = 0;
    TextView tvProteinBreakfast, tvFatBreakfast, tvCarbsBreakfast, tvTotalkCal;
    TextView tvProteinLunch, tvFatLunch, tvCarbsLunch, tvTotalkCalLunch;
    TextView tvProteinDinner, tvFatDinner, tvCarbsDinner, tvTotalkCalDinner;
    TextView tvProteinSnack, tvFatSnack, tvCarbsSnack, tvTotalkCalSnack;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").child(currentDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                }
                else {
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").child(currentDate).child("energy").setValue(0);
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").child(currentDate).child("carbs").setValue(0);
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").child(currentDate).child("fat").setValue(0);
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").child(currentDate).child("protein").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnAddFoodToBreakfast = findViewById(R.id.addFoodBreakfast);
        btnAddFoodToBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepageActivity.this, SearchFoodActivity.class);
                intent.putExtra("PERIOD_OF_DAY", "breakfast");
                startActivity(intent);
                finish();
            }
        });

        myRef = FirebaseDatabase.getInstance().getReference("caloriesAndMacros");
        lvBreakfast = findViewById(R.id.breakfastList);
        breakfastList = new ArrayList<FoodList>();
        tvProteinBreakfast = findViewById(R.id.proteinBreakfast);
        tvFatBreakfast = findViewById(R.id.fatBreakfast);
        tvCarbsBreakfast = findViewById(R.id.carbsBreakfast);
        tvTotalkCal = findViewById(R.id.kCalBreakfast);

        searchFoodInDatabase(user, currentDate, "breakfast", tvProteinBreakfast, tvFatBreakfast, tvCarbsBreakfast, tvTotalkCal, breakfastList,lvBreakfast);

        btnAddFoodToLunch = findViewById(R.id.addFoodLunch);
        btnAddFoodToLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepageActivity.this, SearchFoodActivity.class);
                intent.putExtra("PERIOD_OF_DAY", "lunch");
                startActivity(intent);
                finish();
            }
        });
        lvLunch = findViewById(R.id.lunchList);
        lunchList = new ArrayList<FoodList>();
        tvProteinLunch = findViewById(R.id.proteinLunch);
        tvFatLunch= findViewById(R.id.fatLunch);
        tvCarbsLunch = findViewById(R.id.carbsLunch);
        tvTotalkCalLunch = findViewById(R.id.kCalLunch);
        searchFoodInDatabase(user, currentDate, "lunch", tvProteinLunch, tvFatLunch, tvCarbsLunch, tvTotalkCalLunch, lunchList, lvLunch);

        btnAddFoodToDinner = findViewById(R.id.addFoodDinner);
        btnAddFoodToDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepageActivity.this, SearchFoodActivity.class);
                intent.putExtra("PERIOD_OF_DAY", "dinner");
                startActivity(intent);
                finish();
            }
        });
        lvDinner = findViewById(R.id.dinnerList);
        dinnerList = new ArrayList<FoodList>();
        tvProteinDinner = findViewById(R.id.proteinDinner);
        tvFatDinner= findViewById(R.id.fatDinner);
        tvCarbsDinner = findViewById(R.id.carbsDinner);
        tvTotalkCalDinner = findViewById(R.id.kCalDinner);
        searchFoodInDatabase(user, currentDate, "dinner", tvProteinDinner, tvFatDinner, tvCarbsDinner, tvTotalkCalDinner, dinnerList, lvDinner);

        btnAddFoodToSnack = findViewById(R.id.addFoodSnack);
        btnAddFoodToSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepageActivity.this, SearchFoodActivity.class);
                intent.putExtra("PERIOD_OF_DAY", "snack");
                startActivity(intent);
                finish();
            }
        });
        lvSnack = findViewById(R.id.snackList);
        snackList = new ArrayList<FoodList>();
        tvProteinSnack = findViewById(R.id.proteinSnack);
        tvFatSnack= findViewById(R.id.fatSnack);
        tvCarbsSnack = findViewById(R.id.carbsSnack);
        tvTotalkCalSnack = findViewById(R.id.kCalSnack);
        searchFoodInDatabase(user, currentDate, "snack", tvProteinSnack, tvFatSnack, tvCarbsSnack, tvTotalkCalSnack, snackList, lvSnack);



        buttonToBmi = findViewById(R.id.btnToBmi);
        buttonToBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BmiUserDataActivity.class));
            }
        });


        btnDailyCal = findViewById(R.id.toDailyCal);
        btnDailyCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListOfActivititiesActivity.class));
            }
        });

        btnToActivities = findViewById(R.id.btnToActivities);
        btnToActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListOfActivities.class));
            }
        });

        btnToChart = findViewById(R.id.btnToChart);
        btnToChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EvolutionByMonth.class);
                startActivity(intent);
            }
        });

        btnToGenPlan = findViewById(R.id.btnToMealGen);
        btnToGenPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealPlanGenerator.class);
                startActivity(intent);
            }
        });
    }

    public void setListViewHeight(ListView listView) {
        ArrayAdapter listAdapter = (ArrayAdapter) listView.getAdapter();
        if (listAdapter != null) {
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }

    private void searchFoodInDatabase(FirebaseUser user, String currentDate, String period, TextView tvProtein, TextView tvFat, TextView tvCarbs, TextView tvTotalkCal, ArrayList<FoodList> list ,ListView listView){
        myRef.child(user.getUid()).child("consumption").child(currentDate).child(period).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists()){
                    number = (int)task.getResult().getChildrenCount();
                    double totalProtein = 0, totalFat = 0, totalCarbs = 0, totalkCal = 0;
                    for(int i=0; i< number; i++){
                        totalProtein += Double.parseDouble(task.getResult().child(String.valueOf(i)).child("protein").getValue().toString());
                        tvProtein.setText(String.valueOf(df.format(totalProtein)));
                        totalFat += Double.parseDouble(task.getResult().child(String.valueOf(i)).child("fat").getValue().toString());
                        tvFat.setText(String.valueOf(df.format(totalFat)));
                        totalCarbs += Double.parseDouble(task.getResult().child(String.valueOf(i)).child("carbs").getValue().toString());
                        tvCarbs.setText(String.valueOf(df.format(totalCarbs)));
                        totalkCal += Double.parseDouble(task.getResult().child(String.valueOf(i)).child("kcal").getValue().toString());
                        tvTotalkCal.setText(String.valueOf(df.format(totalkCal)));
                        FoodList foodList = new FoodList();
                        foodList.setFoodName(task.getResult().child(String.valueOf(i)).child("foodName").getValue().toString());
                        foodList.setCal(task.getResult().child(String.valueOf(i)).child("kcal").getValue().toString());
                        list.add(foodList);
                    }
                    foodListAdapter = new FoodListAdapter(getApplicationContext(), list);
                    listView.setAdapter(foodListAdapter);
                }
                else {
                    listView.setAdapter(null);
                }
                setListViewHeight(listView);
            }
        });

    }

}