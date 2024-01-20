package com.example.loginapp;
import java.util.Arrays;

public class Doctor extends Person {

    private String[] specialties;

    private String status;

    public Doctor(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, int healthCardNumber, String status, String ...specialties){
        super(firstName, lastName, emailAddress, accountPassword, phoneNumber, address);
        this.specialties = specialties;
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){ // status can either be "pending", "accepted", or "declined"
        this.status = status;
    }
    public String getSpecialties() {
        return Arrays.toString(specialties);
    }

    public void setSpecialties(String ...specialties) {
        this.specialties = specialties;
    }

}