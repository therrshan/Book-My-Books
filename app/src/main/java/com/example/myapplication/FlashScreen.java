package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class FlashScreen extends AppCompatActivity {


    private final static int time = 1200;
    private SharedPreferences sharedPreferences;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashscreen);
        sharedPreferences = getSharedPreferences("user_uid",MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
        new Handler().postDelayed(() -> {
            if(uid!=null){
                CurrentUser.setFirembaseUser(uid);
                Intent i = new Intent(getApplicationContext(), Homepage.class);
                startActivity(i);

            }else {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);

            }
        },time);

    }

}
