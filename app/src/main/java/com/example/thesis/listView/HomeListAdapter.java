package com.example.thesis.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.thesis.R;
import com.example.thesis.modules.DrinkModel;

import java.util.List;

/* loaded from: C:\Users\nxdgb\OneDrive\Рабочий стол\base_source_from_JADX\resources\classes3.dex */
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyViewHolder> {
    private Context context;
    private List<DrinkModel> list;
    private OnItemClickListener mListener;

    /* loaded from: C:\Users\nxdgb\OneDrive\Рабочий стол\base_source_from_JADX\resources\classes3.dex */
    public interface OnItemClickListener {
        void onItemClick(DrinkModel drinkModel, int i);
    }

    public HomeListAdapter(Context context, List<DrinkModel> list, OnItemClickListener mListener) {
        this.context = context;
        this.list = list;
        this.mListener = mListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_templates, parent, false);
        return new MyViewHolder(v);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DrinkModel drinkModel = this.list.get(position);
        holder.Title.setText(drinkModel.getName());
        holder.Text.setText(drinkModel.getText());
        holder.Price.setText(String.format("$%d", Integer.valueOf(drinkModel.getPrice())));
        Glide.with(holder.tamplateImg.getContext()).load(this.list.get(position).getImageURL()).placeholder(com.firebase.ui.auth.R.drawable.common_full_open_on_phone).error(com.firebase.ui.auth.R.drawable.common_full_open_on_phone).into(holder.tamplateImg);
        holder.bind(drinkModel, this.mListener, position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<DrinkModel> list = this.list;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /* loaded from: C:\Users\nxdgb\OneDrive\Рабочий стол\base_source_from_JADX\resources\classes3.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Price;
        TextView Text;
        TextView Title;
        ImageView tamplateImg;

        MyViewHolder(View itemView) {
            super(itemView);
            this.tamplateImg = itemView.findViewById(R.id.tamplate_img);
            this.Title = itemView.findViewById(R.id.title);
            this.Text = itemView.findViewById(R.id.text);
            this.Price =  itemView.findViewById(R.id.updated_price_text);
        }

        public void bind(final DrinkModel item, final OnItemClickListener mListener, final int position) {
            this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.example.thesis.recyclerViews.HomeListAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    mListener.onItemClick(item, position);
                }
            });
        }
    }
}
