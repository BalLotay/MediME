package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivitySeeShiftInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_see_shift_info);

        TextView dateView = findViewById(R.id.dateView);
        TextView startTimeView = findViewById(R.id.startTimeView);
        TextView endTimeView = findViewById(R.id.endTimeView);

        String[] shiftDetails = getIntent().getStringArrayExtra("shift details");

        String tempStartTime = String.format("%04d", Integer.parseInt(shiftDetails[1]));
        String tempEndTime = String.format("%04d", Integer.parseInt(shiftDetails[2]));

        String date = shiftDetails[0];
        String startTime = tempStartTime.substring(0, 2) + ":" + tempStartTime.substring(2, 4);
        String endTime = tempEndTime.substring(0, 2) + ":" + tempEndTime.substring(2, 4);

        dateView.setText(date);
        startTimeView.setText(startTime);
        endTimeView.setText(endTime);
    }
}