package com.example.fitnessapplication.FitnessApp.UsersActivities.WaterConsumption;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.fitnessapplication.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import me.itangqi.waveloadingview.WaveLoadingView;

public class WaterConsumptionActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "My Notification";
    EditText edtConsumedWater;
    TextView tvConsumedWater, tvWaterLimit;
    Button btnAddWater;
    Button btn250ml, btn500ml, btn750ml;
    float mWater = 0;
    float totalWater = 2000;
    float waterCantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_consumption);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.water_consumption_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.changeWaterLimit) {
                    showBottomSheetDialog();
                }
                if(item.getItemId() == R.id.notification){
                    displayNotification();
                }
                return false;
            }
        });



        WaveLoadingView mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        mWaveLoadingView.setCenterTitleColor(Color.GRAY);
        mWaveLoadingView.setBottomTitleSize(18);
        mWaveLoadingView.setProgressValue(0);
        mWaveLoadingView.setBorderWidth(10);
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(Color.parseColor("#67c3ff"));
        mWaveLoadingView.setBorderColor(Color.WHITE);
        mWaveLoadingView.setTopTitleStrokeColor(Color.BLUE);
        mWaveLoadingView.setTopTitleStrokeWidth(3);
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();
        mWaveLoadingView.setBackgroundColor(Color.parseColor("#2c2c2c"));

        edtConsumedWater = findViewById(R.id.consumedWater);
        tvConsumedWater = findViewById(R.id.consumedWaterTv);
        tvConsumedWater.setText(String.valueOf(mWater));
        tvWaterLimit = findViewById(R.id.maxWaterTv);
        tvWaterLimit.setText(String.valueOf(totalWater));

        btnAddWater = findViewById(R.id.addWater);
        btnAddWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtConsumedWater.getText().toString().matches("")) {
                    Toast.makeText(WaterConsumptionActivity.this, "No input data for water consumed!", Toast.LENGTH_SHORT).show();
                }
                else {
                    float water = Float.parseFloat(edtConsumedWater.getText().toString());
                    addWater(water, mWaveLoadingView);
                }
            }
        });

        btn250ml = findViewById(R.id.ml250);
        btn250ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWater(250, mWaveLoadingView);
            }
        });
        btn500ml = findViewById(R.id.ml500);
        btn500ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWater(500, mWaveLoadingView);
            }
        });
        btn750ml = findViewById(R.id.ml750);
        btn750ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWater(750, mWaveLoadingView);
            }
        });

    }

    private float calculatePercentage(float water) {
        float percent = (100 * water) / totalWater;
        return percent;
    }

    private void changeWaveLoadingViewData(WaveLoadingView mWaveLoadingView) {
        mWaveLoadingView.setProgressValue((int) mWater);
        if(mWater < 50){
            mWaveLoadingView.setBottomTitle(String.format("%.2f%%", mWater));
            mWaveLoadingView.setCenterTitle("");
            mWaveLoadingView.setTopTitle("");
        }
        else if(mWater < 80){
            mWaveLoadingView.setBottomTitle("");
            mWaveLoadingView.setCenterTitle(String.format("%.2f%%", mWater));
            mWaveLoadingView.setTopTitle("");
        }
        else {
            mWaveLoadingView.setBottomTitle("");
            mWaveLoadingView.setCenterTitle("");
            mWaveLoadingView.setTopTitle(String.format("%.2f%%", mWater));
        }
    }


    private void addWater(float water, WaveLoadingView mWaveLoadingView) {
        float percentage = calculatePercentage(water);
        waterCantity += water;
        mWater = mWater + percentage;
        changeWaveLoadingViewData(mWaveLoadingView);
        tvConsumedWater.setText(String.valueOf(waterCantity));
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout_change_water_limit);

        EditText edtWaterLimit = bottomSheetDialog.findViewById(R.id.waterLimit);

        Button btnChangeWaterLimit = bottomSheetDialog.findViewById(R.id.setWaterLimit);
        btnChangeWaterLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalWater = Float.parseFloat(edtWaterLimit.getText().toString());
                bottomSheetDialog.dismiss();
                tvWaterLimit.setText(String.valueOf(totalWater));
            }
        });

        bottomSheetDialog.show();
    }

    public void displayNotification(){
        createNotificationChannel();
        RemoteViews normalLayout = new RemoteViews(getPackageName(), R.layout.drink_water_notification_normal);
        RemoteViews expandedLayout = new RemoteViews(getPackageName(), R.layout.drink_water_notification_expanded);

        Intent waterConsumptionIntent = new Intent(this, WaterConsumptionActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(waterConsumptionIntent);
        PendingIntent addWaterIntent =
                stackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),30000, addWaterIntent);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.glass)
                .setCustomContentView(normalLayout).
                setCustomBigContentView(expandedLayout)
                .addAction(R.drawable.ic_add_water, "Confirm", addWaterIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(addWaterIntent);



        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Notification", importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}