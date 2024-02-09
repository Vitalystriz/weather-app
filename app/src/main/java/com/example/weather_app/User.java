package com.example.weather_app;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String key;
    public String uid;//user uid
    public String email,firstname, lastname;
    public int age;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public User(String uid, String email, String firstname,String lastname, int age,String key) {
        this.uid = uid;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.key = key;
    }



}