package com.example.shopping.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.Constant.Constant;
import com.example.shopping.R;
import com.example.shopping.models.MyCartModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private final Context context;
    private final List<MyCartModel> list;
    private int totalAmount = 0;

    public MyCartAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.price.setText(list.get(position).getProductPrice() + Constant.Rs);
        holder.name.setText(list.get(position).getProductName());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));
        holder.totalQuantity.setText(list.get(position).getTotalQuantity());

        totalAmount = totalAmount + list.get(position).getTotalPrice();
        Intent intent = new Intent(Constant.MyTotalAmount);
        intent.putExtra(Constant.totalAmount, totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, date, time, totalQuantity, totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvProductName);
            price = itemView.findViewById(R.id.tvProductPrice);
            date = itemView.findViewById(R.id.tvCurrentDate);
            time = itemView.findViewById(R.id.tvCurrentTime);
            totalQuantity = itemView.findViewById(R.id.tvTotalQuantity);
            totalPrice = itemView.findViewById(R.id.tvTotalPrice);

        }
    }
}
