package com.example.loginapp;

public class Patient extends Person {

    private int healthCardNumber;

    private String status;

    public Patient(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, int healthCardNumber, String status){
        super(firstName, lastName, emailAddress, accountPassword, phoneNumber, address);
        this.healthCardNumber = healthCardNumber;
        this.status = "pending";
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