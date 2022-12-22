package com.example.thesis.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thesis.listView.TranslateAnimationUtil;
import com.example.thesis.listView.DrinkModel;
import com.example.thesis.listView.ListAdapter;
import com.example.thesis.R;
import com.example.thesis.slider.SliderAdapter;
import com.example.thesis.slider.SliderItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ArrayList drinkArrayList;
    private String[] drinkTitle;
    private int[] imageResorseID;
    private String[] drinkText;
    private String[] drinkPrice;
    private RecyclerView recyclerView;
    private BottomNavigationView navBar;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view =  inflater.inflate(R.layout.fragment_home, container, false);



        //Slider
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPagerImageSlider);
        List<SliderItem> sliderItems = new ArrayList<>();
            sliderItems.add(new SliderItem(R.drawable.group_slider));
            sliderItems.add(new SliderItem(R.drawable.group_slider2));

            viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
            viewPager2.setOffscreenPageLimit(2);
            viewPager2.setClipToPadding(false);
            viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      //  radioGroup = view.findViewById(R.id.radio_group);
//        radioBtn1 = view.findViewById(R.id.radio_fruit);
//        radioBtn2 = view.findViewById(R.id.radio_sour);


        dataInitialize();

        recyclerView = view.findViewById(R.id.drink_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        navBar = getActivity().findViewById(R.id.botton_menu);

        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(0);
        recyclerView.setOnTouchListener(new TranslateAnimationUtil(super.getContext(), navBar));
            ListAdapter listAdapter = new ListAdapter(getContext(), drinkArrayList);
        recyclerView.setAdapter(listAdapter);

        //

        listAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        drinkArrayList = new ArrayList<>();

        drinkTitle = new String[] {
                getString(R.string.bubbleTeaFirst),
                getString(R.string.bubbleTeaFifth),
                getString(R.string.bubbleTeaSecond),
                getString(R.string.bubbleTeaThird),
                getString(R.string.bubbleTeaFourth),

        };

        imageResorseID = new int[] {
                R.drawable.bubbletea_pear_delight,
                R.drawable.bubbletea_cotton_candy,
                R.drawable.bubbletea_paradase_beach,
                R.drawable.bubbletea_kiwi_bomb,
                R.drawable.bubbletea_milky_way
        };

        drinkText = new String[] {
                getString(R.string.Lorem),
                getString(R.string.Lorem),
                getString(R.string.Lorem),
                getString(R.string.Lorem),
                getString(R.string.Lorem),
        };

        drinkPrice = new String[] {
                getString(R.string.FirstPrice),
                getString(R.string.SecondPrice),
                getString(R.string.ThirdPrice),
                getString(R.string.FourthPrice),
                getString(R.string.FifthPrice),
        };

        for(int i = 0; i < drinkTitle.length; i++) {
            DrinkModel drinkModel = new DrinkModel(drinkTitle[i], drinkText[i], imageResorseID[i], drinkPrice[i]);
            drinkArrayList.add(drinkModel);
        }

    }


}
