package com.example.shopping.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.Constant.Constant;
import com.example.shopping.R;
import com.example.shopping.adapters.MyCartAdapter;
import com.example.shopping.models.MyCartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    private TextView tvCartTotalPrice;
    private List<MyCartModel> cartModelList;
    private MyCartAdapter cartAdapter;
    private FirebaseAuth authentication;
    private FirebaseFirestore firestore;
    private RecyclerView rvCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        authentication = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        Toolbar tbCartToolbar = findViewById(R.id.tbCartToolbar);
        tvCartTotalPrice = findViewById(R.id.tvCartTotalPrice);
        rvCart = findViewById(R.id.rvCart);
        setSupportActionBar(tbCartToolbar);
        LocalBroadcastManager.getInstance(this).registerReceiver(MessageReceiver,
                new IntentFilter(Constant.MyTotalAmount));
        setupCartRecyclerView();
        addToCart();
    }

    private void setupCartRecyclerView() {
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this, cartModelList);
        rvCart.setAdapter(cartAdapter);
    }

    private void addToCart() {
        firestore.collection(Constant.AddToCart).document(Objects.requireNonNull
                (authentication.getCurrentUser()).getUid())
                .collection(Constant.User).get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                            cartModelList.add(myCartModel);
                            cartAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public BroadcastReceiver MessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra(Constant.totalAmount, 0);
            tvCartTotalPrice.setText(Constant.Total_Amount + Constant.Rs + totalBill);
        }
    };
}