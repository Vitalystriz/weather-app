package com.example.weather_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CityAdapter extends ArrayAdapter<Saved_data> {
    Context context;
    List<Saved_data> objects;

    public CityAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Saved_data> objects) {
        super(context, resource, textViewResourceId, objects);
        this.objects = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.custom_layout, parent, false);
        TextView city = view.findViewById(R.id.tvTitle); // Corrected this line
        Saved_data p = objects.get(position);
        city.setText(p.getCity());
        return view; // Corrected this line
    }
}
