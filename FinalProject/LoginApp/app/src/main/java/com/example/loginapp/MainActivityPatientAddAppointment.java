package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivityPatientAddAppointment extends AppCompatActivity {

    private EditText dateEditText;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private EditText doctorEditText;
    private Button addAppointment;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");

    List<Appointment> doctorAppointments;
    List<Appointment> patientAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_patient_add_appointment);

        dateEditText = findViewById(R.id.date);
        startTimeEditText = findViewById(R.id.startTime);
        endTimeEditText = findViewById(R.id.endTime);
        doctorEditText = findViewById(R.id.doctor);
        addAppointment = findViewById(R.id.addAppointment);
        String username = getIntent().getStringExtra("Username");

        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doctorUsername = doctorEditText.getText().toString();

//                String dateText = dateEditText.getText().toString();
//                Date dateObject = new Date(2023,10,23);
//            @SuppressLint("NewApi")
//            LocalDate dateObject = LocalDate.parse("2007-12-03");
//                String startTimeText = startTimeEditText.getText().toString();
//                String endTimeText = endTimeEditText.getText().toString();

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            GenericTypeIndicator<List<Appointment>> temp = new GenericTypeIndicator<List<Appointment>>(){};
                            patientAppointments = snapshot.child(username).child("appointments").getValue(temp);
                            doctorAppointments = snapshot.child(doctorUsername).child("appointments").getValue(temp);
                            Log.d("Appointment huh object patient", patientAppointments.toString());
                            Log.d("Appointment huh object doctor", doctorAppointments.toString());
                        }
                        catch(Exception e){
                            Log.d("Appointment object error", e.getMessage());
                        }

                        Appointment newAppointment = new Appointment(username, doctorUsername, "11/11/2023", "11:00", "11:30");
                        doctorAppointments.add(newAppointment);
                        patientAppointments.add(newAppointment);
                        userRef.child(username).child("appointments").setValue(patientAppointments);
                        userRef.child(doctorUsername).child("appointments").setValue(doctorAppointments);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}