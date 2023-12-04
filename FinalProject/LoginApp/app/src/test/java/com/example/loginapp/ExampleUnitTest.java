package com.example.loginapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    public void testAverageRating(){
        
        Doctor doctor = new Doctor("Dan", "Li", "email", "as", "333333", "rew", 243, "declined", "cardio");
        int precalculatedRating = 3;
        doctor.setRating(2);
        doctor.setRating(4);
        doctor.setRating(3);
        int rating = doctor.getAverageRating();
        assertEquals(precalculatedRating, rating);
           
    }

	public void testShiftConflictsWith(){
		Shift s1 = new Shift("10/11/2023", "2300","2330","Harmon");
		Shift s2 = new Shift("10/11/2023", "2300","2330","Harmon");
		boolean result = s1.conflictsWith(s2);
		
		if(result == true) {
			System.out.println("Passed");
		}
		else{
			System.out.println("Failed");
		}
	}

	public void testChangeEmployeeID(){
		Doctor doctor = new Doctor("Dan", "Li", "email", "as", "333333", "rew", 243, "declined", "cardio");
		
		doctor.setEmployeeNumber(333);
		int updatedEmployeeID =  doctor.getEmployeeNumber();
		if(updatedEmployeeID == 333) {
			System.out.println("Passed");
			
		}
		else {
			System.out.println("Failed");
	}

    	public void testPatientGetAppointment(){
		 Patient patient = new Patient("Dan", "L", "rewr", "asd", "asd", "asd", 3, "asd");
	        Appointment ap1 = new Appointment("Dan", "Dan", "Dan", "Dan", "Dan");
	        Appointment ap2 = new Appointment("das", "Dan", "Dan", "Dan", "Dan");
	        
	        patient.addAppointment(ap1);
	        patient.addAppointment(ap2);
	        Appointment received = patient.getAppointment(2);
	        
	        if(received.equals(ap2)) {
	        	System.out.println("Passed");
	        }
	        else {
	        	System.out.println("Failed");
	        }
	        
	        
	       
	    }

}
