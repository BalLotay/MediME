package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;


import com.example.loginapp.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivityLogin extends AppCompatActivity {

    boolean isApproved = false;
    boolean isPending = false;
    boolean isFound = false;
    String personType = "Admin";
    String user;
    String pass;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        TextView username = findViewById(R.id.user);
        TextView password = findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtw);

        //admin and admin

        ValueEventListener checkData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    checker(snapshot, this);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();
                userRef.addValueEventListener(checkData);
            }
        });

    }
    public void checker(DataSnapshot snapshot, ValueEventListener event) {
        for (DataSnapshot person : snapshot.getChildren()) {
            String userTypeString = person.child("userType").getValue().toString();
            String username = person.child("firstName").getValue().toString();
            String password = person.child("accountPassword").getValue().toString();
            String status = person.child("status").getValue().toString();

            if (user.equals(username) && pass.equals(password)) {
                if (status.equals("approved")) {
                    isApproved = true;
                    personType = userTypeString;
                } else if (status.equals("pending")) {
                    isPending = true;
                }
                isFound = true;
                break;
            }
        }
        if (!isFound && user.equals("admin") && pass.equals("admin")) {
            isApproved = true;
        }
        if (isApproved) {
            userRef.removeEventListener(event);
            Toast.makeText(MainActivityLogin.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivityLogin.this, MainActivitySuccessfulLogin.class);
            intent.putExtra("person type", personType);
            startActivity(intent);
            finish();
        } else if (isPending) {
            Toast.makeText(MainActivityLogin.this, "REGISTRATION APPROVAL PENDING.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivityLogin.this,"LOGIN FAILED", Toast.LENGTH_SHORT).show();
        }
        userRef.removeEventListener(event);
    }
}