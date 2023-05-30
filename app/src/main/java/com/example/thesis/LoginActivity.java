package com.example.thesis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thesis.fragments.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText userLogin;
    private EditText userPassword;
    private Button loginBtn;
    private Button singupBtn;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://thesis-53800-default-rtdb.europe-west1.firebasedatabase.app");
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLogin = findViewById(R.id.loginPlane);
        userPassword = findViewById(R.id.passwordPlane);
        loginBtn = findViewById(R.id.login_btn);
        singupBtn = findViewById(R.id.singup_btn);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        SharedPreferences preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        final String userEmailData = preferences.getString("Login", "");
        final String userPasswordData = preferences.getString("Password", "");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Login", userLogin.getText().toString());
                editor.putString("Password", userPassword.getText().toString());
                editor.apply();

                final String userLoginData = userLogin.getText().toString();
                final String userPasswordData = userPassword.getText().toString();

                String loginPattern = "^[^.#\\[\\]$]*$"; // regular expression to match input without '.', '#', '$', '[', or ']'

                if (userPasswordData.isEmpty() || userLoginData.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your Email or Password", Toast.LENGTH_LONG).show();
                } else if(!userLoginData.matches(loginPattern)) {
                    Toast.makeText(getApplicationContext(), "Login can't contain '.', '#', '$', '[', or ']'", Toast.LENGTH_LONG).show();
                } else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Check if Email is exist in firebase database
                            if (snapshot.hasChild(userLoginData)) {
                                final String getPassword = snapshot.child(userLoginData).child("passwords").getValue(String.class);

                                if (getPassword.equals(userPasswordData)) {
                                    Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
                                    openMainActivity();
                                    finish();
                                } else { Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_LONG).show(); }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        singupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
