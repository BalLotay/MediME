package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivityListAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_appointments);

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

        String search = getIntent().getStringExtra("specialty");
        String date = getIntent().getStringExtra("date");
        List<Shift> shiftsToView = new ArrayList<Shift>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Users");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (snapshot.child("userType").getValue(String.class).equals("Doctor")) {

                        GenericTypeIndicator<List<String>> temp = new GenericTypeIndicator<List<String>>() {
                        };
                        List<String> specialties = snapshot.child("specialties").getValue(temp);

                        // Check if the user has the input string in their specialties
                        for (String specialty : specialties) {

                            if (specialty.equals(search)) {
                                GenericTypeIndicator<List<Shift>> listShift = new GenericTypeIndicator<List<Shift>>() {
                                };
                                List<Shift> allShifts = snapshot.child("shifts").getValue(listShift);

                                for (Shift shift : allShifts){
                                    if (shift.getDate().equals(date)){
                                        shiftsToView.add(shift);
                                    }
                                }
                            }
                        }
                    }
                }
                //now we have the list of shifts, view it
                for (Shift shift : shiftsToView) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HHmm");

                    try {
                        Calendar startTime = Calendar.getInstance();
                        startTime.setTime(sdf.parse(shift.getStartTime()));

                        Calendar endTime = Calendar.getInstance();
                        endTime.setTime(sdf.parse(shift.getEndTime()));

                        Calendar currentTime = (Calendar) startTime.clone();

                        // Get appointments at 30-minute intervals
                        while (currentTime.before(endTime)) {
                            Calendar nextTime = (Calendar) currentTime.clone();
                            nextTime.add(Calendar.MINUTE, 30);

                            SimpleDateFormat outputFormat = new SimpleDateFormat("hhmm");

                            String startView = outputFormat.format(currentTime.getTime());
                            String endView = outputFormat.format(nextTime.getTime());
                            String dateView = date;
                            String docView = shift.getDoctor();
                            boolean flag = true;

                            //CHECK IF VALID
                            //for appointments in docusername.
                            GenericTypeIndicator<List<Appointment>> tempo = new GenericTypeIndicator<List<Appointment>>(){};
                            List<Appointment>docAppointmentList = dataSnapshot.child(docView).child("appointments").getValue(tempo);
                            //if appointment.date = date and appointmnet.starttime = startview -> continue
                            for (Appointment app : docAppointmentList){
                                if (app.getDate().equals(date) && app.startTime.equals(startView)){
                                    flag = false;
                                }
                            }
                            if (flag){
                                //insert layoutview here
                                LinearLayout layout = new LinearLayout(MainActivityListAppointments.this,null,0, R.style.ApplicantLinearLayout);
                                layout.setLayoutParams(paramsLinearLayout);
                                TextView textView = new TextView(MainActivityListAppointments.this,null,0,R.style.ApplicantNameView);
                                MaterialButton addButton = new MaterialButton(MainActivityListAppointments.this, null);
                                addButton.setText("Add");
                                textView.setLayoutParams(paramsTextView);
                                layout.setPadding(5,5,50,5);
                                textView.setText(startView + "-"+ endView+ "| Doc: "+docView);
                                layout.addView(textView);
                                layout.addView(addButton);
                                layoutScrollView.addView(layout);


                                addButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String username = getIntent().getStringExtra("user");
                                        GenericTypeIndicator<List<Appointment>> temp = new GenericTypeIndicator<List<Appointment>>(){};
                                        List<Appointment>patientAppointments = dataSnapshot.child(username).child("appointments").getValue(temp);
                                        List<Appointment>doctorAppointments = dataSnapshot.child(docView).child("appointments").getValue(temp);

                                        Appointment newAppointment = new Appointment(username, docView, date, startView, endView);

                                        boolean autoAcceptStatus = (boolean) dataSnapshot.child(docView).child("autoAcceptStatus").getValue();
                                        if (autoAcceptStatus) {
                                            newAppointment.setStatus("approved");
                                            Toast.makeText(MainActivityListAppointments.this, "Appointment accepted!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MainActivityListAppointments.this, "Appointment request sent", Toast.LENGTH_SHORT).show();
                                        }

                                        doctorAppointments.add(newAppointment);
                                        patientAppointments.add(newAppointment);
                                        userRef.child(username).child("appointments").setValue(patientAppointments);
                                        userRef.child(docView).child("appointments").setValue(doctorAppointments);
                                    }
                                });
                            }
                            //continue loop
                            currentTime = nextTime;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors or onCancelled events
                Log.e("Firebase Error", "Error: " + databaseError.getMessage());
            }
        });
    }
}