package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivityViewApplicants extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_applicants);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(10, 0, 0, 0);

        LinearLayout.LayoutParams params1 =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        180);

        params1.setMargins(0, 10, 0, 0);

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

                        if (status.equals("pending")) {
                            LinearLayout layout = new LinearLayout(MainActivityViewApplicants.this,null,0, R.style.ApplicantLinearLayout);
                            layout.setLayoutParams(params1);
                            TextView textView = new TextView(MainActivityViewApplicants.this,null,0,R.style.ApplicantNameView);
                            MaterialButton acceptButton = new MaterialButton(MainActivityViewApplicants.this, null);
                            MaterialButton rejectButton = new MaterialButton(MainActivityViewApplicants.this, null);
                            acceptButton.setText("Accept");
                            rejectButton.setText("Reject");
                            rejectButton.setLayoutParams(params);

                            textView.setText(firstAndLastName);
                            layout.addView(textView);
                            layout.addView(acceptButton);
                            layout.addView(rejectButton);
                            layoutScrollView.addView(layout);
                            textView.setClickable(true);

                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(MainActivityViewApplicants.this,"Works",Toast.LENGTH_SHORT).show();
                                }
                            });

                            acceptButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    layoutScrollView.removeView(layout);
                                    userRef.child(firstName).child("status").setValue("approved");
                                }
                            });
                            rejectButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    layoutScrollView.removeView(layout);
                                    userRef.child(firstName).child("status").setValue("rejected");
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