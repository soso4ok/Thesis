package com.example.thesis;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.thesis.modules.OrderModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartShopActivity extends AppCompatActivity {

    private static final String PREF_RECENT_ORDERS = "recent_orders";

    //XML components
    private ImageButton backButton;
    private static TextView totalPrice;
    private Button buyButton;

    //Variables
    private static int calcTotal = 0;

    private DrinkModel model;
    private RecyclerView rView;
    public static ArrayList<DrinkModel> drinkArrayList = new ArrayList<>();
    private CartListAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);

        backButton = findViewById(R.id.back_button);
        buyButton = findViewById(R.id.buy_button);
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
            Intent intent = new Intent(CartShopActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        });

        buyButton.setOnClickListener(v -> {
            if (drinkArrayList.isEmpty()) {
                Toast.makeText(getApplicationContext(), "There are no drinks in the cart", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(CartShopActivity.this, PaymentConfirmationActivity.class);
                String x = totalPrice.getText().toString().substring(1, totalPrice.length());
                addToRecentOrders(new OrderModel(drinkArrayList, Integer.parseInt(x)));
                startActivity(intent);
            }
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

    private void addToRecentOrders(OrderModel order) {
        // Retrieve the current list of recent orders from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_RECENT_ORDERS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("orders", null);
        ArrayList<OrderModel> recentOrdersList;
        if (json != null) {
            Type type = new TypeToken<ArrayList<OrderModel>>() {}.getType();
            recentOrdersList = gson.fromJson(json, type);
        } else {
            recentOrdersList = new ArrayList<>();
        }

        // Add the new order to the list
        recentOrdersList.add(order);

        // Save the updated list back to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String updatedJson = gson.toJson(recentOrdersList);
        editor.putString("orders", updatedJson);
        editor.apply();
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