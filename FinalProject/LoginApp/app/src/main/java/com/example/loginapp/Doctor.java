package com.example.loginapp;
import java.util.Arrays;

public class Doctor extends Person {

    private String[] specialties;

    public Doctor(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, int healthCardNumber, String ...specialties){
        super(firstName, lastName, emailAddress, accountPassword, phoneNumber, address);
        this.specialties = specialties;
    }

    public String getSpecialties() {
        return Arrays.toString(specialties);
    }

    public void setSpecialties(String ...specialties) {
        this.specialties = specialties;
    }

}