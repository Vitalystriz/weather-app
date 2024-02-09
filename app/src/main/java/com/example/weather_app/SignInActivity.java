package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail, etPassword;
    Button submit;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        submit = (Button) findViewById(R.id.btnSubmit);
        submit.setOnClickListener(this);

        mAuth= FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        String email = etEmail.getText().toString();
        String password = etEmail.getText().toString();
        Intent intent = getIntent();
        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "Successfully login", Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(SignInActivity.this, "login Error", Toast.LENGTH_LONG).show();

                }


            }

        });

    }
}