package com.example.thesis.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.thesis.listView.TranslateAnimationUtil;
import com.example.thesis.listView.DrinkModel;
import com.example.thesis.listView.ListAdapter;
import com.example.thesis.R;
import com.example.thesis.slider.SliderAdapter;
import com.example.thesis.slider.SliderItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //Widgets
    private RecyclerView rView;

    //Variables
    private List<DrinkModel> drinkArrayList;
    private ListAdapter mAdapter;
    private BottomNavigationView navBar;
    private Context mContext;

    //Firebase
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Initialize view
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager2 viewPager2 = view.findViewById(R.id.viewPagerImageSlider);

        // Create slicer for View Pager and add t
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.promotion_first));
        sliderItems.add(new SliderItem(R.drawable.promotion_first));

        /**
         * Sets up the ViewPager2 with appropriate adapter, page limit,
         * padding, and scrolling properties for displaying content with smooth user experience.
         */
        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.setClipToPadding(false);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.SCROLL_CAPTURE_HINT_AUTO);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rView = view.findViewById(R.id.drink_list);
        navBar = getActivity().findViewById(R.id.botton_menu);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        drinkArrayList = new ArrayList<>();

        ClearAll();

        getDataFromFirebase();

        /*databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                drinkArrayList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    DrinkModel model = postSnapshot.getValue(DrinkModel.class);

                    Glide.with(getContext())
                            .load(model)
                            .into(rImage);

                    drinkArrayList.add(model);
                }
                // Create the adapter if it hasn't been created yet
                if (mAdapter == null) {
                    mAdapter = new ListAdapter(getContext(), drinkArrayList);
                    rView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(0);

    }

    private void getDataFromFirebase() {

        Query query = databaseReference.child("Drinks");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ClearAll();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    DrinkModel drinkModel = new DrinkModel();

                    drinkModel.setImageURL(postSnapshot.child("img").getValue().toString());
                    drinkModel.setName(postSnapshot.child("name").getValue().toString());
                    drinkModel.setPrice(Integer.parseInt(postSnapshot.child("price").getValue().toString()));
                    drinkModel.setText(postSnapshot.child("text").getValue().toString());

                    drinkArrayList.add(drinkModel);

                }

                rView.setLayoutManager(new LinearLayoutManager(getContext()));
                rView.setOnTouchListener(new TranslateAnimationUtil(mContext, navBar));
                rView.setHasFixedSize(true);

                // Create the adapter if it hasn't been created yet
                mAdapter = new ListAdapter(mContext, drinkArrayList);
                rView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void ClearAll() {
        if (drinkArrayList == null) {
            drinkArrayList.clear();

            if (drinkArrayList == null) {
                mAdapter.notifyDataSetChanged();
            }
        }

        drinkArrayList = new ArrayList<>();
    }
}

//        ListAdapter listAdapter = new ListAdapter(getContext(), drinkArrayList);
//
//
//        listAdapter.notifyDataSetChanged();


//        super.onViewCreated(view, savedInstanceState);
//
//        //Call method with data drinks to RecyclerView
//        dataInitialize();
//
//        //Before loading

//
//        //Set RecyclerView
//        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
//            @Override protected int getVerticalSnapPreference() {
//                return LinearSmoothScroller.SNAP_TO_START;
//            }
//        };
//        smoothScroller.setTargetPosition(0);
//
//        //Implement a OnTouchListener method to animate the bottom menu
//        navBar = getActivity().findViewById(R.id.botton_menu);
//        recyclerView.setOnTouchListener(new TranslateAnimationUtil(super.getContext(), navBar));
//            ListAdapter listAdapter = new ListAdapter(getContext(), drinkArrayList);
//        recyclerView.setAdapter(listAdapter);
//
//        listAdapter.notifyDataSetChanged();
//    }
//
//    private void dataInitialize() {
//        drinkArrayList = new ArrayList<>();
//
//        drinkTitle = new String[] {
//                getString(R.string.bubbleTeaFirst),
//                getString(R.string.bubbleTeaFifth),
//                getString(R.string.bubbleTeaSecond),
//                getString(R.string.bubbleTeaThird),
//                getString(R.string.bubbleTeaFourth),
//        };
//
//        imageResorseID = new int[] {
//                R.drawable.bubbletea_pear_delight,
//                R.drawable.bubbletea_cotton_candy,
//                R.drawable.bubbletea_paradase_beach,
//                R.drawable.bubbletea_kiwi_bomb,
//                R.drawable.bubbletea_milky_way
//        };
//
//        drinkText = new String[] {
//                getString(R.string.Lorem),
//                getString(R.string.Lorem),
//                getString(R.string.Lorem),
//                getString(R.string.Lorem),
//                getString(R.string.Lorem),
//        };
//
//        drinkPrice = new String[] {
//                getString(R.string.FirstPrice),
//                getString(R.string.SecondPrice),
//                getString(R.string.ThirdPrice),
//                getString(R.string.FourthPrice),
//                getString(R.string.FifthPrice),
//        };
//
//        //Go through all the elements
//        for(int i = 0; i < drinkTitle.length; i++) {
//            DrinkModel drinkModel = new DrinkModel(drinkTitle[i], drinkText[i], imageResorseID[i], drinkPrice[i]);
//            drinkArrayList.add(drinkModel);
//        }
//    }

