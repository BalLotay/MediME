package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.accessibility.AccessibilityViewCommand;

import android.media.tv.TvContentRating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

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
        TextView healthCardNum = findViewById(R.id.healthCardNum);
        TextView healthCardNumView = findViewById(R.id.healthCardNumView);
        TextView specialtiesView = findViewById(R.id.specialtiesView);
        LinearLayout healthCardNumLayout = findViewById(R.id.healthCardNumLayout);
        LinearLayout specialtiesLayout = findViewById(R.id.specialtiesLayout);
        LinearLayout ratingLayout = findViewById(R.id.ratingLayout);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        MaterialButton submitButton = (MaterialButton) findViewById(R.id.submitButton);

        String[] personDetails = getIntent().getStringArrayExtra("person details");

        firstNameView.setText(personDetails[0]);
        lastNameView.setText(personDetails[1]);
        emailView.setText(personDetails[2]);
        phoneView.setText(personDetails[3]);
        addressView.setText(personDetails[4]);
        healthCardNumView.setText(personDetails[5]);


        // If is Doctor
        // personDetails[6] = specialties
        if (personDetails[6] != null) {
            specialtiesLayout.setVisibility(View.VISIBLE);
            healthCardNum.setText("Employee Number");
            healthCardNumView.setText(personDetails[5]);
            specialtiesView.setText(personDetails[6]);

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference userRef = database.getReference("Users");

                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Integer> ratings;
                            GenericTypeIndicator<List<Integer>> temp = new GenericTypeIndicator<List<Integer>>(){};
                            ratings = snapshot.child(personDetails[0]).child("ratings").getValue(temp);

                            ratings.add((int) ratingBar.getRating());
                            userRef.child(personDetails[0]).child("ratings").setValue(ratings);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    Toast.makeText(MainActivitySeeUserInfo.this, "Your rating has been submitted!", Toast.LENGTH_SHORT).show();
                }
            });

        }


            String actionToPerform = personDetails[7];

            if (actionToPerform != null) {

                // See patient details as doctor
                Log.d("actiontoperform", actionToPerform);
                if (actionToPerform.equals("doctorViewAppointments")) {
                    userTextView.setText("Patient Details");
                }

                // See doctor details as patient
                if (actionToPerform.equals("patientViewPastAppointments")) {
                    userTextView.setText("Doctor Details");
                    ratingLayout.setVisibility(View.VISIBLE);
                }

                if (actionToPerform.equals("patientViewUpcomingAppointments")) {
                    userTextView.setText("Doctor Details");
                }
            }



    }
}