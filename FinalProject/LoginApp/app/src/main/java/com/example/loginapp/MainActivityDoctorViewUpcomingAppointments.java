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

import java.util.List;

public class MainActivityDoctorViewUpcomingAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor_view_pending_appointments);

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
                        Log.d("doctorAppointments", appointment.toString());

                        String appointmentStatus = appointment.getStatus();

                        String firstName = appointment.getPatient();
                        Log.d("doctorAppointments", firstName);

                        String lastName = snapshot.child(firstName).child("lastName").getValue().toString();
                        String emailAddress = snapshot.child(firstName).child("emailAddress").getValue().toString();
                        String address = snapshot.child(firstName).child("address").getValue().toString();
                        String phoneNumber = snapshot.child(firstName).child("phoneNumber").getValue().toString();
                        String healthCardNumber = snapshot.child(firstName).child("healthCardNumber").getValue().toString();

                        String firstAndLastName = firstName + " " + lastName;

                        if (appointmentStatus.equals("pending")) {
                            LinearLayout layout = new LinearLayout(MainActivityDoctorViewUpcomingAppointments.this,null,0, R.style.ApplicantLinearLayout);
                            layout.setLayoutParams(params1);
                            TextView textView = new TextView(MainActivityDoctorViewUpcomingAppointments.this,null,0,R.style.ApplicantNameView);
                            MaterialButton acceptButton = new MaterialButton(MainActivityDoctorViewUpcomingAppointments.this, null);
                            MaterialButton rejectButton = new MaterialButton(MainActivityDoctorViewUpcomingAppointments.this, null);
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
                                    Intent intent = new Intent(MainActivityDoctorViewUpcomingAppointments.this, MainActivitySeeUserInfo.class);
                                    String[] personDetails = {firstName, lastName, emailAddress, phoneNumber, address, healthCardNumber, null, "doctorViewUpcomingAppointments"};
                                    intent.putExtra("person details", personDetails);
                                    startActivity(intent);
                                }
                            });

                            acceptButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    appointment.setStatus("approved");
                                    doctorAppointments.remove(appointment);
                                    doctorAppointments.add(appointment);
                                    userRef.child(doctorUsername).child("appointments").setValue(doctorAppointments);

                                    List<Appointment> patientAppointments = snapshot.child(firstName).child("appointments").getValue(temp);
                                    patientAppointments.remove(patientAppointments.size()-1);
                                    patientAppointments.add(appointment);
                                    userRef.child(firstName).child("appointments").setValue(patientAppointments);

                                    layoutScrollView.removeView(layout);
                                }
                            });
                            rejectButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    layoutScrollView.removeView(layout);

                                    appointment.setStatus("rejected");
                                    doctorAppointments.remove(appointment);
                                    doctorAppointments.add(appointment);
                                    userRef.child(doctorUsername).child("appointments").setValue(doctorAppointments);

                                    List<Appointment> patientAppointments = snapshot.child(firstName).child("appointments").getValue(temp);
                                    patientAppointments.remove(appointment);
                                    patientAppointments.add(appointment);
                                    userRef.child(firstName).child("appointments").setValue(patientAppointments);
                                }
                            });

                        } else if (appointmentStatus.equals("approved")) {
                            LinearLayout layout = new LinearLayout(MainActivityDoctorViewUpcomingAppointments.this,null,0, R.style.ApplicantLinearLayout);
                            layout.setLayoutParams(paramsLinearLayout);
                            TextView textView = new TextView(MainActivityDoctorViewUpcomingAppointments.this,null,0,R.style.ApplicantNameView);
                            MaterialButton cancelButton = new MaterialButton(MainActivityDoctorViewUpcomingAppointments.this, null);
                            cancelButton.setText("Cancel");
                            textView.setLayoutParams(paramsTextView);
                            layout.setPadding(5,5,50,5);
                            textView.setText(firstAndLastName);
                            layout.addView(textView);
                            layout.addView(cancelButton);
                            layoutScrollView.addView(layout);
                            textView.setClickable(true);

                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MainActivityDoctorViewUpcomingAppointments.this, MainActivitySeeUserInfo.class);
                                    String[] personDetails = {firstName, lastName, emailAddress, phoneNumber, address, healthCardNumber, null, "viewPendingAppointments"};
                                    intent.putExtra("person details", personDetails);
                                    startActivity(intent);
                                }
                            });

                            cancelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    appointment.setStatus("cancelled");
                                    doctorAppointments.remove(appointment);
                                    doctorAppointments.add(appointment);
                                    userRef.child(doctorUsername).child("appointments").setValue(doctorAppointments);

                                    List<Appointment> patientAppointments = snapshot.child(firstName).child("appointments").getValue(temp);
                                    patientAppointments.remove(patientAppointments.size()-1);
                                    patientAppointments.add(appointment);
                                    userRef.child(firstName).child("appointments").setValue(patientAppointments);

                                    layoutScrollView.removeView(layout);
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