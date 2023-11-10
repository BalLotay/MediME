package com.example.loginapp;

import java.util.ArrayList;

public class Patient extends Person {

    private int healthCardNumber;

    private String status;
    private ArrayList<Appointment> appointments;

    public Patient(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, int healthCardNumber, String status){
        super(firstName, lastName, emailAddress, accountPassword, phoneNumber, address);
        this.healthCardNumber = healthCardNumber;
        this.status = "pending";
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
    }

     public ArrayList<Appointment> getAppointments(){
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> list){
        appointments = list;
    }

    public void addAppointment(Appointment a){
        appointments.add(a);
    }
    public Appointment getAppointment(int i){
        return appointments.get(i);
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){ // status can either be "pending", "accepted", or "declined"
        this.status = status;
    }
    public int getHealthCardNumber() {
        return healthCardNumber;
    }

    public void setHealthCardNumber(int newHealthCardNumber) {
        healthCardNumber = newHealthCardNumber;
    }


}
