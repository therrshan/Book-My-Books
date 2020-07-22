package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends AppCompatActivity {

    Button  cod;
    final DatabaseReference cartListref = FirebaseDatabase.getInstance().getReference("Cart");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        cod = findViewById(R.id.COD);


        cod.setOnClickListener(v -> {

            cartListref.child(CurrentUser.getFirembaseUser()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(PaymentActivity.this, "Order Placed Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PaymentActivity.this, Homepage.class);
                    startActivity(i);

                }
            });


        });

    }
}
