package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivityPatientRegister extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText address;
    private EditText phoneNum;
    private EditText healthNum;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_patient_register);


        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        phoneNum = findViewById(R.id.phonenum);
        healthNum = findViewById(R.id.healthnum);
        register = findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkDataEntered()) {
                    registerUser();
                }
                //once the user clicks register, check to see if the data is valid
            }
        });
    }

    void registerUser() {
        String firstNameStr = firstName.getText().toString();
        String lastNameStr = lastName.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        String addressStr = address.getText().toString();
        String phoneNumStr = phoneNum.getText().toString();
        int healthNumInt = Integer.parseInt(healthNum.getText().toString());
        String status = "pending";
        Patient patient = null;
        patient = new Patient(firstNameStr, lastNameStr, emailStr, passwordStr, phoneNumStr, addressStr, healthNumInt, status);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("Users").child(patient.getFirstName()).setValue(patient);
        myRef.child("Users").child(patient.getFirstName()).child("userType").setValue("Patient");

        Toast t = Toast.makeText(MainActivityPatientRegister.this, "Registration request sent!", Toast.LENGTH_SHORT);
        t.show();

        Intent intent = new Intent(MainActivityPatientRegister.this, MainActivityHome.class);
        startActivity(intent);
        finish(); // Close the current activity to prevent going back to it using the back button
    }

    //function for data validation
    boolean checkDataEntered() {

        if (isEmpty(firstName)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
            return false;
        } else if (isEmpty(lastName)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
            return false;
        } else if (isEmpty(email)) {
            Toast t = Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT);
            t.show();
            return false;
        } else if (isEmpty(password)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
            return false;
        } else if (isEmpty(address)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
            return false;
        } else if (isEmpty(phoneNum)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
            return false;
        } else if (isEmpty(healthNum)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
            return false;
        } else if (!isNumerical(phoneNum)) {
            Toast t = Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT);
            t.show();
            return false;
        } else if (!isNumerical(healthNum)) {
            Toast t = Toast.makeText(this, "Invalid Health Card Number", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }
        Toast t = Toast.makeText(this, "All good", Toast.LENGTH_SHORT);
        return true;

    }


    //simple check to see if
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    //numerical check for phone number/health card
    boolean isNumerical(EditText text) {
        CharSequence str = text.getText().toString();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c) && c != '-') {
                return false; // Found a non-numeric character
            }
        }
        return true; // All characters are numbers or dashes
    }
    boolean isValidEmailAddress(EditText emailEditText) {
        String email = emailEditText.getText().toString();
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


}