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
        
        Doctor doctor = new Doctor("Dan", "Li", "email", "as", "333333", "rew", 243, "declined", "cardio")
        int precalculatedRating = 3;
        doctor.setRating(2);
        doctor.setRating(4);
        doctor.setRating(3);
        int rating = doctor.getAverageRating();
        assertEquals(precalculatedRating, rating);
           
    }

}
