package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivityViewRejectedApplicants extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_rejected_applicants);

        LinearLayout.LayoutParams paramsTextView =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f);

        LinearLayout.LayoutParams paramsLinearLayout =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        180);

        paramsLinearLayout.setMargins(0, 10, 0, 0);

        LinearLayout layoutScrollView = findViewById(R.id.layoutInScrollView);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Users");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    layoutScrollView.removeAllViews();
                    for (DataSnapshot person : snapshot.getChildren()) {
                        String firstName = person.getKey();
                        String username = person.child("emailAddress").getValue().toString();
                        String lastName = person.child("lastName").getValue().toString();
                        String status = person.child("status").getValue().toString();
                        String firstAndLastName = firstName + " " + lastName;

                        if (status.equals("rejected")) {
                            LinearLayout layout = new LinearLayout(MainActivityViewRejectedApplicants.this,null,0, R.style.ApplicantLinearLayout);
                            layout.setLayoutParams(paramsLinearLayout);
                            TextView textView = new TextView(MainActivityViewRejectedApplicants.this,null,0,R.style.ApplicantNameView);
                            MaterialButton acceptButton = new MaterialButton(MainActivityViewRejectedApplicants.this, null);
                            acceptButton.setText("Accept");
                            textView.setLayoutParams(paramsTextView);
                            layout.setPadding(5,5,50,5);
                            textView.setText(firstAndLastName);
                            layout.addView(textView);
                            layout.addView(acceptButton);
                            layoutScrollView.addView(layout);
                            textView.setClickable(true);
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivityViewRejectedApplicants.this)
                                    .setContentTitle("Notification")
                                    .setContentText("Request has been rejected");

                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(0, builder.build());

                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MainActivityViewRejectedApplicants.this, MainActivitySeeUserInfo.class);
                                    String phoneStr = person.child("phoneNumber").getValue().toString();
                                    String addressStr = person.child("address").getValue().toString();
                                    String personTypeStr = person.child("userType").getValue().toString();
                                    String[] personDetails = {firstName, lastName, username, phoneStr, addressStr, null, null};
                                    if (personTypeStr.equals("Doctor")) {
                                        String specialties = person.child("specialties").getValue().toString();
                                        String employeeNumStr = person.child("employeeNumber").getValue().toString();
                                        personDetails[5] = employeeNumStr;
                                        personDetails[6] = specialties;
                                    } else {
                                        String healthNumStr = person.child("healthCardNumber").getValue().toString();
                                        personDetails[5] = healthNumStr;
                                    }
                                    intent.putExtra("person details", personDetails);
                                    startActivity(intent);
                                }
                            });

                            acceptButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    layoutScrollView.removeView(layout);
                                    userRef.child(firstName).child("status").setValue("approved");
                                }
                            });

                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
