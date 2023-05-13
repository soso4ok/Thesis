package com.example.thesis.listView;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thesis.CartShopActivity;
import com.example.thesis.R;
import com.example.thesis.modules.DrinkModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartViewHolder> {
    private ArrayList<DrinkModel> cartItems = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDecreaseClick(int position);

        void onIncreaseClick(int position);

        void onDelete(int position, int price);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public CartListAdapter(ArrayList<DrinkModel> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cart_templates, parent, false);
        return new CartViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        DrinkModel currentItem = cartItems.get(position);

        holder.cartItemName.setText(currentItem.getName());
        holder.cartItemPrice.setText(String.format("$%d", currentItem.getPrice()));
        holder.cartItemCount.setText(String.format("%d", currentItem.getCount()));

        Glide.with(context)
                .load(currentItem.getImageURL())
                .into(holder.cartItemImage);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView cartItemName, cartItemPrice, cartItemCount;
        private ImageView cartItemImage, DeleteButton;
        public ImageButton minusButton, plusButton;

        public CartViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            cartItemName = itemView.findViewById(R.id.title);
            cartItemPrice = itemView.findViewById(R.id.updated_price_text);
            cartItemImage = itemView.findViewById(R.id.tamplate_img);

            cartItemCount = itemView.findViewById(R.id.count_text);
            minusButton = itemView.findViewById(R.id.minus_button);
            plusButton = itemView.findViewById(R.id.plus_button);

            minusButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                onItemClickListener.onDecreaseClick(position);
            });

            plusButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                onItemClickListener.onIncreaseClick(position);
            });

            DeleteButton = itemView.findViewById(R.id.delete_button);
            DeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    int price = cartItems.get(position).getPrice();
                    if (onItemClickListener != null) {
                        onItemClickListener.onDelete(position, price);
                    }
                    deleteData(position, itemView.getContext());
                    CartShopActivity.calculateTotalPrice();
                }
            });
        }
    }

    public DrinkModel getItem(int position) {
        return cartItems.get(position);
    }

    // Method to save data to SharedPreferences
    private void saveData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        editor.putString("MyDataList", json);
        editor.apply();
    }

    // Method to load data from SharedPreferences
    public void loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("MyDataList", "");
        Type type = new TypeToken<List<DrinkModel>>() {}.getType();
        List<DrinkModel> data = gson.fromJson(json, type);
        if (data != null) {
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }
            cartItems.clear();
            cartItems.addAll(data);
            notifyDataSetChanged();
        }
    }

    // Method to add new data to the list
    public void addData(DrinkModel myData, Context context) {
        if (myData != null) {
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }
            cartItems.add(myData);
            notifyItemInserted(cartItems.size() - 1);
            saveData(context);
        }
    }

    // Method to delete data from the list
    public void deleteData(int position, Context context) {
        cartItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartItems.size());
        saveData(context);
    }
}