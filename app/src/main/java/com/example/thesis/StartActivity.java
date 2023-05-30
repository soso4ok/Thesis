package com.example.thesis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button goToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        goToLogin = findViewById(R.id.go_to_login);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        SharedPreferences preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        final String userEmailData = preferences.getString("Login", "");
        final String userPasswordData = preferences.getString("Password", "");

        if (firebaseUser != null || !userEmailData.equals("") || !userPasswordData.equals("")) {
            // User is already signed in, so go to home activity
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        goToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }
}