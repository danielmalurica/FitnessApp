package com.example.fitnessapplication.FitnessApp.UsersActivities.Charts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq.MacrosClass;
import com.example.fitnessapplication.FitnessApp.UsersActivities.HomePage.UserHomepageActivity;
import com.example.fitnessapplication.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EvolutionByMonth extends AppCompatActivity {

    TextView tvCurrentCal, tvCurrentProtein, tvCurrentFat, tvCurrentCarbs;
    TextView tvTargetCalories, tvTargetProtein, tvTargetFat, tvTargetCarbs, tvBurnedCal, tvConsumedCal;
    CircularProgressIndicator cpiCal;
    LinearProgressIndicator lpiProtein;
    LinearProgressIndicator lpiFat;
    LinearProgressIndicator lpiCarbs;

    private DatabaseReference mDatabase;
    MacrosClass macrosClass;
    private PieChart pieChart;
    DayScrollDatePicker dayScrollDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evolution_by_month);

        pieChart = findViewById(R.id.pieChart);
        pieChart.setVisibility(View.INVISIBLE);

        setupPieChart();

        ArrayList<PieEntry> entries = new ArrayList<>();



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        macrosClass = new MacrosClass(0, 0, 0);

        tvTargetCalories = findViewById(R.id.targetCalories);
        tvTargetProtein = findViewById(R.id.proteinTarget);
        tvTargetFat = findViewById(R.id.fatTarget);
        tvTargetCarbs = findViewById(R.id.carbsTarget);
        tvBurnedCal = findViewById(R.id.burnedCal);
        tvConsumedCal = findViewById(R.id.consumedCal);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("caloriesAndMacros").child(user.getUid()).child("goals").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(EvolutionByMonth.this, "Error!", Toast.LENGTH_SHORT).show();
                }
                else {
                    tvTargetCalories.setText(task.getResult().child("calories").getValue().toString());
                    tvTargetProtein.setText(task.getResult().child("proteins").getValue().toString());
                    tvTargetFat.setText(task.getResult().child("fat").getValue().toString());
                    tvTargetCarbs.setText(task.getResult().child("carbs").getValue().toString());
                }
            }
        });

        tvCurrentCal = findViewById(R.id.currentCal);
        tvCurrentProtein = findViewById(R.id.currentProtein);
        tvCurrentFat = findViewById(R.id.currentFat);
        tvCurrentCarbs = findViewById(R.id.currentCarbs);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Calendar mCalendar = Calendar.getInstance();

        mDatabase.child("caloriesAndMacros").child(user.getUid()).child("activities").child(currentDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    final DecimalFormat df = new DecimalFormat("0");
                    tvBurnedCal.setText(snapshot.child("burnedCalories").getValue().toString());

                }
                else {
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("activities").child(currentDate).child("burnedCalories").setValue(0);
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("activities").child(currentDate).child("unit").setValue("calorie");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").child(currentDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    final DecimalFormat df = new DecimalFormat("0.0");
                    double consumedCal = Double.parseDouble(snapshot.child("energy").getValue().toString());
                    double burned = Double.parseDouble(tvBurnedCal.getText().toString());
                    double diferenceCal = consumedCal - burned;
                    if(diferenceCal >= 0){
                        tvCurrentCal.setText(String.valueOf(df.format(diferenceCal)));
                    }
                    tvConsumedCal.setText(snapshot.child("energy").getValue().toString());
                    tvCurrentProtein.setText(snapshot.child("protein").getValue().toString());
                    tvCurrentFat.setText(snapshot.child("fat").getValue().toString());
                    tvCurrentCarbs.setText(snapshot.child("carbs").getValue().toString());
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


        lpiProtein = findViewById(R.id.proteinIndicator);
        setDataToLinearProgressIndicator(tvCurrentProtein, tvTargetProtein, lpiProtein);
        lpiFat = findViewById(R.id.fatIndicator);
        setDataToLinearProgressIndicator(tvCurrentFat, tvTargetFat, lpiFat);
        lpiCarbs = findViewById(R.id.carbsIndicator);
        setDataToLinearProgressIndicator(tvCurrentCarbs, tvTargetCarbs, lpiCarbs);
        cpiCal = findViewById(R.id.circularProgressIndicator);
        setDataToCircularProgressIndicator(tvCurrentCal, tvTargetCalories, cpiCal);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        dayScrollDatePicker = findViewById(R.id.dayDatePicker);
        dayScrollDatePicker.setStartDate(01, 01, 2022);
        dayScrollDatePicker.setEndDate(31,12,2022);
        dayScrollDatePicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@Nullable Date date) {
                if(date != null){
                    mDatabase.child("caloriesAndMacros").child(user.getUid()).child("consumption").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(sdf.format(date))){
                                double carbs, fat, protein;
                                Long comp = new Long(0);
                                if(snapshot.child(sdf.format(date)).child("carbs").getValue().equals(comp)){
                                    carbs = 0;
                                } else {
                                    carbs = (double) snapshot.child(sdf.format(date)).child("carbs").getValue();
                                }
                                if(snapshot.child(sdf.format(date)).child("fat").getValue().equals(comp)){
                                    fat = 0;
                                } else {
                                    fat = (double) snapshot.child(sdf.format(date)).child("fat").getValue();
                                }
                                if(snapshot.child(sdf.format(date)).child("protein").getValue().equals(comp)){
                                    protein = 0;
                                } else {
                                    protein = (double) snapshot.child(sdf.format(date)).child("protein").getValue();
                                }

                                double carbsCal = carbs * 4;
                                double fatCal = fat * 9;
                                double proteinCal = protein * 4;
                                double total = carbsCal + fatCal + proteinCal;
                                double percentCarbs = (double) carbsCal / total;
                                percentCarbs *= 100;
                                pieChart.setVisibility(View.VISIBLE);
                                entries.clear();
                                entries.add(new PieEntry((float) percentCarbs, "Carbs"));
                                double percentFat = (double) fatCal / total;
                                percentFat *= 100;
                                entries.add(new PieEntry((float) percentFat, "Fat"));
                                double percentProtein = (double) proteinCal / total;
                                percentProtein *= 100;
                                entries.add(new PieEntry((float) percentProtein, "Protein"));

                                ArrayList<Integer> colors = new ArrayList<>();
                                for(int color : ColorTemplate.MATERIAL_COLORS){
                                    colors.add(color);
                                }

                                for(int color : ColorTemplate.VORDIPLOM_COLORS){
                                    colors.add(color);
                                }

                                PieDataSet dataSet = new PieDataSet(entries, "Macro Nutrients");
                                dataSet.setColors(colors);
                                PieData data = new PieData(dataSet);
                                data.setDrawValues(true);
                                data.setValueFormatter(new PercentFormatter(pieChart));
                                data.setValueTextSize(12f);
                                data.setValueTextColor(Color.BLACK);
                                pieChart.setData(data);
                                pieChart.setCenterText(sdf.format(date));
                                pieChart.invalidate();
                                pieChart.animateY(1400, Easing.EaseInOutQuad);
                            } else {
                                Toast.makeText(EvolutionByMonth.this, "No data for " + sdf.format(date), Toast.LENGTH_SHORT).show();
                                pieChart.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }


    public void setDataToLinearProgressIndicator(TextView current, TextView target, LinearProgressIndicator linearProgressIndicator){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                double result = (Float.parseFloat(current.getText().toString())) / (Float.parseFloat(target.getText().toString()));
                result *= 100;
                linearProgressIndicator.setProgress((int) result);
            }
        }, 2000);
    }

    public void setDataToCircularProgressIndicator(TextView current, TextView target, CircularProgressIndicator circularProgressIndicator){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                double result = (Float.parseFloat(current.getText().toString())) / (Float.parseFloat(target.getText().toString()));
                result *= 100;
                circularProgressIndicator.setProgress((int) result);
            }
        }, 2000);
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }
}