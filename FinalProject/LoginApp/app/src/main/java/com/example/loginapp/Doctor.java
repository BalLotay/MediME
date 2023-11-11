package com.example.loginapp;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Doctor extends Person {
    private List<String> specialties;
    private int employeeNumber;
    private String status;
    private boolean autoAcceptStatus;
    private List<Appointment> appointments;

    public Doctor() {

    }

    public Doctor(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, int employeeNumber, String status, String ...specialties){
        super(firstName, lastName, emailAddress, accountPassword, phoneNumber, address);
        this.employeeNumber = employeeNumber;
        this.specialties = Arrays.asList(specialties);
        this.status = status;
        this.appointments = new ArrayList<>();

        // Dummy appointment to act as placeholder for Realtime Database
        appointments.add(new Appointment("null", "null", "null", "null", "null"));
    }

    public List<Appointment> getAppointments(){
        return this.appointments;
    }

    public void setAppointments(List<Appointment> list){
        appointments = list;
    }

    public void addAppointment(Appointment a){
        appointments.add(a);
    }
    public Appointment getAppointment(int i){
        return appointments.get(i);
    }

    public void removeAppointment(int i) {
        appointments.remove(i);
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
    public List<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<String> specialties) {
        this.specialties = specialties;
    }

}
