package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private EditText etmail,etpassword;
    private String email,password;
    private FirebaseAuth auth;
    FirebaseDatabase ref;
    DatabaseReference fdatabase;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etmail = findViewById(R.id.email);
        etpassword= (EditText)findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance();
        fdatabase = ref.getReference("Users");
    }

    @Override
    public void onBackPressed() {
            finishAffinity();
    }

    public void onClick(View view) {
        Intent i = new Intent(this, Registration.class);
        startActivity(i);
    }

    public void onChangePassword(View view){
        Intent i = new Intent(this, ForgotPassword.class);
        startActivity(i);
    }

    public void cardclick(View view){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
       try {
            email=etmail.getText().toString();
            password=etpassword.getText().toString();
           if(isNetworkAvailable()) {
           progressDialog.show();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String s = auth.getCurrentUser().getUid();
                                    fdatabase.child(s).child("password").setValue(password);
                                    sharedPreferences = getSharedPreferences("user_uid",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("uid",s);
                                    editor.apply();
                                    CurrentUser.setFirembaseUser(s);
                                    Intent i = new Intent(Login.this, Homepage.class);
                                    startActivity(i);
                                    progressDialog.dismiss();
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this, " Invalid data", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else{
                Toast.makeText(Login.this, "No Internet connection", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            progressDialog.dismiss();
            Toast.makeText(Login.this, "Please enter valid data", Toast.LENGTH_SHORT).show();
        }
        //FirebaseAuth.getInstance().signOut();
       // auth.signOut();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
