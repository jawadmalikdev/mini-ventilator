package com.example.miniventilator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.miniventilator.doctor.DoctorDashboard;
import com.example.miniventilator.doctor.DoctorSignup;
import com.example.miniventilator.doctor.Doctorlogin;
import com.example.miniventilator.patient.PatientDashboard;
import com.example.miniventilator.patient.PatientLogin;
import com.example.miniventilator.patient.PatientSignup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login_patient(View view) {
        startActivity(new Intent(getApplicationContext(), PatientLogin.class));
    }

    public void login_doctor(View view) {
        startActivity(new Intent(getApplicationContext(), Doctorlogin.class));
    }

    public void signup_patient(View view) {
        Intent i = new Intent(MainActivity.this, PatientSignup.class);
        startActivity(i);
    }

    public void signup_doctor(View view) {
        Intent i = new Intent(MainActivity.this, DoctorSignup.class);
        startActivity(i);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)) {
            if (preferences.getDataAs(this).equals("patient")) {
                startActivity(new Intent(MainActivity.this, PatientDashboard.class));
                finish();
            } else if (preferences.getDataAs(this).equals("Doctor")) {
                startActivity(new Intent(MainActivity.this, DoctorDashboard.class));
                finish();
            }
        }
    }
}