package com.example.casodistudiomamange.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseController {

    public DatabaseReference dataref;
    public FirebaseFirestore df;
    public String codiceGroupOrder;
    public String codiceSingleOrder;
    private Table table;


    public DatabaseController() {
        //this.dataref = FirebaseDatabase.getInstance().getReference().child("Ordini");
        this.df= FirebaseFirestore.getInstance();
    }


    public void createOrdersFirestore(String usernameInserito){


    }

    //false-> tavolo occupato  true-> tavolo libero
    public boolean isTableAvailable(String codiceTavolo){
        table = new Table();
        df.collection("TAVOLI")
                .whereEqualTo("codiceTavolo",codiceTavolo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                table.setFlag((Long)doc.getData().get("flag"));
                            }
                        }
                    }
                });
        if(table.getFlag() == 0){
            return false;
        } else {
            return true;
        }
    }

}
