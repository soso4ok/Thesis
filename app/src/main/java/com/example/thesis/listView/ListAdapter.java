package com.example.thesis.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesis.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

   Context context;
   ArrayList<DrinkModel> drinkModelArrayList;

   public ListAdapter(Context context, ArrayList<DrinkModel> drinkModelArrayList) {
      this.context = context;
      this.drinkModelArrayList = drinkModelArrayList;
   }

   @NonNull
   @Override
   public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(context).inflate(R.layout.product_templates, parent, false);
      return new MyViewHolder(v);
   }

   @Override
   public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      DrinkModel drinkModel = drinkModelArrayList.get(position);
      holder.tamplateImg.setImageResource(drinkModel.getImageID());
      holder.Title.setText(drinkModel.getName());
      holder.Text.setText(drinkModel.getText());
      holder.Price.setText(drinkModel.getPrice());
   }


   @Override
   public int getItemCount() {
      return drinkModelArrayList.size();
   }

   public static class MyViewHolder extends RecyclerView.ViewHolder {
      ImageView tamplateImg;
      TextView Title;
      TextView Text;
      TextView Price;

      public MyViewHolder(@NonNull View itemView) {
         super(itemView);
         tamplateImg = itemView.findViewById(R.id.tamplate_img);
         Title = itemView.findViewById(R.id.title);
         Text = itemView.findViewById(R.id.text);
          Price = itemView.findViewById(R.id.price);

      }
   }
}
