package com.example.weather_app;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;
public  class WeatherData {
    @SerializedName("name")
    private String cityName;

    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private List<Weather> weather;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getTemperature() {
        if (main != null) {
            Log.println(Log.INFO,"m", String.valueOf(main));
            return main.getTemp();
        }
        return 0; // Handle the case where main is null
    }

    public int getHumidity() {
        if (main != null) {
            Log.println(Log.INFO,"m", String.valueOf(main));
            return main.getHum();
        }
        return 0; // Handle the case where main is null
    }

    public String getWeatherDescription() {
        if (weather != null && !weather.isEmpty()) {
            return weather.get(0).getDescription();
        }
        return ""; // Handle the case where weather is null or empty
    }

    // Inner classes for serialization
    private static class Main {
        @SerializedName("temp")
        private double temp;
        @SerializedName("humidity")
        private int hum;

        public double getTemp() {
            return temp;
        }
        public int getHum() {
            return hum;
        }
    }

    private static class Weather {
        @SerializedName("description")
        private String description;

        public String getDescription() {
            return description;
        }
    }
}

