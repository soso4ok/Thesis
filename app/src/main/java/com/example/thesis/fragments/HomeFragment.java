package com.example.thesis.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thesis.CartShopActivity;
import com.example.thesis.DrinkActivity;
import com.example.thesis.R;
import com.example.thesis.listView.HomeListAdapter;
import com.example.thesis.listView.TranslateAnimationUtil;
import com.example.thesis.modules.DrinkModel;
import com.example.thesis.slider.MarginPageTransformer;
import com.example.thesis.slider.SliderAdapter;
import com.example.thesis.modules.SliderModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeListAdapter.OnItemClickListener {
    private static List<DrinkModel> drinkArrayList;
    private ImageButton shopCartButton;
    private DatabaseReference databaseReference;
    private HomeListAdapter mAdapter;
    private Context mContext;
    private BottomNavigationView navBar;
    private RecyclerView rView;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        List<SliderModel> sliderItems = new ArrayList<>();

        sliderItems.add(new SliderModel(R.drawable.promotion_first));
        sliderItems.add(new SliderModel(R.drawable.promotion_second));

        float margin = getResources().getDimensionPixelSize(R.dimen.page_margin);
        float horizontalMargin = getResources().getDimensionPixelSize(R.dimen.page_horizontal_margin);

        ViewPager2 viewPager2 = view.findViewById(R.id.home_fragment_slider);
        viewPager2.setPageTransformer(new MarginPageTransformer(margin, horizontalMargin));
        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.getChildAt(0).setOverScrollMode(0);

        shopCartButton = view.findViewById(R.id.cart_button);

        super.onCreate(savedInstanceState);
        this.rView = view.findViewById(R.id.drink_list);
        this.navBar =getActivity().findViewById(R.id.botton_menu);
        this.databaseReference = FirebaseDatabase.getInstance().getReference();

        drinkArrayList = new ArrayList();

        ClearAll();
        getDataFromFirebase();

        RecyclerView.SmoothScroller smoothScroller =
                new LinearSmoothScroller(getContext()) { // from class: com.example.thesis.fragments.HomeFragment.2
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            protected int getVerticalSnapPreference() {
                return -1;
            }
        };
        smoothScroller.setTargetPosition(0);

        shopCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartShopActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getDataFromFirebase() {
        Query query = this.databaseReference.child("Drinks");
        query.addValueEventListener(new ValueEventListener() { // from class: com.example.thesis.fragments.HomeFragment.3
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot snapshot) {
                HomeFragment.this.ClearAll();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    DrinkModel drinkModel = new DrinkModel();

                    drinkModel.setImageURL(postSnapshot.child("img").getValue().toString());
                    drinkModel.setName(postSnapshot.child("name").getValue().toString());
                    drinkModel.setPrice(Integer.parseInt(postSnapshot.child("price").getValue().toString()));
                    drinkModel.setText(postSnapshot.child("text").getValue().toString());

                    drinkArrayList.add(drinkModel);
                }
                setUpRecyclerView();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError error) {
                String errorMessage = error.getMessage();
                if (error.getCode() == -24) {
                    Toast.makeText(HomeFragment.this.mContext, "Network error. Please check your internet connection.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(HomeFragment.this.mContext, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setUpRecyclerView() {
        this.rView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.rView.setOnTouchListener(new TranslateAnimationUtil(this.mContext, this.navBar));
        this.rView.setHasFixedSize(true);
        HomeListAdapter homeListAdapter = new HomeListAdapter(this.mContext, drinkArrayList, this);
        this.mAdapter = homeListAdapter;
        this.rView.setAdapter(homeListAdapter);
        this.mAdapter.notifyDataSetChanged();
    }

    private void ClearAll() {
        List<DrinkModel> list = drinkArrayList;
        if (list == null) {
            list.clear();
            if (drinkArrayList == null) {
                this.mAdapter.notifyDataSetChanged();
            }
        }
        drinkArrayList = new ArrayList();
    }

    @Override // com.example.thesis.recyclerViews.HomeListAdapter.OnItemClickListener
    public void onItemClick(DrinkModel item, int position) {
        DrinkModel clickedItem = drinkArrayList.get(position);
        Intent intent = new Intent(getActivity(), DrinkActivity.class);
        intent.putExtra("drinkModel", clickedItem);
        startActivity(intent);
    }
}