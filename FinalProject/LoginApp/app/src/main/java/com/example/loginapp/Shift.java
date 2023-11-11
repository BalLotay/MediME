package com.example.loginapp;

import java.util.Date;
import java.util.Calendar;
public class Shift {

    String date;

    String startTime;

    String endTime;

    String doctor;

    public Shift(){

    }



    public Shift(String date, String startTime, String endTime, String doctor){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public boolean conflictsWith(Shift shift2){
        String date2 = shift2.getDate(); //"10/11/2023"
        String[] date2Info = date2.split("/");
        String[] date1Info = date.split("/");

        if(true){
            return true;
        }

        return false;

    }
}
