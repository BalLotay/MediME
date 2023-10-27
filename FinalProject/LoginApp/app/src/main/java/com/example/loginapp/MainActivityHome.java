package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivityHome extends AppCompatActivity {

    private Button buttonLogin;
    private Button buttonPatientRegister;
    private Button buttonDoctorRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        buttonLogin = (Button) findViewById(R.id.LogIn);
        buttonPatientRegister = (Button) findViewById(R.id.patientRegister);
        buttonDoctorRegister = (Button) findViewById(R.id.doctorRegister);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(MainActivityLogin.class);
            }
        });

        buttonPatientRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(MainActivityPatientRegister.class);
            }
        });

        buttonDoctorRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(MainActivityDoctorRegister.class);
            }
        });

    }

    public void openActivity(Class someClass) {
        Intent intent = new Intent(this, someClass);
        startActivity(intent);
    }

}