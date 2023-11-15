package com.example.loginapp;

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

    public boolean isValid(){
        int end = 0;
        int start = 0;
        try {
            start = Integer.parseInt(this.startTime);
            end = Integer.parseInt(this.endTime);
        }
        catch (Exception e){
            return false;
        }

        if(end <= start){
            return false;
        }
        else{
            return true;
        }

    }
    public boolean conflictsWith(Shift shift2){
        String date2 = shift2.getDate(); //"10/11/2023"
        String[] date2Info = date2.split("/");
        String[] date1Info = date.split("/");

        int start1 = Integer.parseInt(this.startTime);
        int end1 = Integer.parseInt(this.endTime);
        int start2 = Integer.parseInt(shift2.getStartTime());
        int end2 = Integer.parseInt(shift2.getEndTime());

        if(date1Info[0].equals(date2Info[0]) && date1Info[1].equals(date2Info[1]) && date1Info[2].equals(date2Info[2])){
            if ((start1 >= start2 && start1 <= end2) || (end1 >= start2 && end1 <= end2) || (start2 >= start1 && start2 <= end1) || (end2 >= start1 && end2 <= end1)){
                return true;
            }
        }

        return false;

    }
}