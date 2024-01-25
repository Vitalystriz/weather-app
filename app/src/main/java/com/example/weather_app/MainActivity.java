package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Firebase mAuth;
    Button btnSignIn,btnSignUp,btnShow;
    private   TextView tvBattary;
    private   BatteryBroadcastReceiver batteryReceiver;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvBattary = findViewById(R.id.TvBattery);
        btnShow= findViewById(R.id.btnShow);
        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        batteryReceiver = new BatteryBroadcastReceiver(tvBattary);

        // Register the receiver with the intent filter for BATTERY_CHANGED broadcasts
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the receiver when the activity is destroyed
        unregisterReceiver(batteryReceiver);
    }


    @Override
    public void onClick(View view) {
        if(view==btnSignUp){
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);}
        if(view==btnSignIn){
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);}
        if(view==btnShow){
            Intent intent = new Intent(this,Major_activity.class);
            startActivity(intent);}
        }
    }
