package com.example.thesis.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thesis.CartShopActivity;
import com.example.thesis.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrCodeFragment extends Fragment {

    private ImageButton shopCartButton;
    private ImageView qrImageView;
    private TextView referralPoints;

    public static String encryptionKey = "/A?D(G+KaPdSgVkYp3s6v9y$B&E)H@Mc";
    byte[] encryptedUserLoginData = null;

    // Create a reference to the Firebase Realtime Database
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://thesis-53800-default-rtdb.europe-west1.firebasedatabase.app");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code, container, false);

        qrImageView = view.findViewById(R.id.qr_code);
        referralPoints = view.findViewById(R.id.referral_points);

        shopCartButton = view.findViewById(R.id.cart_button);

        shopCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CartShopActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences preferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userLoginData = preferences.getString("Login", "");

        String encryptedString = null;
        try {
            encryptedString = Base64.encodeToString(encryptedLoginData(userLoginData), Base64.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        QRGEncoder qrgEncoder = new QRGEncoder(encryptedString, null, QRGContents.Type.TEXT, 600);

        qrgEncoder.setColorWhite(Color.WHITE);
        qrgEncoder.setColorBlack(Color.rgb(58, 55, 55));

        // Getting QR-Code as Bitmap
        Bitmap bitmap = qrgEncoder.getBitmap(0);
        // Setting Bitmap to ImageView
        qrgEncoder.setColorBlack(Color.WHITE);
        qrImageView.setImageBitmap(bitmap);


// Create a listener to retrieve the points value from the database
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get the points value from the snapshot
                // Get the points value from the snapshot as Long
                if (userLoginData.matches("\\d+")) {
                    long points = ((Long) snapshot.child(userLoginData).child("points").getValue());
                    referralPoints.setText(points + " points");
                } else {
                    String points = ((String) snapshot.child(userLoginData).child("points").getValue());
                    referralPoints.setText(points + " points");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });

        return view;

    }

    // Encryption
    public byte[] encryptedLoginData(String userLoginData) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        encryptedUserLoginData = cipher.doFinal(userLoginData.getBytes("UTF-8"));
        return encryptedUserLoginData;
    }


}
