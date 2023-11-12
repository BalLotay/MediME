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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivityDoctorViewApprovedAppointments extends AppCompatActivity {

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

                        if (appointmentStatus.equals("approved")) {
                            LinearLayout layout = new LinearLayout(MainActivityDoctorViewApprovedAppointments.this,null,0, R.style.ApplicantLinearLayout);
                            layout.setLayoutParams(paramsLinearLayout);
                            TextView textView = new TextView(MainActivityDoctorViewApprovedAppointments.this,null,0,R.style.ApplicantNameView);
                            MaterialButton cancelButton = new MaterialButton(MainActivityDoctorViewApprovedAppointments.this, null);
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
                                    Intent intent = new Intent(MainActivityDoctorViewApprovedAppointments.this, MainActivitySeeUserInfo.class);
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