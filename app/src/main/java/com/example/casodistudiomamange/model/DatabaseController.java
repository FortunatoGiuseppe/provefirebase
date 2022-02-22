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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    //false-> tavolo occupato  true-> tavolo libero
    public void createOrdersFirestore(String usernameInserito, String codiceTavolo){
        DocumentReference docRef = df.collection("TAVOLI").document(codiceTavolo);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                table= documentSnapshot.toObject(Table.class);
                if(table.getFlag()==0){
                    //Allora il tavolo è libero, perciò devo impostare il tavolo a occupato sul db, creare group order
                    // e creare single order relativo all'utente corrente

                    //imposto tavolo a occupato
                    docRef.update("flag",1);

                    //creo group order
                    Map<String, Object> nuovoGroupOrder = new HashMap<>();
                    nuovoGroupOrder.put("codice", "GO2");       //ricorda di modificare!
                    nuovoGroupOrder.put("codiceTavolo", codiceTavolo);
                    //aggiungo group order
                    df.collection("GROUP ORDERS").add(nuovoGroupOrder);

                    //creo single order
                    Map<String, Object> nuovoSingleOrder = new HashMap<>();
                    nuovoSingleOrder.put("codice", "SO2");       //ricorda di modificare!
                    nuovoSingleOrder.put("codiceGroupOrder", "GO2");
                    //aggiungo single order
                    df.collection("SINGLE ORDERS").add(nuovoSingleOrder);

                }else{
                    //Allora il tavolo è occupato, perciò esiste già il group order (che devo leggere) e devo solo
                    // creare il single order che si deve unire al group order già presente
                }
            }
        });
    }



}
