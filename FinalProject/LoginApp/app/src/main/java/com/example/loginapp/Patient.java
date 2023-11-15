package com.example.loginapp;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {

    private int healthCardNumber;
    private String status;
    private List<Appointment> appointments;

    public Patient() {

    }

    public Patient(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, int healthCardNumber, String status){
        super(firstName, lastName, emailAddress, accountPassword, phoneNumber, address);
        this.healthCardNumber = healthCardNumber;
        this.status = "pending";
        this.appointments = new ArrayList<>();

        // Dummy appointment to act as placeholder for Realtime Database
        appointments.add(new Appointment("null", "null", "null", "null", "null"));
    }

    public List<Appointment> getAppointments(){
        return this.appointments;
    }

    public void setAppointments(List<Appointment> list){
        this.appointments = list;
    }

    public void addAppointment(Appointment a){
        this.appointments.add(a);
    }

    public Appointment getAppointment(int i){
        return this.appointments.get(i);
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