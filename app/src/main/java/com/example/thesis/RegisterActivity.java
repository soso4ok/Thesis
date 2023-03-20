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

public class RegisterActivity extends AppCompatActivity {

    // Connerct Firebase
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://thesis-53800-default-rtdb.europe-west1.firebasedatabase.app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button registerBtn = findViewById(R.id.register_btn);
        EditText login = findViewById(R.id.phoneNumber);
        final EditText email = findViewById(R.id.editTextTextEmailAddress);
        final EditText password = findViewById(R.id.editTextTextPassword);
        final EditText conPassword = findViewById(R.id.editTextTextPassword);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Button loginBtn = findViewById(R.id.backToLogin);
                final String loginEnd = login.getText().toString();
                final String emailEnd = email.getText().toString();
                final String passwordEnd = password.getText().toString();
                final String conPasswordEnd = conPassword.getText().toString();

                loginBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                });


                // check if user fill all the fields before sending data to firebase
                if(loginEnd.isEmpty() || emailEnd.isEmpty() || passwordEnd.isEmpty() || conPasswordEnd.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,  "Please fill all fields", Toast.LENGTH_LONG).show();
                }
                // check if passwords are matching with each other
                // if not matching with each other then show a |
                else if(!passwordEnd.equals(conPasswordEnd)){
                    Toast.makeText(RegisterActivity.this,"Passwords are not matching", Toast.LENGTH_LONG).show();
                } else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(loginEnd)) {

                                Toast.makeText(RegisterActivity.this, "User is alredy registered!", Toast.LENGTH_LONG).show();

                            } else {

                                // Save the user's email and password in the Firebase Realtime Database and display a notification that the user has been successfully registered.
                                databaseReference.child("users").child(loginEnd).child("emails").setValue(emailEnd);
                                databaseReference.child("users").child(loginEnd).child("passwords").setValue(passwordEnd);
                                Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_LONG).show();

                                onBackPressed();

                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }
}