package com.example.fitnessapplication.FitnessApp.Classes;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fitnessapplication.FitnessApp.UsersActivities.DailyCalAndMacroReq.MacrosClass;
import com.example.fitnessapplication.FitnessApp.UsersActivities.HomePage.UserHomepageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddToConsumption {

    long id = 0;

    public void addDataToConsumption(@NonNull DatabaseReference myRef, @NonNull FirebaseUser user, double energy, double carbs, double fat, double protein, String period, MacrosClass macrosClass, TextView tvFoodName, TextView tvEnergy, TextView tvProtein, TextView tvFat, TextView tvCarbs) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        myRef.child(user.getUid()).child("consumption").child(currentDate).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().exists()) {
                    double respEnergy = (double) task.getResult().child("energy").getValue(Double.class);
                    double respCarbs = (double) task.getResult().child("carbs").getValue(Double.class);
                    double respFat = (double) task.getResult().child("fat").getValue(Double.class);
                    double respProtein = (double) task.getResult().child("protein").getValue(Double.class);
                    myRef.child(user.getUid()).child("consumption").child(currentDate).child("energy").setValue(respEnergy + energy);
                    myRef.child(user.getUid()).child("consumption").child(currentDate).child("carbs").setValue(respCarbs + carbs);
                    myRef.child(user.getUid()).child("consumption").child(currentDate).child("fat").setValue(respFat + fat);
                    myRef.child(user.getUid()).child("consumption").child(currentDate).child("protein").setValue(respProtein + protein);

                } else {
                    myRef.child(user.getUid()).child("consumption").child(currentDate).setValue(macrosClass);
                    myRef.child(user.getUid()).child("consumption").child(currentDate).child("energy").setValue(energy);

                }

            }
        });

        myRef.child(user.getUid()).child("consumption").child(currentDate).child(period).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().exists()) {
                    id = task.getResult().getChildrenCount();
                }
                Map<String, String> values = new HashMap<>();
                values.put("foodName", tvFoodName.getText().toString());
                values.put("kcal", tvEnergy.getText().toString());
                values.put("protein", tvProtein.getText().toString());
                values.put("fat", tvFat.getText().toString());
                values.put("carbs", tvCarbs.getText().toString());
                myRef.child(user.getUid()).child("consumption").child(currentDate).child(period).child(String.valueOf(id)).setValue(values);
            }
        });

    }

}
