package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import com.example.loginapp.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivityLogin extends AppCompatActivity {

    boolean isCorrect;
    String personType = "Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        TextView username = findViewById(R.id.user);
        TextView password = findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtw);


        //admin and admin

        loginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference patientRef = database.getReference("Patients");
                DatabaseReference doctorRef = database.getReference("Doctors");

                patientRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot patient : snapshot.getChildren()) {
                                String username = patient.getKey().toString();
                                String password = patient.child("accountPassword").getValue().toString();
                                String status = patient.child("status").getValue().toString();


                                if (user.equals(username) && pass.equals(password) && status.equals("approved")) {
                                   isCorrect = true;
                                   personType = "Patient";
                                   break;
                                }
                                else if (user.equals(username) && pass.equals(password) && status.equals("pending"))
                                {
                                    Toast.makeText(getApplicationContext(),"Approval pending, please try again later.",Toast.LENGTH_SHORT).show();
                                }
                                else if (user.equals(username) && pass.equals(password) && status.equals("rejected"))
                                {
                                    Toast.makeText(getApplicationContext(),"Rejected, contact (111)-111-1111 for inquires.",Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                doctorRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot doctor : snapshot.getChildren()) {
                                String username = doctor.getKey().toString();
                                String password = doctor.child("accountPassword").getValue().toString();
                                String status = doctor.child("status").getValue().toString();

                                if (user.equals(username) && pass.equals(password) && status.equals("approved")) {
                                    isCorrect = true;
                                    personType = "Doctor";
                                    break;
                                }
                                else if (user.equals(username) && pass.equals(password) && status.equals("pending"))
                                {
                                    Toast.makeText(getApplicationContext(),"Approval pending, please try again later.",Toast.LENGTH_SHORT).show();
                                }
                                else if (user.equals(username) && pass.equals(password) && status.equals("rejected"))
                                {
                                    Toast.makeText(getApplicationContext(),"Rejected, contact (111)-111-1111 for inquires.",Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                if (user.equals("admin") && pass.equals("admin") || isCorrect) {
                    // If correct
                    Toast.makeText(MainActivityLogin.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();

                    // Redirect to the login page
                    Intent intent = new Intent(MainActivityLogin.this, MainActivitySuccessfulLogin.class);
                    intent.putExtra("person type", personType);
                    startActivity(intent);
                    finish(); // Close the current activity to prevent going back to it using the back button
                } else
                    Toast.makeText(MainActivityLogin.this,"LOGIN FAILED", Toast.LENGTH_SHORT).show();
                    // If incorrect
            }
        });
    }
}