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

public class MainActivityPatientViewPastAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_patient_view_past_appointments);

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

        String patientUsername = getIntent().getStringExtra("patientUsername");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Users");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    layoutScrollView.removeAllViews();

                    List<Appointment> patientAppointments;

                    GenericTypeIndicator<List<Appointment>> temp = new GenericTypeIndicator<List<Appointment>>(){};
                    patientAppointments = snapshot.child(patientUsername).child("appointments").getValue(temp);

                    for (int i = 1; i < patientAppointments.size(); i++) {   // First appointment (at index 0) is a dummy
                        Appointment appointment = patientAppointments.get(i);

                        String appointmentStatus = appointment.getStatus();

                        String firstName = appointment.getDoctor();

                        String lastName = snapshot.child(firstName).child("lastName").getValue().toString();
                        String emailAddress = snapshot.child(firstName).child("emailAddress").getValue().toString();
                        String address = snapshot.child(firstName).child("address").getValue().toString();
                        String phoneNumber = snapshot.child(firstName).child("phoneNumber").getValue().toString();
                        String specialties = snapshot.child(firstName).child("specialties").getValue().toString();
                        String employeeNumStr = snapshot.child(firstName).child("employeeNumber").getValue().toString();

                        String firstAndLastName = firstName + " " + lastName;
                        boolean isPastAppointment = false;

                        isPastAppointment = appointment.isPastAppointment();

                        if (appointmentStatus.equals("approved") && isPastAppointment) {
                            LinearLayout layout = new LinearLayout(MainActivityPatientViewPastAppointments.this,null,0, R.style.ApplicantLinearLayout);
                            layout.setLayoutParams(paramsLinearLayout);
                            TextView textView = new TextView(MainActivityPatientViewPastAppointments.this,null,0,R.style.ApplicantNameView);
                            textView.setLayoutParams(paramsTextView);
                            layout.setPadding(5,5,50,5);
                            textView.setText(firstAndLastName);
                            layout.addView(textView);
                            layoutScrollView.addView(layout);
                            textView.setClickable(true);

                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MainActivityPatientViewPastAppointments.this, MainActivitySeeUserInfo.class);
                                    String[] personDetails = {firstName, lastName, emailAddress, phoneNumber, address, employeeNumStr, specialties, "patientViewPastAppointments"};
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