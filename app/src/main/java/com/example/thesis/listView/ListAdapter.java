package com.example.thesis.listView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thesis.R;
import com.example.thesis.modules.DrinkModel;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

   public static final int res = 1;
   public OnItemClickListener mListener;

   private Context context;
   private List<DrinkModel> list;


   public ListAdapter(Context context, List<DrinkModel> list) {
      this.context = context;
      this.list = list;
   }

   @NonNull
   @Override
   public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_templates, parent, false);
      return new MyViewHolder(v);
   }


   @Override
   public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

      final DrinkModel drinkModel = list.get(position);

      holder.Title.setText(drinkModel.getName());
      holder.Text.setText(drinkModel.getText());
      holder.Price.setText(String.format("$%d", drinkModel.getPrice()));

      Glide.with(holder.tamplateImg.getContext())
              .load(list.get(position).getImageURL())
              .placeholder(com.firebase.ui.auth.R.drawable.common_full_open_on_phone)
              .error(com.firebase.ui.auth.R.drawable.common_full_open_on_phone)
              .into(holder.tamplateImg);

//      holder.itemView.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View view) {
//            mListener.onItemClick(drinkModel);
//         }
//      });
   }

   @Override
   public int getItemCount() {
      if (list == null) {
         return 0;
      } else {
         return list.size();
      }
   }
//implements View.OnClickListener
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
         Price = itemView.findViewById(R.id.updated_price_text);

      }

//      @Override
//      public void onClick(View v) {
//         int position = getAdapterPosition();
//         if (position != RecyclerView.NO_POSITION) {
//            DrinkModel drinkModel = mListener.get(position);
//            mListener.onItemClick(drinkModel); // <-- Call the listener
//         }
//      }
   }

   public interface OnItemClickListener {
      void onItemClick(DrinkModel drinkModel);
   }
}
