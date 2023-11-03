package com.example.loginapp;
import java.util.Arrays;

public class Doctor extends Person {

    private String[] specialties;
    private int employeeNumber;

    private String status;
    private boolean autoAcceptStatus;

    public Doctor(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, int employeeNumber, String status, String ...specialties){
        super(firstName, lastName, emailAddress, accountPassword, phoneNumber, address);
        this.employeeNumber = employeeNumber;
        this.specialties = specialties;
        this.status = status;
    }

    public void setAutoAcceptStatus(boolean autoAcceptStatus){
        this.autoAcceptStatus = autoAcceptStatus;
    }

    public boolean getAutoAcceptStatus(){
        return autoAcceptStatus;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){ // status can either be "pending", "accepted", or "declined"
        this.status = status;
    }
    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int newEmployeeNumber) {
        employeeNumber = newEmployeeNumber;
    }
    public String getSpecialties() {
        return Arrays.toString(specialties);
    }

    public void setSpecialties(String ...specialties) {
        this.specialties = specialties;
    }

}
