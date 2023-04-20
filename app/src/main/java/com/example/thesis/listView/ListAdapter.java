package com.example.thesis.listView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thesis.DrinkActivity;
import com.example.thesis.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

   public static final int res = 1;

   Context context;
   List<DrinkModel> list;
   private ItemClickListener mClickListener;

   public ListAdapter(Context context, List<DrinkModel> list) {
      this.context = context;
      this.list = list;
   }

   @NonNull
   @Override
   public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //or context
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_templates, parent, false);
      return new MyViewHolder(v);
   }


   @Override
   public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

      final DrinkModel drinkModel = list.get(position);

      //holder.tamplateImg.setImageResource(drinkModel.getImageID());
      holder.Title.setText(drinkModel.getName());
      holder.Text.setText(drinkModel.getText());
      holder.Price.setText(String.format("$%d", drinkModel.getPrice()));

      Glide.with(holder.tamplateImg.getContext())
              .load(list.get(position).getImageURL())
              .placeholder(com.firebase.ui.auth.R.drawable.common_full_open_on_phone)
              .error(com.firebase.ui.auth.R.drawable.common_full_open_on_phone)
              .into(holder.tamplateImg);
   }

   @Override
   public int getItemCount() {
      if (list == null) {
         return 0;
      } else {
         return list.size();
      }
   }

//RecyclerView.ViewHolder implements View.OnClickListener
   public class MyViewHolder extends RecyclerView.ViewHolder {

      ImageView tamplateImg;
      TextView Title;
      TextView Text;
      TextView Price;

      MyViewHolder(View itemView) {

         super(itemView);

         tamplateImg = itemView.findViewById(R.id.tamplate_img);
         Title = itemView.findViewById(R.id.title);
         Text = itemView.findViewById(R.id.text);
         Price = itemView.findViewById(R.id.price);

         //itemView.setOnClickListener(this);
      }

//      @Override
//      public void onClick(View view) {
//         if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//      }
   }

   void setClickListener(ItemClickListener itemClickListener) {
      this.mClickListener = itemClickListener;
   }

   public interface ItemClickListener {
      void onItemClick(View view, int position);
   }



   /*public static class MyViewHolder extends RecyclerView.ViewHolder {
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
   }*/
}
