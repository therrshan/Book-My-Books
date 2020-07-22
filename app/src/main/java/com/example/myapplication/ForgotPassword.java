package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText editText;
    String email;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        editText = (EditText)findViewById(R.id.email0);
        auth=FirebaseAuth.getInstance();
    }
    public  void sendclick(View view){
        email=editText.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        if(TextUtils.isEmpty(email)){
            progressDialog.dismiss();
            Toast.makeText(ForgotPassword.this, "Enter valid email id", Toast.LENGTH_SHORT).show();
        }else {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPassword.this, "Link Sent on your mail", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ForgotPassword.this,Login.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(ForgotPassword.this, "Email id doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
