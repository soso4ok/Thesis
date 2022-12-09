package com.example.thesis.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thesis.listView.Drink;
import com.example.thesis.listView.ListAdapter;
import com.example.thesis.R;
import com.example.thesis.slider.SliderAdapter;
import com.example.thesis.slider.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager2;
    private ArrayList drinkArrayList;
    private String[] drinkTitle;
    private int[] imageResorseID;
    private String[] drinkText;
    private String[] drinkPrice;
    private RecyclerView recyclerView;


    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v =  inflater.inflate(R.layout.fragment_home, container, false);

        //Slider
        ViewPager2 viewPager2 = v.findViewById(R.id.viewPagerImageSlider);
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.group_slider));
        sliderItems.add(new SliderItem(R.drawable.group_slider2));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.setClipToPadding(false);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataInitialize();

        recyclerView = view.findViewById(R.id.drink_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        ListAdapter listAdapter = new ListAdapter(getContext(), drinkArrayList);
//        ListAdapter listAdapter = new ListAdapter(getContext(), drinkArrayList);
        recyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        drinkArrayList = new ArrayList<>();

        drinkTitle = new String[] {
                getString(R.string.bubbleTeaFirst),
                getString(R.string.bubbleTeaSecond),
                getString(R.string.bubbleTeaThird),
        };


        imageResorseID = new int[] {
                R.drawable.bubbletea_pear_delight,
                R.drawable.bubbletea_paradase_beach,
                R.drawable.bubbletea_kiwi_bomb,
        };

        drinkText = new String[] {
                getString(R.string.Lorem),
                getString(R.string.Lorem),
                getString(R.string.Lorem),
        };

        drinkPrice = new String[] {
                getString(R.string.FirstPrice),
                getString(R.string.SecondPrice),
                getString(R.string.ThirdPrice),
        };

        for(int i = 0; i < drinkTitle.length; i++) {
            Drink drink = new Drink(drinkTitle[i], drinkText[i], imageResorseID[i], drinkPrice[i]);
            drinkArrayList.add(drink);
        }

    }
}
