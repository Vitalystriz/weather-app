package com.example.weather_app;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.BatteryManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;

public class BatteryBroadcastReceiver extends BroadcastReceiver {
    private TextView batteryLevelTextView;

    // Constructor to pass the TextView reference
    public BatteryBroadcastReceiver(TextView textView) {
        this.batteryLevelTextView = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            int batteryPercentage = (level /  scale) * 100;

            batteryLevelTextView.setTextColor(Color.GREEN);
            if (batteryPercentage<20){
                batteryLevelTextView.setTextColor(Color.YELLOW);
            }
            if (batteryPercentage<5){
                batteryLevelTextView.setTextColor(Color.RED);
            }
            // Display battery level in the TextView
            if (batteryLevelTextView != null) {
                batteryLevelTextView.setText( batteryPercentage + "%");
            }

            // You can also show a Toast if needed
            Toast.makeText(context, "Battery level: " + batteryPercentage + "%", Toast.LENGTH_SHORT).show();
        }
    }
}





