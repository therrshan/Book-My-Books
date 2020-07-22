package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {


    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private Button buy;
    private TextView total;
    private int totalPrice = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rv = findViewById(R.id.cartList);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);


        buy = findViewById(R.id.buyNow);
        total = findViewById(R.id.totalPrice);

        buy.setOnClickListener(v -> {
            Intent i = new Intent(this, ConfirmOrder.class);
            startActivity(i);

        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListref =FirebaseDatabase.getInstance().getReference("Cart");

        FirebaseRecyclerOptions <cart> options = new FirebaseRecyclerOptions.Builder<cart>().setQuery(cartListref.child(CurrentUser.getFirembaseUser()).child("Products"), cart.class).build();


        FirebaseRecyclerAdapter <cart, cartViewHolder> adapter = new FirebaseRecyclerAdapter<cart, cartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull cartViewHolder holder, int i, @NonNull cart cart) {

                holder.prodQty.setText("Quantity: "+cart.getQuantity());
                holder.prodName.setText(cart.getBook_name());
                holder.prodPrice.setText("Price: "+cart.getPrice()+"/-");

                int individualPrice = (Integer.valueOf(cart.getPrice()))*(Integer.valueOf(cart.getQuantity()));
                totalPrice = totalPrice + individualPrice;

                total.setText("TotalPrice:"+totalPrice+"/-");

                holder.itemView.setOnClickListener(v -> {

                    CharSequence options[] = new CharSequence[]{

                            "Remove"
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle("Cart Options");

                    builder.setItems(options, (dialog, which) -> {

                        if(which == 0){
                            cartListref.child(CurrentUser.getFirembaseUser()).child("Products").child(cart.getBook_name()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(CartActivity.this, "Removed form Cart", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    });

                    builder.show();

                });



            }


            @NonNull
            @Override
            public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view_list, parent, false);
                cartViewHolder holder = new cartViewHolder(view);
                return holder;
            }
        };

        rv.setAdapter(adapter);
        adapter.startListening();

    }

}
