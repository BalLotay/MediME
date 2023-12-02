package com.example.loginapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Appointment {
    String status; // pending, approved or cancelled
    String date;
    String startTime; // eg, "13:00"
    String endTime;
    String doctor; // Doctor's username (firstName)
    String patient;

    public Appointment() {

    }

    public Appointment(String patient, String doctor, String date, String startTime, String endTime) {
        status = "pending";
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getPatient() {
        return patient;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String s) {
        this.status = s;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String e) {
        this.endTime = e;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String s) {
        this.startTime = s;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String d) {
        this.date = d;
    }

    @Exclude
    public boolean isPastAppointment() {

        if (date == null || startTime == null) {
            return false;
        }

        String dateTimeStr = date + " " + startTime;

        try {
            return new SimpleDateFormat("MM/dd/yyyy HHmm").parse(dateTimeStr).before(new Date());
        } catch (ParseException pe) {
            return false;
        }
    }

    @Exclude
    public boolean isCancellableAppointment() {

        if (date == null || startTime == null) {
            return false;
        }

        String dateTimeStr = date + " " + startTime;

        try {
            Date d = new Date();
            d.setTime(d.getTime() + TimeUnit.HOURS.toMillis(1));

            return new SimpleDateFormat("MM/dd/yyyy HHmm").parse(dateTimeStr).after(d);
        } catch (ParseException pe) {
            return false;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "status: " + status + ", date: " + date + ", startTime: " + startTime + ", endTime: " + endTime
                + ", patient: " + patient + ", doctor: " + doctor;
    }
}