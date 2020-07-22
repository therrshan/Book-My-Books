package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedback extends AppCompatActivity {
    private Button btn;
    private String uid,name;
    private com.example.myapplication.User user;
    private EditText etfeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        uid =CurrentUser.getFirembaseUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Users").child(uid);
        final DatabaseReference ref2 = database.getReference("Feedback");
        btn = findViewById(R.id.submit);
        etfeedback = findViewById(R.id.feedback);

        ref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key =dataSnapshot.getKey();
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(v -> {
            ref2.child(user.getName()).push().setValue(etfeedback.getText().toString());
            Toast.makeText(Feedback.this, "Submitted", Toast.LENGTH_SHORT).show();
            etfeedback.setText("");

        });
    }
}
