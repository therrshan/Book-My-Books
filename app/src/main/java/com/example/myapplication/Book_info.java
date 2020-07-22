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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class Book_info extends AppCompatActivity {
    public String rating, productid;
    private String book_name, book_price;
    private String book_url;
    private String image_url;
    private String desc;
    private String uid;
    private String qty;
    private TextView bookname,description,price;
    private ImageView bookimg;
    private FloatingActionButton read;
    private com.example.myapplication.User user;
    private Spinner spinner;
    private RatingBar ratingBar;
    private static final String[] paths = {"1", "2", "3", "4"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(Book_info.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String quantity;

                switch (position) {
                    case 0:
                        qty="1";

                        break;
                    case 1:
                        qty="2";
                        break;
                    case 2:
                        qty="3";
                        break;
                    case 3:
                        qty="4";
                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextView tv = findViewById(R.id.rtng);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon( R.drawable.backarrow);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        uid =CurrentUser.getFirembaseUser();
        ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {

            int noofstars = ratingBar.getNumStars();
            float getrating = ratingBar.getRating();


            DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Rating");
            dref.child(CurrentUser.getFirembaseUser()).child(book_name).child("Rating").setValue(getrating);
            tv.setText("Rating: "+getrating+"/"+noofstars);
        });



        final FirebaseDatabase database = FirebaseDatabase.getInstance();

       // final DatabaseReference ref2 = database.getReference("Downloaded Books");
       // final DatabaseReference ref3 = database.getReference("Books Read");
        final DatabaseReference ref = database.getReference("Users");
        final DatabaseReference ref4 = database.getReference("Books/Books/All Books");



        Intent i = getIntent();
        book_name = i.getExtras().getString("book_name");
        book_url = i.getExtras().getString("book_url");
        desc = i.getExtras().getString("description");
        image_url = i.getExtras().getString("img_url");


        bookname = findViewById(R.id.book_name_id);
        description= findViewById(R.id.book_desc_id);
        bookimg = findViewById(R.id.book_img_id);
        price = findViewById(R.id.book_price);

        description.setText(desc);
        bookname.setText(book_name);
        Picasso.get().load(image_url).into(bookimg);

        ref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              Long  a = dataSnapshot.child(book_name).child("price").getValue(Long.class);
              book_price = a.toString();
                price.setText("Price :"+book_price+" /-");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        Button Cartbtn = findViewById(R.id.addCart);
        Cartbtn.setOnClickListener(v -> {
            addtoCart();

        });



        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission ","You have permission");
            }else {
                Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(Book_info.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }


    }

    private void addtoCart() {

        String  saveDate;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        saveDate = dateFormat.format(cal.getTime());

        DatabaseReference cartList =FirebaseDatabase.getInstance().getReference().child("Cart");
        final HashMap <String, Object> cartMap = new HashMap<>();
        cartMap.put("book_name", book_name);
        cartMap.put("price", book_price);
        cartMap.put("quantity", qty);
        cartMap.put("date", saveDate);

        cartList.child(CurrentUser.getFirembaseUser()).child("Products").child(book_name).updateChildren(cartMap).addOnCompleteListener(task -> {
            Toast.makeText(Book_info.this, "Added to cart", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(Book_info.this, Homepage.class);
            startActivity(i);
        });



    }


    private void setSupportActionBar(Toolbar toolbar) {

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
