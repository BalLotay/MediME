package com.example.loginapp;
import androidx.annotation.NonNull;

public class Appointment {
	String status; // pending, approved or cancelled
	String date;
	String startTime; // eg, "13:00"
	String endTime;
	String doctor; // Doctor's username (firstName)
	String patient;

	public Appointment() {

	}
	public Appointment(String patient, String doctor, String date, String startTime, String endTime) {
		status = "pending";
		this.patient = patient;
		this.doctor = doctor;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getDoctor() {
		return doctor;
	}

	public String getPatient() {
		return patient;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String s) {
		this.status = s;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String e) {
		this.endTime = e;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String s) {
		this.startTime = s;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String d) {
		this.date = d;
	}

	@NonNull
	@Override
	public String toString() {
		return "status: " + status + ", date: " + date + ", startTime: " + startTime + ", endTime: " + endTime
				+ ", patient: " + patient + ", doctor: " + doctor;
	}
}