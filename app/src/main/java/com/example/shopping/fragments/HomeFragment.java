package com.example.shopping.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.shopping.Constant.Constant;
import com.example.shopping.R;
import com.example.shopping.activities.ShowAllActivity;
import com.example.shopping.adapters.CategoryAdapter;
import com.example.shopping.adapters.NewProductsAdapter;
import com.example.shopping.models.CategoryModel;
import com.example.shopping.models.NewProductsModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryModelList;
    private NewProductsAdapter newProductsAdapter;
    private List<NewProductsModel> newProductsModelList;
    TextView tvCategoryShowAll;
    TextView tvNewProductShowAll;
    View fragmentHomeRoot;
    FirebaseFirestore firebaseFirestore;
    RecyclerView rvCategory;
    RecyclerView rvNewProduct;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeRoot = inflater.inflate(R.layout.fragment_home, container, false);
        findViewById();
        firebaseFirestore = FirebaseFirestore.getInstance();
        onClickCategoryShowAll();
        onClickNewProductShowAll();
        imageSlider();
        rvCategory();
        categoryCollection();
        rvNewProduct();
        newProductCollection();
        return fragmentHomeRoot;
    }

    private void findViewById() {
        rvCategory = fragmentHomeRoot.findViewById(R.id.rvCategory);
        rvNewProduct = fragmentHomeRoot.findViewById(R.id.rvNewProduct);
        tvCategoryShowAll = fragmentHomeRoot.findViewById(R.id.tvCategorySeeAll);
        tvNewProductShowAll = fragmentHomeRoot.findViewById(R.id.tvNewProductsSeeAll);
    }

    private void onClickCategoryShowAll() {
        tvCategoryShowAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ShowAllActivity.class);
            startActivity(intent);
        });
    }

    private void onClickNewProductShowAll() {
        tvNewProductShowAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ShowAllActivity.class);
            startActivity(intent);
        });
    }

    private void imageSlider() {
        ImageSlider imageSlider = fragmentHomeRoot.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner1, Constant.Discount_On_Shoes, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.baneer4, Constant.Discount_On_Mobiles, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.baneer2, Constant.Discount_On_Perfumes, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3, Constant.Seventy_Percent_Off, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);
    }

    private void rvCategory() {
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL
                , false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        rvCategory.setAdapter(categoryAdapter);
    }

    private void categoryCollection() {
        firebaseFirestore.collection(Constant.Category)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CategoryModel categoryModel = document.toObject(CategoryModel.class);
                            categoryModelList.add(categoryModel);
                            categoryAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), Constant.Error + task.getException(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void rvNewProduct() {
        rvNewProduct.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL
                , false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsModelList);
        rvNewProduct.setAdapter(newProductsAdapter);

    }

    private void newProductCollection() {
        firebaseFirestore.collection(Constant.NewProducts)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                            newProductsModelList.add(newProductsModel);
                            newProductsAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
