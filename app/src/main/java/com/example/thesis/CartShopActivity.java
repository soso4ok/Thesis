package com.example.thesis;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesis.listView.CartListAdapter;
import com.example.thesis.modules.DrinkModel;

import java.util.ArrayList;

/* loaded from: C:\Users\nxdgb\OneDrive\Рабочий стол\base_source_from_JADX\resources\classes5.dex */
public class CartShopActivity extends AppCompatActivity {

    //XML components
    private ImageButton backButton;
    private static TextView totalPrice;

    //Variables
    private static int calcTotal = 0;

    private DrinkModel model;
    private RecyclerView rView;
    private static ArrayList<DrinkModel> drinkArrayList = new ArrayList<>();
    private CartListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);

        backButton = findViewById(R.id.back_button);
        totalPrice = findViewById(R.id.total_price);
        rView = findViewById(R.id.recyclerView);

         model = (DrinkModel) getIntent().getSerializableExtra("drinkModel");

        if (model == null) {
            mAdapter = new CartListAdapter(drinkArrayList, getApplicationContext());
            mAdapter.loadData(getApplicationContext()); // Load the data from SharedPreferences
            rView.setAdapter(mAdapter);
            rView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mAdapter = new CartListAdapter(drinkArrayList, getApplicationContext());
            mAdapter.loadData(getApplicationContext()); // Load the data from SharedPreferences
            mAdapter.addData(new DrinkModel(model.getId(), model.getName(), model.getText(), model.getPrice(), model.getSavedOriginalPrice(), model.getImageURL(), model.getCount()), getApplicationContext());
            rView.setAdapter(mAdapter);
            rView.setLayoutManager(new LinearLayoutManager(this));
        }


        backButton.setOnClickListener(view -> {
            onBackPressed();
        });



        mAdapter.setOnItemClickListener(new CartListAdapter.OnItemClickListener() {
            @Override
            public void onDecreaseClick(int position) {
                DrinkModel currentItem = mAdapter.getItem(position);
                int count = currentItem.getCount();

                if (count > 1) {
                    count--;
                    currentItem.setCount(count);
                    currentItem.setPrice(currentItem.getPrice() - currentItem.getSavedOriginalPrice());
                    mAdapter.notifyItemChanged(position);

                    calculateTotalPrice(); // Recalculate the total price
                } else if (count == 1) {
                }
            }

            @Override
            public void onIncreaseClick(int position) {
                DrinkModel currentItem = mAdapter.getItem(position);
                int count = currentItem.getCount();

                if (count < 9) {
                    count++;
                    currentItem.setCount(count);
                    currentItem.setPrice(currentItem.getPrice() + currentItem.getSavedOriginalPrice());
                    mAdapter.notifyItemChanged(position);
                    // Update total price or any other logic here

                    calculateTotalPrice(); // Recalculate the total price
                }
            }

                @Override
                public void onDelete(int position, int price) {
                    calculateTotalPrice(); // Recalculate the total price
                }
            });

        calculateTotalPrice(); // Recalculate the total price

    }

    public static void calculateTotalPrice() {
        int total = 0;

        for (DrinkModel item : drinkArrayList) {
            total += item.getPrice();
        }

        calcTotal = total;
        totalPrice.setText(String.format("$%d", calcTotal));
    }
}