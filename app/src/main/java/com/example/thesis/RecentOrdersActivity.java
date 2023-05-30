package com.example.thesis;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.thesis.listView.RecentOrdersListAdapter;
import com.example.thesis.modules.OrderModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecentOrdersActivity extends AppCompatActivity {

    private static final String PREF_RECENT_ORDERS = "recent_orders";
    private RecyclerView recyclerView;
    private RecentOrdersListAdapter orderAdapter;
    private List<OrderModel> orderList;

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_orders);

        recyclerView = findViewById(R.id.orderItems);
        backButton = findViewById(R.id.back_button);

        orderAdapter = new RecentOrdersListAdapter(orderList, getApplicationContext());
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_RECENT_ORDERS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("orders", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<OrderModel>>() {}.getType();
            orderList = gson.fromJson(json, type);
        } else {
            orderList = new ArrayList<>();
        }

        // Set the updated list to the adapter
        orderAdapter.setOrderList(orderList);
        // Notify the adapter of the changes
        orderAdapter.notifyDataSetChanged();


        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(RecentOrdersActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        });

    }
}