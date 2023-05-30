package com.example.thesis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.thesis.modules.DrinkModel;

public class DrinkActivity extends AppCompatActivity {

    //XML elements
    private TextView mainTitle;
    private ImageView productImage;
    private TextView productText;
    private TextView updatedPriceText;
    private TextView countText;

    //XML Buttons
    private ImageButton addDrinkToCart;
    private ImageButton shopCartButton;
    private ImageButton backButton;
    private ImageButton minusButton;
    private ImageButton plusButton;

    //Drink model
    private DrinkModel model;

    //Variables
    private int mCount = 1;
    private int updatedPrice = 0;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        mainTitle = findViewById(R.id.title);
        productText = findViewById(R.id.text);
        productImage = findViewById(R.id.image);

        //Buttons
        minusButton = findViewById(R.id.minus_button);
        plusButton = findViewById(R.id.plus_button);
        backButton = findViewById(R.id.back_button);
        shopCartButton = findViewById(R.id.cart_button);
        addDrinkToCart = findViewById(R.id.add_drink_button);

        updatedPriceText = findViewById(R.id.updated_price_text);
        countText = findViewById(R.id.count_text);


        model = (DrinkModel) getIntent().getSerializableExtra("drinkModel");
        if (model != null) {
            mainTitle.setText(model.getName());
            productText.setText(model.getText());
            updatedPriceText.setText(String.format("$%d", Integer.valueOf(model.getPrice())));
            Glide.with(getApplicationContext()).load(model.getImageURL()).into(productImage);
        } else {
            Log.e("DrinkActivity", "Error: DrinkModel object is null");
            Toast.makeText(this, "Error: Unable to display drink information", Toast.LENGTH_LONG).show();
            finish();
        }

        //Counter buttons
        minusButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.thesis.activities.DrinkActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                updateMinusPrice();
            }
        });
        plusButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.thesis.activities.DrinkActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                updatePlusPrice();
            }
        });


        shopCartButton.setOnClickListener(v -> {
            startActivity(new Intent(DrinkActivity.this, CartShopActivity.class));
        });

        backButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.thesis.activities.DrinkActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                setBackButton(view);
            }
        });
        addDrinkToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartShopActivity cartShopActivity = new CartShopActivity();
                if (cartShopActivity.drinkArrayList != null) {
                    boolean itemFound = false;
                    for (DrinkModel drinkModel : cartShopActivity.drinkArrayList) {
                        if (drinkModel.getName().equals(model.getName())) {
                            itemFound = true;
                            break; // Exit the loop since the item is already in the cart
                        }
                    }
                    if (itemFound) {
                        Toast.makeText(getApplicationContext(), "This product is already in your cart", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(DrinkActivity.this, CartShopActivity.class);
                        String count = countText.getText().toString();
                        int countValue = Integer.parseInt(count);

                        // Update the model with the count and price
                        model.setCount(countValue);
                        model.setSavedOriginalPrice(model.getPrice());
                        String x = updatedPriceText.getText().toString();
                        model.setPrice(Integer.parseInt(x.substring(1, x.length())));

                        // Pass the model and original price as extras to the intent
                        intent.putExtra("drinkModel", model);

                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void updateMinusPrice() {
        if (mCount > 1) {
            mCount--;
            countText.setText(String.valueOf(mCount));

            // Update the price
            int originalPrice = model.getPrice();
            int updatedPrice = originalPrice * mCount;
            updatedPriceText.setText(String.format("$%d", updatedPrice));
        }
    }

    public void updatePlusPrice() {
        if (mCount < 9) {
            mCount++;
            countText.setText(String.valueOf(mCount));

            // Update the price
            int originalPrice = model.getPrice();
            int updatedPrice = originalPrice * mCount;
            updatedPriceText.setText(String.format("$%d", updatedPrice));
        }
    }


    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$2$com-example-thesis-activities-DrinkActivity  reason: not valid java name */
    public void setBackButton(View view) {
        onBackPressed();
    }
}
