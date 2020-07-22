package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;


    ArrayList<String> mNames= new ArrayList<>();
    ArrayList<Integer > mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        category();
        recyclerview2();
        recyclerview3();
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error","You have permission");
            }else {
                Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(Homepage.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        if(!isNetworkAvailable())
            Toast.makeText(Homepage.this, "No Internet connection", Toast.LENGTH_LONG).show();

    }


    private void category() {

        ImageButton a,b,c,d,e,f;

        a = findViewById(R.id.m1);
        b = findViewById(R.id.m2);
        c = findViewById(R.id.m6);
        d = findViewById(R.id.m3);
        e = findViewById(R.id.m4);
        f = findViewById(R.id.m5);

        a.setOnClickListener(v -> {
            Intent i = new Intent(this, Marathi.class);
            startActivity(i);
        });

        b.setOnClickListener(v -> {
            Intent i = new Intent(this, Fiction.class);
            startActivity(i);
        });

        c.setOnClickListener(v -> {
            Intent i = new Intent(this, Thriller.class);
            startActivity(i);
        });

        d.setOnClickListener(v -> {
            Intent i = new Intent(this, Romance.class);
            startActivity(i);
        });

        e.setOnClickListener(v -> {
            Intent i = new Intent(this, Selfhelp.class);
            startActivity(i);
        });

        f.setOnClickListener(v -> {
            Intent i = new Intent(this, Technical.class);
            startActivity(i);
        });

    }

    public void setSupportActionBar(Toolbar toolbar) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()){
            case R.id.account:
                i = new Intent(this,Account.class);
                startActivity(i);
                break;
            case R.id.Cart:
                i = new Intent(this, CartActivity.class);
                startActivity(i);
                break;

            case R.id.Contact:
                i = new Intent(this,Aboutus.class);
                startActivity(i);
                break;

            case R.id.categories:
                i = new Intent(this,Categories.class);
                startActivity(i);
                break;
            case R.id.feedback:
                i = new Intent(this,Feedback.class);
                startActivity(i);
                break; 

            case R.id.srch:
                i = new Intent(this,Search.class);
                startActivity(i);
                break;

            case R.id.logOut:
                i = new Intent(this,Login.class);
                startActivity(i);
                break;

        }
        drawer.closeDrawer((GravityCompat.START));
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer((GravityCompat.START));
        } else {
            finishAffinity();
        }
    }



    private void recyclerview2() {

        final RecyclerView recyclerView =findViewById(R.id.recyclerview_id2);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Books/Books").child("Trending");
        ArrayList<Book> trendingbooks =new ArrayList<>();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //  String name=dataSnapshot.getKey();
                Book b1 =dataSnapshot.getValue(Book.class);
                ((Recyclerview_adapter)recyclerView.getAdapter()).update(b1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        Recyclerview_adapter adapter =new Recyclerview_adapter(recyclerView,Homepage.this,trendingbooks);
        recyclerView.setAdapter(adapter);
    }

    private void recyclerview3() {
        final RecyclerView recyclerView =findViewById(R.id.recyclerview_id3);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Books/Books/New Releases");
        ArrayList<Book> newreleases =new ArrayList<>();

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //  String name=dataSnapshot.getKey();
                Book b1 =dataSnapshot.getValue(Book.class);
                ((Recyclerview_adapter) Objects.requireNonNull(recyclerView.getAdapter())).update(b1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        Recyclerview_adapter adapter =new Recyclerview_adapter(recyclerView,Homepage.this,newreleases);
        recyclerView.setAdapter(adapter);

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchbar, menu);
        return true;

    }
   public boolean search_click(MenuItem item){
       Intent i = new Intent(Homepage.this, Search.class);
       i.putExtra("category","All Books");
       startActivity(i);
       return true;
   }

    public void cartBtn(View view) {
        Intent i = new Intent(this, CartActivity.class);
        startActivity(i);
    }
}

