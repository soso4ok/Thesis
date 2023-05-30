package com.example.thesis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.thesis.fragments.HomeFragment;
import com.example.thesis.fragments.QrCodeFragment;

public class PaymentConfirmationActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button toQrActivity;
    private Button toHomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        toQrActivity = findViewById(R.id.toQrBtn);
        toHomeActivity = findViewById(R.id.toHomeBtn);


        toQrActivity.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Очищаємо стек активностей
            intent.putExtra("fragment", "QrCodeFragment");  // Додаємо ідентифікатор фрагмента
            startActivity(intent);
        });

        toHomeActivity.setOnClickListener(v -> {
            startActivity(new Intent(PaymentConfirmationActivity.this, MainActivity.class));
        });





    }
}