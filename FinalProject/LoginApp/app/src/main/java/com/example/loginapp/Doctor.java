package com.example.loginapp;
import java.util.Arrays;
import java.util.ArrayList;

public class Doctor extends Person {

    private String[] specialties;
    private int employeeNumber;

    private String status;
    private boolean autoAcceptStatus;
    private ArrayList<Appointment> appointments;

    private ArrayList<Shift> shifts;



    public Doctor(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, int employeeNumber, String status, String ...specialties){
        super(firstName, lastName, emailAddress, accountPassword, phoneNumber, address);
        this.employeeNumber = employeeNumber;
        this.specialties = specialties;
        this.status = status;
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
    }

    public ArrayList<Appointment> getAppointments(){
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> list){
        appointments = list;
    }

    public ArrayList<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(ArrayList<Shift> shifts) {
        this.shifts = shifts;
    }

    public void addAppointment(Appointment a){
        appointments.add(a);
    }
    public Appointment getAppointment(int i){
        return appointments.get(i);
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
