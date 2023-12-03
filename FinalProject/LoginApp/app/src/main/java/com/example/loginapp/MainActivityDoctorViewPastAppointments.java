package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.List;

public class MainActivityDoctorViewPastAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor_view_approved_appointments);

        LinearLayout.LayoutParams paramsTextView =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f);

        paramsTextView.setMargins(10, 0, 0, 0);

        LinearLayout.LayoutParams paramsLinearLayout =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        180);

        paramsLinearLayout.setMargins(0, 10, 0, 0);

        LinearLayout layoutScrollView = findViewById(R.id.layoutInScrollView);

        String doctorUsername = getIntent().getStringExtra("doctorUsername");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Users");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    layoutScrollView.removeAllViews();

                    List<Appointment> doctorAppointments;

                    GenericTypeIndicator<List<Appointment>> temp = new GenericTypeIndicator<List<Appointment>>(){};
                    doctorAppointments = snapshot.child(doctorUsername).child("appointments").getValue(temp);

                    for (int i = 1; i < doctorAppointments.size(); i++) {   // First appointment (at index 0) is a dummy
                        Appointment appointment = doctorAppointments.get(i);

                        String appointmentStatus = appointment.getStatus();

                        String firstName = appointment.getPatient();

                        String lastName = snapshot.child(firstName).child("lastName").getValue().toString();
                        String emailAddress = snapshot.child(firstName).child("emailAddress").getValue().toString();
                        String address = snapshot.child(firstName).child("address").getValue().toString();
                        String phoneNumber = snapshot.child(firstName).child("phoneNumber").getValue().toString();
                        String healthCardNumber = snapshot.child(firstName).child("healthCardNumber").getValue().toString();

                        String firstAndLastName = firstName + " " + lastName;
                        boolean isPastAppointment = false;

                        isPastAppointment = appointment.isPastAppointment();

                        if (appointmentStatus.equals("approved") && isPastAppointment) {
                            LinearLayout layout = new LinearLayout(MainActivityDoctorViewPastAppointments.this,null,0, R.style.ApplicantLinearLayout);
                            layout.setLayoutParams(paramsLinearLayout);
                            TextView textView = new TextView(MainActivityDoctorViewPastAppointments.this,null,0,R.style.ApplicantNameView);
                            textView.setLayoutParams(paramsTextView);
                            layout.setPadding(5,5,50,5);
                            textView.setText(firstAndLastName);
                            layout.addView(textView);
                            layoutScrollView.addView(layout);
                            textView.setClickable(true);

                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MainActivityDoctorViewPastAppointments.this, MainActivitySeeUserInfo.class);
                                    String[] personDetails = {firstName, lastName, emailAddress, phoneNumber, address, healthCardNumber, null, "doctorViewAppointments"};
                                    intent.putExtra("person details", personDetails);
                                    startActivity(intent);
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