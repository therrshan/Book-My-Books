package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ConfirmOrder extends AppCompatActivity {

    Button confirm;
    EditText txtName, txtAddress, txtPhone;
    String name11, add11, phno11;
    Long a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        confirm = findViewById(R.id.confirmBtn);
        txtAddress = findViewById(R.id.txtAddress);
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);


        phno11 = txtPhone.getText().toString().trim();
        add11 = txtAddress.getText().toString().trim();
        name11 = txtName.getText().toString().trim();


        confirm.setOnClickListener(v -> {

            DatabaseReference Shipping = FirebaseDatabase.getInstance().getReference("ShipList");

            final HashMap<String, Object> shipList = new HashMap<>();
            shipList.put("Name", name11);
            shipList.put("Address", add11);
            shipList.put("Phone", phno11);

            Shipping.child(CurrentUser.getFirembaseUser()).child(name11).updateChildren(shipList).addOnCompleteListener(task -> {
                Toast.makeText(ConfirmOrder.this, "Information Added Sucessfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ConfirmOrder.this, PaymentActivity.class);
                startActivity(i);

            });

        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ConfirmOrder.this, CartActivity.class);
        Toast.makeText(this, "Purchase Cancelled", Toast.LENGTH_SHORT).show();
        startActivity(i);


    }
}
