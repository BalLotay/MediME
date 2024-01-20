package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
    private  EditText phoneNum;
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

                checkDataEntered(); //once the user clicks register, check to see if the data is valid

                String firstNameStr = firstName.getText().toString();
                String lastNameStr = lastName.getText().toString();
                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();
                String addressStr = address.getText().toString();
                String phoneNumStr = phoneNum.getText().toString();
                int healthNumInt = Integer.parseInt(healthNum.getText().toString());
                String status = "pending";

                Patient patient = new Patient(firstNameStr, lastNameStr, emailStr, passwordStr, phoneNumStr, addressStr, healthNumInt, status);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("Patients").child(patient.getEmailAddress()).setValue(patient);

                Toast t = Toast.makeText(MainActivityPatientRegister.this, "REGISTRATION SUCCESSFUL!", Toast.LENGTH_SHORT);
                t.show();

                Intent intent = new Intent(MainActivityPatientRegister.this, MainActivityHome.class);
                startActivity(intent);
                finish(); // Close the current activity to prevent going back to it using the back button
            }
        });
    }

    //function for data validation
    void checkDataEntered(){
        if (isEmpty(firstName)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
        } else if (isEmpty(lastName)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
        }else if (isEmpty(email)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
        }else if (isEmpty(password)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
        }else if (isEmpty(address)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
        }else if (isEmpty(phoneNum)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
        }else if (isEmpty(healthNum)) {
            Toast t = Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT);
            t.show();
        }else if (!isNumerical(phoneNum)) {
            Toast t = Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT);
            t.show();
        }else if (!isNumerical(healthNum)) {
            Toast t = Toast.makeText(this, "Invalid Health Card Number", Toast.LENGTH_SHORT);
            t.show();
        }

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


}