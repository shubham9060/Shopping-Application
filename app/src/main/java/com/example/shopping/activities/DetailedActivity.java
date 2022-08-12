package com.example.shopping.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shopping.Constant.Constant;
import com.example.shopping.R;
import com.example.shopping.models.NewProductsModel;
import com.example.shopping.models.ShowAllModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class DetailedActivity extends AppCompatActivity {
    ImageView ivDetailedImage;
    TextView tvRating, tvDetailedName, tvDetailedDescription, tvDetailedPrice, tvQuantity;
    Button btnAddToCart, btnBuyNow;
    ImageView ivAddItems, ivRemoveItems;
    ShowAllModel showAllModel = null;
    FirebaseAuth authentication;
    private FirebaseFirestore firestore;
    NewProductsModel newProductsModel = null;
    int totalQuantity = 1;
    int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        authentication = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        final Object object = getIntent().getSerializableExtra(Constant.Detailed);
        if (object instanceof NewProductsModel) {
            newProductsModel = (NewProductsModel) object;
        } else if (object instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) object;
        }
        findViewById();
        showAll();
        Addtocart();
        addItems();
        removeItems();
    }

    private void findViewById() {
        ivDetailedImage = findViewById(R.id.ivDetailedImage);
        tvDetailedName = findViewById(R.id.tvDetailedName);
        tvRating = findViewById(R.id.tvRating);
        tvDetailedDescription = findViewById(R.id.tvDetailedDescription);
        tvDetailedPrice = findViewById(R.id.tvDetailedPrice);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        ivRemoveItems = findViewById(R.id.tvRemoveItem);
        ivAddItems = findViewById(R.id.ivAddItem);
        tvQuantity = findViewById(R.id.tvQuantity);
    }


    private void showAll() {
        if (newProductsModel != null) {
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(ivDetailedImage);
            tvDetailedName.setText(newProductsModel.getName());
            tvRating.setText(newProductsModel.getRating());
            tvDetailedDescription.setText(newProductsModel.getDescription());
            tvDetailedPrice.setText(String.valueOf(newProductsModel.getPrice()));
            tvDetailedName.setText(newProductsModel.getName());
            totalPrice = newProductsModel.getPrice() * totalQuantity;

        }
        if (showAllModel != null) {
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(ivDetailedImage);
            tvDetailedName.setText(showAllModel.getName());
            tvRating.setText(showAllModel.getRating());
            tvDetailedDescription.setText(showAllModel.getDescription());
            tvDetailedPrice.setText(String.valueOf(showAllModel.getPrice()));
            totalPrice = showAllModel.getPrice() * totalQuantity;
        }
    }

    private void Addtocart() {
        btnAddToCart.setOnClickListener(view -> addtocart());
    }

    private void addItems() {
        ivAddItems.setOnClickListener(view -> {
            if (totalQuantity < 10) {
                totalQuantity++;
                tvQuantity.setText(String.valueOf(totalQuantity));

                if (newProductsModel != null) {
                    totalPrice = newProductsModel.getPrice() * totalQuantity;
                }
                if (showAllModel != null) {
                    totalPrice = showAllModel.getPrice() * totalQuantity;
                }
            }
        });
    }

    private void removeItems() {
        ivRemoveItems.setOnClickListener(view -> {
            if (totalQuantity > 1) {
                totalQuantity--;
                tvQuantity.setText(String.valueOf(totalQuantity));
            }
        });

    }

    private void addtocart() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat(Constant.Date_Format, Locale.getDefault());
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat(Constant.Time_Format, Locale.getDefault());
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put(Constant.Product_Name, tvDetailedName.getText().toString());
        cartMap.put(Constant.Product_Price, tvDetailedPrice.getText().toString());
        cartMap.put(Constant.Current_Time, saveCurrentTime);
        cartMap.put(Constant.Current_Date, saveCurrentDate);
        cartMap.put(Constant.Total_Quantity, tvQuantity.getText().toString());
        cartMap.put(Constant.Total_Price, totalPrice);

        firestore.collection(Constant.AddToCart).document(Objects.requireNonNull
                (authentication.getCurrentUser()).getUid())
                .collection(Constant.User).add(cartMap).addOnCompleteListener(task -> {
            Toast.makeText(DetailedActivity.this, Constant.AddedToCart, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}


