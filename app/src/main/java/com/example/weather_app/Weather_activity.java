package com.example.weather_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Weather_activity extends AppCompatActivity implements View.OnClickListener {
    private TextView cityNameTextView, timeTextView;
    private TextView temperatureTextView;
    private TextView weatherDescriptionTextView;
    private TextView humidityTextView;
    private ImageView weatherIconImageView;
    private Button btnBack;
    private String LastCity="";
    private OpenWeatherMapService openWeatherMapService;
    private String apiKey = "92a7752dfaa07aa38a243960a294086c";
    private String unit="metric";
    Saved_data Sd;
    FirebaseAuth mAuth;
    DatabaseReference PostRef;
    WeatherData weatherData;
    private String city;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mAuth= FirebaseAuth.getInstance();
        findView();

        Intent intent = getIntent();
        if (intent != null) {
            // Update the class-level variable with the value from the Intent
            city = intent.getStringExtra("city");
            cityNameTextView.setText(city);
        } else {
            Log.d("sfs", "error");
        }

        // Remove this line, as it sets the text with the local variable (which is null)
        // cityNameTextView.setText(city);

//        cityNameTextView.setText(city);

        // weatherIconImageView = findViewById(R.id.weather_icon_image_view);
        setDataTime(timeTextView);
        openWeatherMapService = ApiClient.getInstance().create(OpenWeatherMapService.class);
        btnBack.setOnClickListener(this);
        loadWeatherData(city);
    }

    private void findView() {
        cityNameTextView = findViewById(R.id.TvCity);
        btnBack = findViewById(R.id.btnBack);
        temperatureTextView = findViewById(R.id.TvTemp);
        timeTextView = findViewById(R.id.TvTime);
        weatherDescriptionTextView = findViewById(R.id.TvDesc);
        humidityTextView = findViewById(R.id.TvHum);
        cityNameTextView = findViewById(R.id.TvCity);
        cityNameTextView = findViewById(R.id.TvCity); // Assuming this is the TextView for city name
        weatherIconImageView= findViewById(R.id.IvMain);
    }

    private void setDataTime(TextView timeTextView) {
        Date currentDate = new Date();

        // Format the date and time using SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDateTime = sdf.format(currentDate);
        timeTextView.setText(formattedDateTime);

    }

    private void loadWeatherData(String location) {

        openWeatherMapService.getCurrentWeatherData(location, apiKey,unit).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful()) {
                     weatherData = response.body();
                    if (weatherData != null) {
                        Log.d("MainActivity", "WeatherData JSON: " + new Gson().toJson(weatherData));
                        updateUI(weatherData);
                    } else {
                        showErrorToast();
                    }
                } else {
                    showErrorToast();
                }
            }


            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                showErrorToast();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateUI(WeatherData weatherData) {
        Log.d("MainActivity",weatherData.getCityName());
        cityNameTextView.setText(weatherData.getCityName());
        temperatureTextView.setText(String.valueOf(weatherData.getTemperature())); // Convert to String
        weatherDescriptionTextView.setText(String.valueOf(weatherData.getWeatherDescription()));
        humidityTextView.setText(String.valueOf(weatherData.getHumidity())); // Convert to String
        int imageResource = R.drawable.sunnycloudlyrainy;
        if(weatherData.getWeatherDescription().contains("lightning"))
            imageResource = R.drawable.lightning;
        if(weatherData.getWeatherDescription().contains("rain")){
            imageResource = R.drawable.rainy;
        }
        if(weatherData.getWeatherDescription().contains("clou")){
            imageResource = R.drawable.cloudly;
        }
        if(weatherData.getWeatherDescription().contains("sun")){
            imageResource = R.drawable.sunny;
        }
        weatherIconImageView.setImageResource(imageResource);



        // Load weather icon using a library like Picasso or Glide
    }

//    private void insertInDb(WeatherData weatherData) {
//
//        Sd = new Saved_data();
//        Sd.setCity(weatherData.getCityName());
//        DatabaseReference SdRef = FirebaseDatabase.getInstance().getReference("saved_data");
//        // Push the new post to Firebase, which generates a unique key
//        DatabaseReference newSdRef = SdRef.push();
//        // Set the value of the new post using the Post object
//        newSdRef.setValue(Sd).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    // Post saved successfully
//                    // You can add any additional logic here
//                } else {
//                    // Handle the error
//                }
//            }
//        });
//        finish();
//    }
//    private void insertInDb(WeatherData weatherData) {
//    String cityName = weatherData.getCityName();
//    DatabaseReference saved_dataRef = FirebaseDatabase.getInstance().getReference("saved_data");
//
//    // Check if the city already exists in the database
//    saved_dataRef.orderByChild("city").equalTo(cityName).addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            if (dataSnapshot.exists()) {
//                // City already exists, handle accordingly (show a message, etc.)
//                Toast.makeText(Weather_activity.this, "City already exists in the database", Toast.LENGTH_SHORT).show();
//            } else {
//                // City doesn't exist, proceed with inserting
//                Saved_data sd = new Saved_data();
//                sd.setCity(cityName);
//
//                DatabaseReference newSdRef = saved_dataRef.push();
//                newSdRef.setValue(sd).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            // Post saved successfully
//                            // You can add any additional logic here
//                            finish();
//                        } else {
//                            // Handle the error
//                            Toast.makeText(Weather_activity.this, "Failed to save city to the database", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//            Toast.makeText(Weather_activity.this, "Failed to save city to the database", Toast.LENGTH_SHORT).show();
//        }
//    });
//}
        private void insertInDb(WeatherData weatherData) {
            // Get the current user UID
            String uid = mAuth.getCurrentUser().getUid();

            // Reference to the user's data node in the database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

            // Check if the city already exists for the user
            userRef.child("saved_data").orderByChild("city").equalTo(weatherData.getCityName())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // City already exists for the user, handle accordingly
                                Toast.makeText(Weather_activity.this, "City already exists in the user's database", Toast.LENGTH_SHORT).show();
                            } else {
                                // City doesn't exist, proceed with inserting
                                Saved_data sd = new Saved_data();
                                sd.setCity(weatherData.getCityName());

                                DatabaseReference newSdRef = userRef.child("saved_data").push();
                                newSdRef.setValue(sd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Data saved successfully for the user
                                            // You can add any additional logic here
                                            finish();
                                        } else {
                                            // Handle the error
                                            Toast.makeText(Weather_activity.this, "Failed to save city to the user's database", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Weather_activity.this, "Failed to save city to the user's database", Toast.LENGTH_SHORT).show();
                        }
                    });
        }





    private void showErrorToast() {
        Toast.makeText(Weather_activity.this, "Failed to load weather data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        DataSnapshot dataSnapshot = null;
        ArrayList<Saved_data> saved_data = Major_activity.getSavedData(dataSnapshot);
        if (LastCity != weatherData.getCityName()) {
            insertInDb(weatherData);
        }
        LastCity = weatherData.getCityName();
        finish();
    }
}
