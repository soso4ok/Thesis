package com.example.thesis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    // Connect Firebase
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://thesis-53800-default-rtdb.europe-west1.firebasedatabase.app");

    private Button registerBtn;
    private Button loginBtn;
    private EditText login;
    private EditText email;
    private EditText password;
    private EditText repeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerBtn = findViewById(R.id.register_btn);
        loginBtn = findViewById(R.id.backToLogin);

        login = findViewById(R.id.login);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        repeatPassword = findViewById(R.id.editTextTextPassword);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String loginData = login.getText().toString();
                String emailData = email.getText().toString();
                String passwordData = password.getText().toString();
                String passwordDataRep = repeatPassword.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; // regular expression pattern matches a string that represents an email address.
                String loginPattern = "^[^.#\\[\\]$]*$"; // regular expression to match input without '.', '#', '$', '[', or ']'

                // check if user fill all the fields before sending data to firebase
                if(loginData.isEmpty() || emailData.isEmpty() || passwordData.isEmpty() || passwordDataRep.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,  "Please fill all fields", Toast.LENGTH_LONG).show();
                } else if (!emailData.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid Email", Toast.LENGTH_LONG).show();
                } else if(!loginData.matches(loginPattern)) {
                    Toast.makeText(getApplicationContext(), "Login can't contain '.', '#', '$', '[', or ']'", Toast.LENGTH_LONG).show();
                }
                // check if passwords are matching with each other
                // if not matching with each other then show a |
                else if(!passwordData.equals(passwordDataRep)){
                    Toast.makeText(RegisterActivity.this,"Passwords are not matching", Toast.LENGTH_LONG).show();
                } else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(loginData)) {
                                Toast.makeText(RegisterActivity.this, "User is alredy registered!", Toast.LENGTH_LONG).show();
                            } else {
                                // Save the user's email and password in the Firebase Realtime Database
                                // and display a notification that the user has been successfully registered.
                                databaseReference.child("users").child(loginData).child("emails").setValue(emailData);
                                databaseReference.child("users").child(loginData).child("passwords").setValue(passwordData);
                                databaseReference.child("users").child(loginData).child("points").setValue("0");

                                Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_LONG).show();
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