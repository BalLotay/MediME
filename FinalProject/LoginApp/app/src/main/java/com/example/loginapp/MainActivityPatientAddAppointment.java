package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
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
import com.wdullaer.materialdatetimepicker.time.Timepoint;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivityPatientAddAppointment extends AppCompatActivity {

    private Button selectDateButton;
    private Button selectStartTimeButton;
    private Button selectEndTimeButton;

    private Button confirmButton;
    private EditText doctorEditText;

    private Calendar selectedDate;
    private String date;
    private Calendar selectedStartTime;
    private Calendar selectedEndTime;
    private Button addAppointment;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");

    List<Appointment> doctorAppointments;
    List<Appointment> patientAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_patient_add_appointment);

        selectDateButton = findViewById(R.id.select);
        selectStartTimeButton = findViewById(R.id.start);
        selectEndTimeButton = findViewById(R.id.end);
        confirmButton = findViewById(R.id.addAppointmentButton);

        doctorEditText = findViewById(R.id.doctor);
        addAppointment = findViewById(R.id.addAppointment);
        String username = getIntent().getStringExtra("Username");

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        selectStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(true);
            }
        });

        selectEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(false);
            }
        });

        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doctorUsername = doctorEditText.getText().toString();

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // Create and handle the appointment object using the selectedDate, selectedStartTime, and selectedEndTime
                        if (selectedDate != null && selectedStartTime != null && selectedEndTime != null) {
                            int startHour = 100 * selectedStartTime.get(Calendar.HOUR_OF_DAY);
                            int startMinute = selectedStartTime.get(Calendar.MINUTE);

                            int endHour = 100 * selectedEndTime.get(Calendar.HOUR_OF_DAY);
                            int endMinute = selectedEndTime.get(Calendar.MINUTE);

                            int start = startHour + startMinute;
                            int end = endHour + endMinute;

                            GenericTypeIndicator<List<Appointment>> temp = new GenericTypeIndicator<List<Appointment>>(){};
                            patientAppointments = snapshot.child(username).child("appointments").getValue(temp);
                            doctorAppointments = snapshot.child(doctorUsername).child("appointments").getValue(temp);

                            Appointment newAppointment = new Appointment(username, doctorUsername, date, String.valueOf(start), String.valueOf(end));
                            doctorAppointments.add(newAppointment);
                            patientAppointments.add(newAppointment);
                            userRef.child(username).child("appointments").setValue(patientAppointments);
                            userRef.child(doctorUsername).child("appointments").setValue(doctorAppointments);
                        } else {
                            // Handle case where not all input data is selected.
                            Toast.makeText(MainActivityPatientAddAppointment.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);

                // Format the selected date as desired (e.g., "MM/dd/yyyy")
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                String formattedDate = dateFormat.format(selectedDate.getTime());
                date = formattedDate;
                // Update the date button text
                selectDateButton.setText(formattedDate); //Shift("10/11/2023", 400, 800, "s1")
            }
        }, year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }

    private void showTimePickerDialog(final boolean isStartTime) {
        Calendar now = Calendar.getInstance();

        if (selectedDate != null) {
            now = selectedDate;
        }

        // Initialize the time picker with the current time or the selected time, if available
        Timepoint initialTime = new Timepoint(
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE)
        );

        if (isStartTime && selectedStartTime != null) {
            initialTime = new Timepoint(
                    selectedStartTime.get(Calendar.HOUR_OF_DAY),
                    selectedStartTime.get(Calendar.MINUTE)
            );
        } else if (!isStartTime && selectedEndTime != null) {
            initialTime = new Timepoint(
                    selectedEndTime.get(Calendar.HOUR_OF_DAY),
                    selectedEndTime.get(Calendar.MINUTE)
            );
        }

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        Calendar selectedTime = Calendar.getInstance();
                        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedTime.set(Calendar.MINUTE, minute);

                        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                        String formattedTime = timeFormat.format(selectedTime.getTime());

                        if (isStartTime) {
                            selectedStartTime = selectedTime;
                            selectStartTimeButton.setText(formattedTime);
                        } else {
                            selectedEndTime = selectedTime;
                            selectEndTimeButton.setText(formattedTime);
                        }
                    }
                },
                initialTime.getHour(), // Initial hour
                initialTime.getMinute(), // Initial minute
                false // Use 24-hour format (false for AM/PM)
        );

        // Set the 30-minute time interval
        timePickerDialog.setTimeInterval(1, 30);

        // Show the time picker dialog
        timePickerDialog.show(getSupportFragmentManager(), "TimePickerDialog");
    }

}