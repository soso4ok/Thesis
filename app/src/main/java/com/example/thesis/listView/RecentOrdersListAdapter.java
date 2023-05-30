package com.example.thesis.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thesis.R;
import com.example.thesis.modules.DrinkModel;
import com.example.thesis.modules.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class RecentOrdersListAdapter extends RecyclerView.Adapter<RecentOrdersListAdapter.RecentViewAdapter> {

    Context context;
    List<OrderModel> drinkModelList = new ArrayList<>();

    public RecentOrdersListAdapter(List<OrderModel> drinkModelList, Context context) {
        this.drinkModelList = drinkModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecentOrdersListAdapter.RecentViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recent_template, parent, false);
        return new RecentViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentOrdersListAdapter.RecentViewAdapter holder, int position) {
        OrderModel drinkModel = drinkModelList.get(position);

        for (DrinkModel drinkData : drinkModel.getDrinkModelArrayList()) {
            holder.drinkTextView.append(String.format(drinkData.getName() + "\n"));
        }

        holder.priceTextView.setText(String.valueOf(String.format(" $" + drinkModel.getTotalOrderPrice())));

    }

    @Override
    public int getItemCount() {
        return drinkModelList.size();
    }

    public static class RecentViewAdapter extends RecyclerView.ViewHolder {

        TextView drinkTextView;
        TextView priceTextView;

        public RecentViewAdapter(@NonNull View itemView) {
            super(itemView);
            drinkTextView = itemView.findViewById(R.id.drinks);
            priceTextView = itemView.findViewById(R.id.price);
        }

    }
    public void setOrderList(List<OrderModel> drinkModelList) {
        this.drinkModelList = drinkModelList;
    }

}
