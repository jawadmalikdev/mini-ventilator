package com.example.miniventilator.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniventilator.Loadingbar;
import com.example.miniventilator.MainActivity;
import com.example.miniventilator.R;
import com.example.miniventilator.SensorValues;
import com.example.miniventilator.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientDashboard extends AppCompatActivity {
    Button values, doctor_ins, profile, signout;
    TextView pt_user;
    //intent variables
    String pusername, pname, pemail, ppassword, page, pgender;
    DatabaseReference databaseReference;
    final Loadingbar loadingbar = new Loadingbar(PatientDashboard.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        getSupportActionBar().setTitle("Patient Dashboard");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(preferences.getUserName(this));
        values = (Button) findViewById(R.id.buttonvalues);
        doctor_ins = (Button) findViewById(R.id.doctorins);
        profile = (Button) findViewById(R.id.ptprofile);
        signout = (Button) findViewById(R.id.ptbuttonout);
        pt_user = (TextView) findViewById(R.id.ptuser);
        loadingbar.showdialog();
        getthedata();
        values.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                Intent localintent = new Intent(PatientDashboard.this, SensorValues.class);
                startActivity(localintent);
                Toast.makeText(PatientDashboard.this, "Sensor Values", Toast.LENGTH_SHORT).show();
            }
        });

        doctor_ins.setOnClickListener((View v) -> {
            startActivity(new Intent(PatientDashboard.this, ViewInstructions.class));
        });

        profile.setOnClickListener(v -> {
            Intent intent = new Intent(PatientDashboard.this, PatientProfile.class);
            intent.putExtra("Name", pname);
            intent.putExtra("Username", pusername);
            intent.putExtra("Email", pemail);
            intent.putExtra("Password", ppassword);
            intent.putExtra("Age", page);
            intent.putExtra("Gender", pgender);
            startActivity(intent);
        });

        signout.setOnClickListener(v ->{
                Intent newintent = new Intent(PatientDashboard.this, MainActivity.class);
                startActivity(newintent);
                preferences.clearData(this);
                Toast.makeText(PatientDashboard.this, "Signed Out Successfully", Toast.LENGTH_SHORT).show();
                finish();
        });

    }

    public void getthedata() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pusername = snapshot.child("patient_username").getValue(String.class);
                pname = snapshot.child("patient_name").getValue(String.class);
                pemail = snapshot.child("patient_email").getValue(String.class);
                ppassword = snapshot.child("patient_password").getValue(String.class);
                page = snapshot.child("patient_age").getValue(String.class);
                pgender = snapshot.child("patient_gender").getValue(String.class);
                pt_user.setText(pname);
                loadingbar.dismissbar();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingbar.dismissbar();
                Toast.makeText(PatientDashboard.this, "Database Problem", Toast.LENGTH_SHORT).show();
            }
        });
        /*
        Intent newintent = getIntent();
        pusername = newintent.getStringExtra("Username");
        pname = newintent.getStringExtra("Name");
        pemail = newintent.getStringExtra("Email");
        ppassword = newintent.getStringExtra("Password");
        page = newintent.getStringExtra("Age");
        pgender = newintent.getStringExtra("Gender");
        */
    }
}