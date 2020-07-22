package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private EditText searchtext;
    private DatabaseReference ref;
    private static int count;
    RecyclerView rv;
    ArrayList<Book> searchlist =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        count=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ref= FirebaseDatabase.getInstance().getReference("Books/Books/All Books");
        rv = findViewById(R.id.search_rv);

        searchtext = findViewById(R.id.search);

        searchtext.setOnClickListener(v -> searchtext.setText(""));
        search("");
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    search(s.toString());
                }else {
                    search("");
                }

            }
        });
    }

    private void search(String s) {
         Query query = ref.orderByChild("name").startAt(s).endAt(s+"\uf8ff");
                    if(count==1){
                        ((Recyclerview_adapter)rv.getAdapter()).clear();
                    }
                    query.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            //  String name=dataSnapshot.getKey();
                            Book b1 =dataSnapshot.getValue(Book.class);
                            ((Recyclerview_adapter)rv.getAdapter()).update(b1);
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

                    Recyclerview_adapter adapter =new Recyclerview_adapter(rv,Search.this,searchlist);
                    rv.setLayoutManager(new GridLayoutManager(Search.this,2));
                    rv.setAdapter(adapter);
                    count=1;
    }
}
