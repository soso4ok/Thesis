package com.example.thesis.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.thesis.CartShopActivity;
import com.example.thesis.R;
import com.example.thesis.RecentOrdersActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private ImageButton shopCartButton;
    private ImageButton recentPurchasesButton;

    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        shopCartButton = view.findViewById(R.id.cart_button);
        recentPurchasesButton = view.findViewById(R.id.recent_purchases_button);

        shopCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CartShopActivity.class);
                startActivity(intent);
            }
        });

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);

        recentPurchasesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecentOrdersActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location1 = new LatLng(48.920544, 24.707491);  // вулиця Незалежності, 5
        LatLng location2 = new LatLng(48.909203, 24.704441);  // вулиця Євгена Коновальця, 86
        LatLng location3 = new LatLng(48.907199, 24.670679);  // вулиця Гетьмана Мазепи, 164
        LatLng location4 = new LatLng(48.924918, 24.708866);  // вулиця Незалежності, 81

        mMap.addMarker(new MarkerOptions().position(location1).title("вулиця Незалежності, 5"));
        mMap.addMarker(new MarkerOptions().position(location2).title("вулиця Євгена Коновальця, 86"));
        mMap.addMarker(new MarkerOptions().position(location3).title("вулиця Гетьмана Мазепи, 164"));
        mMap.addMarker(new MarkerOptions().position(location4).title("вулиця Незалежності, 81"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, 10));
    }
}