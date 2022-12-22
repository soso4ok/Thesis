package com.example.thesis;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;

public class DrinkActivity extends AppCompatActivity {

    private TextView title_view;
    private TextView text_view;
    private TextView price_view;
    private ImageView imageView;
    private ImageButton backButton;
    private TextView num;
    private ImageView plus;
    private ImageView minus;
    private int last;
    private int count_price;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

       backButton = findViewById(R.id.btn_back);
       backButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
               onPause();
           }
       });

       plus = findViewById(R.id.plus);
       minus = findViewById(R.id.minus);
       num = findViewById(R.id.num);


       plus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            String PLUS = num.getText().toString();
            last = Integer.parseInt(PLUS);
            if (last == 99) {

            } else {
                last++;
            }
               num.setText(String.valueOf(last));
           }
       });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MINUS = num.getText().toString();
                last = Integer.parseInt(MINUS);
                if (last == 0) {

                } else {
                    last--;
                }
                num.setText(String.valueOf(last));
            }
        });

        title_view = findViewById(R.id.title);
        text_view = findViewById(R.id.text);
        price_view = findViewById(R.id.price);
        imageView = findViewById(R.id.image);

            android.content.Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

        title_view.setText(getIntent().getStringExtra("TITLE"));
        text_view.setText(getIntent().getStringExtra("TEXT"));
        price_view.setText(getIntent().getStringExtra("PRICE"));
        imageView.setImageResource(getIntent().getIntExtra("IMAGE", 0));
    }
}