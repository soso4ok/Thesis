package com.example.thesis;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thesis.listView.DrinkModel;

import java.nio.charset.StandardCharsets;

public class DrinkActivity extends AppCompatActivity {

    private TextView title_view;
    private TextView text_view;
    private TextView price_view;
    private ImageView imageView;
    private ImageButton backButton;
    private ImageButton btn_add_drink;

    private TextView num;
    private ImageView plus, minus;
    private int last;
    private TextView count_price;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        title_view = findViewById(R.id.title);
        text_view = findViewById(R.id.text);
        price_view = findViewById(R.id.price);
        imageView = findViewById(R.id.image);

        /*android.content.Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        /**
         * The code assumes that there is an intent that contains the data with the keys
         * "TITLE", "TEXT", "PRICE", and "IMAGE", and that these values are passed as strings and an integer,respectively.
         * If any of these keys are not present in the intent, or if the data is not of the expected type, the default value
         * of 0 will be used for the image resource.

        title_view.setText(getIntent().getStringExtra("TITLE"));
        text_view.setText(getIntent().getStringExtra("TEXT"));
        price_view.setText(getIntent().getStringExtra("PRICE"));
        imageView.setImageResource(getIntent().getIntExtra("IMAGE", 0));
        */



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
       count_price = findViewById(R.id.price);


       plus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            String PLUS = num.getText().toString();
            last = Integer.parseInt(PLUS);
            if (last == 99) {

            }
            last++;
               count_price.setText(Integer.parseInt(String.valueOf(price_view))*last);
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


    }
}