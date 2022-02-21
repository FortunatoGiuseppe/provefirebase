package com.example.casodistudiomamange.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.casodistudiomamange.R;
import com.example.casodistudiomamange.activity.MaMangeNavigationActivity;
import com.example.casodistudiomamange.adapter.Adapter_category;
import com.example.casodistudiomamange.adapter.Adapter_plates;
import com.example.casodistudiomamange.model.Category;
import com.example.casodistudiomamange.model.Databasee;
import com.example.casodistudiomamange.model.Plate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView_plates;
    /*private List<String> platesName;    //contenitore nomi dei piatti
    private List<String> platesImg;     //contenitore immagini dei piatti
    private List<String> platesDescription; //contenitore descrizioni dei piatti
    private List<String> plateFlag; //flag delle bevande*/

    private ArrayList<Plate> plates;    //lista che conterrà i nomi delle categorie
    private Adapter_plates adapter_plates;
    private FirebaseFirestore db;
    String CategoryKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        CategoryKey = bundle.getString("CategoryKey");

        db = FirebaseFirestore.getInstance();
        plates= new ArrayList<Plate>();
        adapter_plates = new Adapter_plates(getContext(), plates);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category,null);
        getActivity().setTitle("Categorie");


        recyclerView_plates = v.findViewById(R.id.recycleview_plates);
        recyclerView_plates.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1 , LinearLayoutManager.VERTICAL, false);
        recyclerView_plates.setLayoutManager(gridLayoutManager);

        recyclerView_plates.setAdapter(adapter_plates);

        caricaPiatti();


        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void caricaPiatti() {
       db.collection(CategoryKey)
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
                      if(task.isSuccessful()){
                          for (QueryDocumentSnapshot document : task.getResult()){

                                  Log.d("TAG",document.getId()+""+document.getData());



                          }
                      }
                  }
              });

                       //problemi: leggere senza sapere codici e trasformare cose lette in oggetto di plate

    }

}