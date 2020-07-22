package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Categories extends AppCompatActivity {

    ImageButton m , s, r, t, tech, f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        m = findViewById(R.id.marathiimg);
        s = findViewById(R.id.selfhelp);
        r = findViewById(R.id.romanceimg);
        t = findViewById(R.id.thriller);
        tech = findViewById(R.id.technical);
        f = findViewById(R.id.fictionimg);

        m.setOnClickListener(v -> {
            Intent intent = new Intent(this,Marathi.class);
            startActivity(intent);

        });

        s.setOnClickListener(v -> {
            Intent intent1 = new Intent(this,Selfhelp.class);
            startActivity(intent1);

        });

        r.setOnClickListener(v -> {
            Intent intent1 = new Intent(this,Romance.class);
            startActivity(intent1);

        });

        t.setOnClickListener(v -> {
            Intent intent1 = new Intent(this,Thriller.class);
            startActivity(intent1);

        });

        tech.setOnClickListener(v -> {
            Intent intent1 = new Intent(this,Fiction.class);
            startActivity(intent1);

        });

        f.setOnClickListener(v -> {
            Intent intent1 = new Intent(this,Fiction.class);
            startActivity(intent1);

        });


    }


}
