package com.example.loginapp;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person {

    private String[] specialties;
    private int employeeNumber;

    private String status;
    private boolean autoAcceptStatus;
    private List<Appointment> appointments;

    private List<Shift> shifts;



    public Doctor(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, int employeeNumber, String status, String ...specialties){
        super(firstName, lastName, emailAddress, accountPassword, phoneNumber, address);
        this.employeeNumber = employeeNumber;
        this.specialties = specialties;
        this.status = status;
        appointments = new ArrayList<Appointment>();
        shifts = new ArrayList<Shift>();
        Shift nullShift = new Shift("null","null","null",firstName);
        shifts.add(nullShift);
    }

    public List<Appointment> getAppointments(){
        return appointments;
    }

    public void setAppointments(List<Appointment> list){
        appointments = list;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public void addShift(Shift shift){
        this.shifts.add(shift);
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
