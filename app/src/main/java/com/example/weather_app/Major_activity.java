package com.example.weather_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class Major_activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    EditText etCity;
    Button btnSearch;
    String city;
    private ArrayList<Saved_data> saved_data;
    CityAdapter cityAdapter;
    ListView lv;
    FirebaseAuth mAuth;
    DatabaseReference saved_dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        lv = findViewById(R.id.lv);
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        saved_dataRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

        etCity = findViewById(R.id.etCity);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        // Set item click listener for the ListView
        lv.setOnItemClickListener(this);

        // Retrieve data initially
        retrieveData();
    }

    private void retrieveData() {
        saved_data = new ArrayList<>();
        saved_dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                saved_data = getSavedData(dataSnapshot);
                Log.d("SavedData", saved_data.toString()); // Log the data for debugging

                cityAdapter = new CityAdapter(Major_activity.this, 0, 0, saved_data);
                lv.setAdapter(cityAdapter);
                cityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Major_activity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        // Start Weather_activity with the entered city
        startWeatherActivity(etCity.getText().toString());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Start Weather_activity with the selected city
        startWeatherActivity(saved_data.get(position).getCity());
    }

    private void startWeatherActivity(String city) {
        Intent intent = new Intent(this, Weather_activity.class);
        intent.putExtra("city", city);

        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public static ArrayList<Saved_data> getSavedData(DataSnapshot dataSnapshot) {
        ArrayList<Saved_data> savedData = new ArrayList<>();

        if (dataSnapshot != null && dataSnapshot.child("saved_data").exists()) {
            DataSnapshot savedDataSnapshot = dataSnapshot.child("saved_data");

            for (DataSnapshot postSnapshot : savedDataSnapshot.getChildren()) {
                Saved_data saved_data1 = postSnapshot.getValue(Saved_data.class);
                if (saved_data1 != null) {
                    savedData.add(saved_data1);
                }
            }
        }

        return savedData;
    }



}
