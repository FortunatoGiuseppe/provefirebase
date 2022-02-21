package com.example.casodistudiomamange.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.casodistudiomamange.adapter.Adapter_category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class  Databasee {

    FirebaseFirestore db;
    static final String TAG = "Read Data Activity";
    ArrayList<Category> categorie=new ArrayList<>();


    public Databasee() {
        db = FirebaseFirestore.getInstance();
    }

    public ArrayList<Category> caricaCategorie(Adapter_category adapter_category) {
        /*db.collection("Categorie")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                categorie.add(doc.getId());
                            }
                        }
                    }
                });
        return categorie;*/
        db.collection("Categorie")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestone error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                categorie.add(dc.getDocument().toObject(Category.class));
                            }
                            adapter_category.notifyDataSetChanged();
                        }
                    }
                });

        return categorie;
    }
}


