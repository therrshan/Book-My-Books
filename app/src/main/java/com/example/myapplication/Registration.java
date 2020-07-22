package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Registration extends AppCompatActivity {

    private DatabaseReference fdatabase;
    private EditText etname, etmail_id, etpassword, etcpassword, etcontact;
    private CardView button;
    private String name,email, password,contact,cpassword;
    private User user = new User();
    FirebaseDatabase ref;
    FirebaseAuth auth;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etname = findViewById(R.id.editText1);
        etmail_id = findViewById(R.id.editText2);
        etcontact = findViewById(R.id.editText3);
        etpassword = findViewById(R.id.editText4);
        etcpassword = findViewById(R.id.editText5);
        button = findViewById(R.id.button);

        final ProgressDialog progressDialog = new ProgressDialog(Registration.this);
        button.setOnClickListener(v -> {

            progressDialog.setMessage("Loading");
            try {

                getdata();
                Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                Matcher mat = pattern.matcher(user.getMail_id());
                if(isNetworkAvailable()) {
                    if (!user.getName().matches(".*\\d+.*") && !user.getName().isEmpty()) {
                        if(mat.matches()) {
                            if (user.getContact().length() == 10) {
                                if(user.getPassword().length()>=6) {
                                    if (password.equals(cpassword)) {
                                        progressDialog.show();
                                        auth.createUserWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(task -> {
                                                      if (task.isSuccessful()) {
                                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                                        String uid = firebaseUser.getUid();
                                                        sharedPreferences = getSharedPreferences("user_uid", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("uid", uid);
                                                        editor.apply();
                                                        CurrentUser.setFirembaseUser(uid);
                                                        fdatabase.child(uid).setValue(user);
                                                        Intent i = new Intent(Registration.this, Homepage.class);
                                                        startActivity(i);
                                                        progressDialog.dismiss();
                                                    } else {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(Registration.this, "Account already exists", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(Registration.this, "Password not matching", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(Registration.this, "Password should contain atleast 6 characters ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Registration.this, "Please enter valid contact no", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Registration.this, "Please enter valid email", Toast.LENGTH_SHORT).show();

                        }
                    }else {
                        Toast.makeText(Registration.this, "Please enter valid name", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Registration.this, "No Internet connection", Toast.LENGTH_SHORT).show();

                }
            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(Registration.this, "Please enter valid email ", Toast.LENGTH_SHORT).show();
            }
        });

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance();
        fdatabase = ref.getReference("Users");

    }

    public void getdata() {
        email = etmail_id.getText().toString();
        password = etpassword.getText().toString();
        name = etname.getText().toString();
        contact = etcontact.getText().toString();
        cpassword= etcpassword.getText().toString();
        user.setName(name);
        user.setMail_id(email);
        user.setPassword(password);
        user.setContact(contact);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Registration.this, Login.class);
        startActivity(i);


    }
}
