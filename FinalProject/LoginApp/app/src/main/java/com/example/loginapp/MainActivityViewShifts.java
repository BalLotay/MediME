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

public class MainActivityViewShifts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_shifts);

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

                    List<Shift> doctorShifts;

                    GenericTypeIndicator<List<Shift>> temp = new GenericTypeIndicator<List<Shift>>(){};
                    doctorShifts = snapshot.child(doctorUsername).child("shifts").getValue(temp);

                    for (int i = 1; i < doctorShifts.size(); i++) {   // First shift (at index 0) is a dummy
                        Shift shift = doctorShifts.get(i);

                        String date = shift.getDate();
                        String startTime = shift.getStartTime();
                        String endTime = shift.getEndTime();

                        LinearLayout layout = new LinearLayout(MainActivityViewShifts.this,null,0, R.style.ApplicantLinearLayout);
                        layout.setLayoutParams(paramsLinearLayout);
                        TextView textView = new TextView(MainActivityViewShifts.this,null,0,R.style.ApplicantNameView);
                        MaterialButton deleteButton = new MaterialButton(MainActivityViewShifts.this, null);
                        deleteButton.setText("Delete");
                        textView.setLayoutParams(paramsTextView);
                        layout.setPadding(5,5,50,5);
                        textView.setText(date);
                        layout.addView(textView);
                        layout.addView(deleteButton);
                        layoutScrollView.addView(layout);
                        textView.setClickable(true);

                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivityViewShifts.this, MainActivitySeeShiftInfo.class);
                                String[] shiftDetails = {date, startTime, endTime};
                                intent.putExtra("shift details", shiftDetails);
                                startActivity(intent);
                            }
                        });
                        deleteButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                doctorShifts.remove(shift);
                                userRef.child(doctorUsername).child("shifts").setValue(doctorShifts);
                                layoutScrollView.removeView(layout);
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}