package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivitySeeUserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_see_user_info);

        TextView userTextView = findViewById(R.id.userTextView);
        TextView firstNameView = findViewById(R.id.firstNameView);
        TextView lastNameView = findViewById(R.id.lastNameView);
        TextView emailView = findViewById(R.id.emailView);
        TextView phoneView = findViewById(R.id.phoneView);
        TextView addressView = findViewById(R.id.addressView);
        TextView healthCardNumView = findViewById(R.id.healthCardNumView);
        TextView specialtiesView = findViewById(R.id.specialtiesView);
        LinearLayout specialtiesLayout = findViewById(R.id.specialtiesLayout);

        String[] personDetails = getIntent().getStringArrayExtra("person details");
        Toast.makeText(this, personDetails[0], Toast.LENGTH_SHORT).show();

            firstNameView.setText(personDetails[0]);
            lastNameView.setText(personDetails[1]);
            emailView.setText(personDetails[2]);
            phoneView.setText(personDetails[3]);
            addressView.setText(personDetails[4]);
            healthCardNumView.setText(personDetails[5]);



        if (personDetails[6] != null) {
            specialtiesLayout.setVisibility(View.VISIBLE);
            healthCardNumView.setText(personDetails[5]);
            specialtiesView.setText(personDetails[6]);
        }

                try {
        if(personDetails[7] != null) {
            userTextView.setText("Patient Details");
        }
                } catch (Exception e) {
                    Log.d("userinfo error", e.getMessage());
                }

    }
}