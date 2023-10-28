package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivitySuccessfulLogin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_successful_login);

        MaterialButton logOffButton = findViewById(R.id.logOffButton);
        MaterialButton viewApplicantsButton = findViewById(R.id.viewApplicantsButton);
        MaterialButton viewRejectedApplicantsButton = findViewById(R.id.viewRejectedApplicantsButton);
        TextView role = findViewById(R.id.role);

        String personType = getIntent().getStringExtra("person type");
        role.setText(personType);

        if (personType.equals("Admin")) {
            viewApplicantsButton.setVisibility(View.VISIBLE);
            viewRejectedApplicantsButton.setVisibility(View.VISIBLE);
        }

        viewApplicantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivitySuccessfulLogin.this, MainActivityViewApplicants.class);
                startActivity(intent);
            }
        });

        viewRejectedApplicantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivitySuccessfulLogin.this, MainActivityViewRejectedApplicants.class);
                startActivity(intent);
            }
        });


        logOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform logout actions if needed

                // Redirect to the login page
                Intent intent = new Intent(MainActivitySuccessfulLogin.this, MainActivityHome.class);
                startActivity(intent);
                finish(); // Close the current activity to prevent going back to it using the back button
            }
        });
    }
}
