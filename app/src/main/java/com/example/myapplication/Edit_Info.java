package com.example.myapplication;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Info extends AppCompatActivity {
private CardView btn;
private EditText etname,etcontact;
String name,contact;
User user,changeuser;
String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__info);

        uid =CurrentUser.getFirembaseUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Users").child(uid);
        btn = findViewById(R.id.save);
        etname = findViewById(R.id.changename);
        etcontact = findViewById(R.id.changecontact);

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key =dataSnapshot.getKey();
                user = dataSnapshot.getValue(User.class);
                etname.setText(user.getName());
                etcontact.setText(user.getContact());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(v -> {
            changeuser = user;
            getdata();
            ref.setValue(changeuser);
            Toast.makeText(Edit_Info.this, "Saved", Toast.LENGTH_SHORT).show();
        });

    }
    public void getdata() {

        name = etname.getText().toString();
        contact = etcontact.getText().toString();
        changeuser.setName(name);
        changeuser.setContact(contact);
    }
}
