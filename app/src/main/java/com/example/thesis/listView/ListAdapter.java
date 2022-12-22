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

import com.example.thesis.DrinkActivity;
import com.example.thesis.MainActivity;
import com.example.thesis.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {


   public static final int res = 1;

   Context context;
   ArrayList<DrinkModel> drinkModelArrayList;
   Fragment fragment;
   Intent intent;

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
   public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

      final  DrinkModel drinkModel = drinkModelArrayList.get(position);


      holder.tamplateImg.setImageResource(drinkModel.getImageID());
      holder.Title.setText(drinkModel.getName());
      holder.Text.setText(drinkModel.getText());
      holder.Price.setText(drinkModel.getPrice());


      holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            AppCompatActivity appCompatActivity = (AppCompatActivity)view.getContext();

                  intent = new Intent(context, new DrinkActivity().getClass());
                  intent.putExtra("TITLE", drinkModel.getName().toString());
                  intent.putExtra("PRICE", drinkModel.getPrice().toString());
                  intent.putExtra("TEXT", drinkModel.getText().toString());
                  intent.putExtra("IMAGE", drinkModel.getImageID());
                  appCompatActivity.setResult(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                  context.startActivity(intent);

               //                  fragment = new PeardelightFragment();
//                  appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
//                  break;

         }
      });

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
