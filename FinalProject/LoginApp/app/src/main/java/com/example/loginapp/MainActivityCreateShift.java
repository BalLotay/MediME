package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



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


public class MainActivityCreateShift extends AppCompatActivity {

    private Button selectDateButton;
    private Button selectStartTimeButton;
    private Button selectEndTimeButton;

    private Button confirmButton;

    private Calendar selectedDate;
    private String date;
    private Calendar selectedStartTime;
    private Calendar selectedEndTime;

    private Doctor doctor;

    private List<Shift> shifts;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_create_shift);

        selectDateButton = findViewById(R.id.select);
        selectStartTimeButton = findViewById(R.id.start);
        selectEndTimeButton = findViewById(R.id.end);
        confirmButton = findViewById(R.id.confirm);

        String user = getIntent().getStringExtra("user");
        username = user;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Users");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<Shift>> temp = new GenericTypeIndicator<List<Shift>>(){};
                shifts = snapshot.child(username).child("shifts").getValue(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and handle the shift object using the selectedDate, selectedStartTime, and selectedEndTime
                if (selectedDate != null && selectedStartTime != null && selectedEndTime != null) {
                    int startHour = 100*selectedStartTime.get(Calendar.HOUR_OF_DAY);
                    int startMinute = selectedStartTime.get(Calendar.MINUTE);

                    int endHour = 100*selectedEndTime.get(Calendar.HOUR_OF_DAY);
                    int endMinute = selectedEndTime.get(Calendar.MINUTE);

                    int start = startHour + startMinute;
                    int end= endHour + endMinute;

                    boolean conflicts = false;

                    Shift newShift = new Shift(date,String.valueOf(start),String.valueOf(end), username);
                    /*
                    shifts.add(newShift);
                    //add to database (replace list with updated)
                    userRef.child(username).child("shifts").setValue(shifts);

                     */


                    if(!newShift.isValid()){
                        //invalid shift toast
                        Toast.makeText(MainActivityCreateShift.this, "Invalid Shift Entered, check time entered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                         for (int i = 1; i < shifts.size(); i++){
                            if(newShift.conflictsWith(shifts.get(i))){
                                conflicts = true;
                            }
                        }
                        if (!conflicts){
                            shifts.add(newShift);
                            //add to database (replace list with updated)
                            userRef.child(username).child("shifts").setValue(shifts);
                            Toast.makeText(MainActivityCreateShift.this, "Shift added", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            //conflicts toast
                            Toast.makeText(MainActivityCreateShift.this, "Cannot add shift due to time conflict", Toast.LENGTH_SHORT).show();
                        }
                    }






                } else {
                    // Handle case where not all input data is selected.
                }
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

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

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