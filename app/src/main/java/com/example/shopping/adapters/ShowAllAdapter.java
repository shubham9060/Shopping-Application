package com.example.shopping.adapters;

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
import com.example.shopping.Constant.Constant;
import com.example.shopping.R;
import com.example.shopping.activities.DetailedActivity;
import com.example.shopping.models.ShowAllModel;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ViewHolder> {
    private final Context context;
    private final List<ShowAllModel> list;

    public ShowAllAdapter(Context context, List<ShowAllModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.ItemImage);
        holder.Cost.setText(Constant.Rs + (list.get(position).getPrice()));
        holder.Name.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailedActivity.class);
            intent.putExtra(Constant.Detailed, list.get(holder.getAdapterPosition()));
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ItemImage;
        private final TextView Cost;
        private final TextView Name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemImage = itemView.findViewById(R.id.ivItemImage);
            Cost = itemView.findViewById(R.id.tvItemCost);
            Name = itemView.findViewById(R.id.tvItemName);

        }
    }
}
