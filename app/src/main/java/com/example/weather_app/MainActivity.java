package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Firebase mAuth;
    Button btnSignIn,btnSignUp,btnShow;
    private   TextView tvBattary;
    private   BatteryBroadcastReceiver batteryReceiver;
    private  Integer flag=0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate called");

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvBattary = findViewById(R.id.TvBattery);
        btnShow = findViewById(R.id.btnShow);

        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnShow.setOnClickListener(this);

        batteryReceiver = new BatteryBroadcastReceiver(tvBattary);

        FirebaseAuth.getInstance().signOut();
        // Register the receiver with the intent filter for BATTERY_CHANGED broadcasts
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
        flag++;
        //onUserSignedOut();
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//
//        if (isUserSignedIn()) {
//            onUserSignedIn();
//        } else {
//            onUserSignedOut();
//        }
//    }

    private boolean isUserSignedIn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser() != null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the receiver when the activity is destroyed
        unregisterReceiver(batteryReceiver);
    }


//    public void showRightButtons(){
//
//        btnSignIn.setVisibility(View.INVISIBLE);
//        btnSignUp.setVisibility(View.INVISIBLE);
//        btnShow.setVisibility(View.VISIBLE);
//
//
//    }
//    public void showTopButtons(){
//        btnSignIn.setVisibility(View.VISIBLE);
//        btnSignUp.setVisibility(View.VISIBLE);
//        btnShow.setVisibility(View.INVISIBLE);
//    }
//    private void updateButtonVisibility(boolean userSignedIn) {
//        if (userSignedIn) {
//            btnSignIn.setVisibility(View.INVISIBLE);
//            btnSignUp.setVisibility(View.INVISIBLE);
//            btnShow.setVisibility(View.VISIBLE);
//        } else {
//            btnSignIn.setVisibility(View.VISIBLE);
//            btnSignUp.setVisibility(View.VISIBLE);
//            btnShow.setVisibility(View.INVISIBLE);
//        }
//    }

    // Use this method when the user signs in
//    private void onUserSignedIn() {
//        updateButtonVisibility(true);
//    }
//
//    // Use this method when the user signs out
//    private void onUserSignedOut() {
//        updateButtonVisibility(false);
//    }

    @Override
    public void onClick(View view) {
        if (view == btnSignUp) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
        if (view == btnSignIn) {
            // Check if the user is signed in
            if (isUserSignedIn()) {
                // User is signed in, allow navigation to Major_activity
                Intent intent = new Intent(this, Major_activity.class);
                startActivity(intent);
            } else {
                // User is not signed in, direct them to the sign-in activity
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
            }
        }
        if (view == btnShow) {
            // Check if the user is signed in
            if (isUserSignedIn()) {

                // User is signed in, allow navigation to Major_activity
                Intent intent = new Intent(this, Major_activity.class);
                startActivity(intent);
            } else {
                // User is not signed in, direct them to the sign-in activity
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
            }
        }
    }
}
