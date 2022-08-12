package com.example.shopping.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.Constant.Constant;
import com.example.shopping.R;
import com.example.shopping.adapters.ShowAllAdapter;
import com.example.shopping.models.ShowAllModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {
    private ShowAllAdapter showAllAdapter;
    private List<ShowAllModel> showAllModelList;
    private String type;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        type = getIntent().getStringExtra(Constant.Type);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.show_all_rec);
        showAllRecyclerView();
        typeIsEmpty();
        cameraCollection();
        shoesCollection();
        mobilephonesCollection();
        womanCollection();
        watchCollection();
        kidsCollection();
    }

    private void showAllRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        showAllModelList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, showAllModelList);
        recyclerView.setAdapter(showAllAdapter);

    }

    private void typeIsEmpty() {
        if (type == null || type.isEmpty()) {
            firestore.collection(Constant.ShowAll).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                showAllModelList.add(showAllModel);
                                showAllAdapter.notifyDataSetChanged();
                            }
                        }

                    });
        }
    }


    private void cameraCollection() {
        if (type != null && type.equalsIgnoreCase(Constant.Camera)) {

            firestore.collection(Constant.ShowAll).whereEqualTo(Constant.Type, Constant.Camera).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                showAllModelList.add(showAllModel);
                                showAllAdapter.notifyDataSetChanged();
                            }
                        }

                    });
        }
    }


    private void shoesCollection() {
        if (type != null && type.equalsIgnoreCase(Constant.Shoes)) {

            firestore.collection(Constant.ShowAll).whereEqualTo(Constant.Type, Constant.Shoes).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                showAllModelList.add(showAllModel);
                                showAllAdapter.notifyDataSetChanged();
                            }
                        }

                    });
        }
    }

    private void mobilephonesCollection() {
        if (type != null && type.equalsIgnoreCase(Constant.Mobile_Phones)) {
            firestore.collection(Constant.ShowAll).whereEqualTo(Constant.Type, Constant.Mobile).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                showAllModelList.add(showAllModel);
                                showAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }

    private void womanCollection() {

        if (type != null && type.equalsIgnoreCase(Constant.Woman)) {

            firestore.collection(Constant.ShowAll).whereEqualTo(Constant.Type, Constant.Woman).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                showAllModelList.add(showAllModel);
                                assert showAllModel != null;
                                Log.e("watch", showAllModel.toString());
                                showAllAdapter.notifyDataSetChanged();
                            }
                        }

                    });
        }
    }


    private void watchCollection() {
        if (type != null && type.equalsIgnoreCase(Constant.Watch)) {

            firestore.collection(Constant.ShowAll).whereEqualTo(Constant.Type, Constant.Watch).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                showAllModelList.add(showAllModel);
                                assert showAllModel != null;
                                showAllAdapter.notifyDataSetChanged();
                            }
                        }

                    });
        }
    }

    private void kidsCollection() {
        if (type != null && type.equalsIgnoreCase(Constant.Kids)) {

            firestore.collection(Constant.ShowAll).whereEqualTo(Constant.Type, Constant.Woman).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                showAllModelList.add(showAllModel);
                                assert showAllModel != null;
                                showAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }


}

