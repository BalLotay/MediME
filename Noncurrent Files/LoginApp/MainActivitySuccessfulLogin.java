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
        TextView role = findViewById(R.id.role);

        String personType = getIntent().getStringExtra("person type");
        role.setText(personType);


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
