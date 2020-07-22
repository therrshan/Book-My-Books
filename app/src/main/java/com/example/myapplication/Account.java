package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {
    private CardView btn ;
    private String uid,s;
    private User user;
    private TextView name,email,contact;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        uid = CurrentUser.getFirembaseUser();
        btn = findViewById(R.id.edit);
        name = findViewById(R.id.user_name);
        email = findViewById(R.id.email_id);
        contact = findViewById(R.id.mobile_no);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key =dataSnapshot.getKey();
                user = dataSnapshot.getValue(User.class);
                name.setText(user.getName());
                email.setText(user.getMail_id());
                contact.setText(user.getContact());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn.setOnClickListener(v -> {
            Intent i = new Intent(Account.this, Edit_Info.class);
            startActivity(i);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logoutbar, menu);
        return true;

    }
    public boolean logout_click(MenuItem item){
        sharedPreferences = getSharedPreferences("user_uid",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        CurrentUser.setFirembaseUser (null);
        Intent i = new Intent(Account.this, Login.class);
        startActivity(i);
        return true;
    }
}
