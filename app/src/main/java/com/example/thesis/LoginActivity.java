package com.example.thesis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    // Connerct Firebase
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://thesis-53800-default-rtdb.europe-west1.firebasedatabase.app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText login = findViewById(R.id.OOOOOOOOOOOOO);
        final EditText password = findViewById(R.id.editTextTextPassword);
        final Button login_btn = findViewById(R.id.login_btn);
        final Button sing_up = findViewById(R.id.singup_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    final String loginEnd = login.getText().toString();
                final String passwordEnd = password.getText().toString();

                if (passwordEnd.isEmpty() || loginEnd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your Email or Password", Toast.LENGTH_LONG).show();
                } else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Check if Email is exist in firebase database
                            if (snapshot.hasChild(loginEnd)) {

                                final String getPassword = snapshot.child(loginEnd).child("passwords").getValue(String.class);

                                if (getPassword.equals(passwordEnd)) {
                                    Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
                                    openMainActivity();
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }


            }
        });

        sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}