package com.example.casodistudiomamange.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Databasee {

    FirebaseFirestore db;
    static final String TAG = "Read Data Activity";
    ArrayList<String> categorie;


    public Databasee() {
        db = FirebaseFirestore.getInstance();
    }

    public ArrayList<String> caricaCategorie(){
        db.collection("Categorie")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot doc: task.getResult()){
                            categorie.add(doc.getData().toString());
                        }
                    }
                });
        return categorie;
    }
}


