package com.example.thesis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

    private RecyclerView rView;
    private ArrayList<DrinkModel> drinkArrayList = new ArrayList<>();
    private CartListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);

        backButton = findViewById(R.id.back_button);

        rView = findViewById(R.id.recyclerView);

        DrinkModel model = (DrinkModel) getIntent().getSerializableExtra("drinkModel");

        if (model == null) {
            mAdapter = new CartListAdapter(drinkArrayList, getApplicationContext());
            mAdapter.loadData(getApplicationContext()); // Load the data from SharedPreferences
            rView.setAdapter(mAdapter);
            rView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mAdapter = new CartListAdapter(drinkArrayList, getApplicationContext());
            mAdapter.loadData(getApplicationContext()); // Load the data from SharedPreferences
            mAdapter.addData(new DrinkModel(model.getId(), model.getName(), model.getText(), model.getPrice(), model.getImageURL(), model.getCount()), getApplicationContext());

            rView.setAdapter(mAdapter);
            rView.setLayoutManager(new LinearLayoutManager(this));
        }

        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

        mAdapter.setOnItemClickListener(new CartListAdapter.OnItemClickListener() {
            @Override
            public void onDecreaseClick(int position) {
                DrinkModel model = mAdapter.getItem(position);
                int count = model.getCount();
                if (count > 1) {
                    count--;
                    model.setCount(count);
                    mAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onIncreaseClick(int position) {
                DrinkModel model = mAdapter.getItem(position);
                int count = model.getCount();
                if (count < 10) {
                    count++;
                    model.setCount(count);
                    mAdapter.notifyItemChanged(position);
                    // Update total price or any other logic here
                }
            }
        });
    }
}