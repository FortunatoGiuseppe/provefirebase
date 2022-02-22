package com.example.casodistudiomamange.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.casodistudiomamange.R;
import com.example.casodistudiomamange.fragment.GroupOrderFragment;
import com.example.casodistudiomamange.fragment.RestaurantFragment;
import com.example.casodistudiomamange.fragment.SingleOrderFragment;
import com.example.casodistudiomamange.model.Databasee;
import com.example.casodistudiomamange.model.DatabaseController;
import com.example.casodistudiomamange.model.Table;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MaMangeNavigationActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener{
    public String codiceSingleOrder;
    public String codiceGroupOrder;
    public DatabaseController dbc;
    private Databasee db;


    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_mange_navigation);

        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        Intent intent = getIntent();
        String usernameInserito = intent.getStringExtra("UsernameInserito");

        bottomNavigationView=findViewById(R.id.bottom_navigation_bar);


        dbc = new DatabaseController();
        //boolean isAvailable=dbc.isTableAvailable("MST001");

        dbc.createOrdersFirestore(usernameInserito,"MST001");


        Fragment fragment = null;
        fragment = new RestaurantFragment();
        loadFragment(fragment);

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation_bar);
        navigationBarView.setOnItemSelectedListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    public Fragment fragment = null;
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.restaurant_menu:
                fragment = new RestaurantFragment();
                break;
            case R.id.single_order:
                fragment = new SingleOrderFragment();
                break;
            case R.id.group_order:
                fragment = new GroupOrderFragment();
                break;

        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }




    @Override
    public void onBackPressed() {

        /*
        Una volta entrato nella sezione menù non è più possibile tornare indietro alla selezione del tavolo
         */

        if(bottomNavigationView.getSelectedItemId()==R.id.restaurant_menu){
            bottomNavigationView.setSelectedItemId(R.id.restaurant_menu);
        }

        /*
        Una volta entrato nella sezione del singolo ordine o dell'ordine collettivo nel momento in cui clicco sul tasto "Indietro" sono reindirizzato al menù
         */


        if((bottomNavigationView.getSelectedItemId()==R.id.single_order) || (bottomNavigationView.getSelectedItemId()==R.id.group_order)){
            bottomNavigationView.setSelectedItemId(R.id.restaurant_menu);
        }


        /*
        Una volta entrato nella categoria di un piatto nel momento in cui clicco "Indietro" vengo riendirizzato alla sezione menù
         */

        if(bottomNavigationView.getSelectedItemId()==R.id.recycleview_plates){
            bottomNavigationView.setSelectedItemId(R.id.restaurant_menu);
        }
    }



    private boolean groupOrderExists(Table table){
        return table.getFlag()==1;
    }




}