
import java.util.Date;

public class Appointment {
	String status;
	Date date;
	String startTime; // eg, "13:00"
	String endTime;
	Doctor doctor;
	Patient patient;

	public Appointment() {

	}

	public Appointment(Doctor doctor, Patient patient, Date date, String endTime, String startTime) {
		status = "pending";
		if (doctor.autoAcceptStatus == true) {
			status = "accepted";
		}
		this.patient = patient;
		this.doctor = doctor;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;

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

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date d) {
		this.date = d;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient p) {
		this.patient = p;
	}

	public Doctor getDoctor() {
		return this.patient;
	}

	public void setDoctor(Doctor d) {
		this.doctor = d;
	}

}
