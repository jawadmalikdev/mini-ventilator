package com.example.miniventilator.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class DoctorDashboard extends AppCompatActivity {
    Button checkpateint, add_ins, profile, sign_out, deletepatient, _delaccount;
    TextView _loggedind;
    //intent variables
    String username, name, demail, dpassword;
    DatabaseReference reference;
    final Loadingbar loadingbar = new Loadingbar(DoctorDashboard.this);
    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        getSupportActionBar().setTitle("Doctor Dashboard");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkpateint = (Button) findViewById(R.id.checkp);
        add_ins = (Button) findViewById(R.id.buttonins);
        profile = (Button) findViewById(R.id.profilep);
        sign_out = (Button) findViewById(R.id.buttonout);
        deletepatient = (Button) findViewById(R.id.buttondelete);
        _delaccount = (Button) findViewById(R.id.delaccount);
        _loggedind = (TextView) findViewById(R.id.loggedindoc);
        loadingbar.showdialog();
        handleuserdata();
        checkpateint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                Intent localintent = new Intent(DoctorDashboard.this, SensorValues.class);
                startActivity(localintent);
            }
        });

        add_ins.setOnClickListener((View v) -> {
            startActivity(new Intent(DoctorDashboard.this, AddInstructions.class));
        });

        profile.setOnClickListener(v -> {
            Intent intentgo = new Intent(DoctorDashboard.this, DoctorProfile.class);
            intentgo.putExtra("fullname", name);
            intentgo.putExtra("Username", username);
            intentgo.putExtra("email", demail);
            intentgo.putExtra("password", dpassword);
            startActivity(intentgo);
        });
        deletepatient.setOnClickListener((View view) ->{
            startActivity(new Intent(DoctorDashboard.this, DeletePatient.class));
                });

        _delaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                reference = FirebaseDatabase.getInstance().getReference("Doctor");
                reference.child(username).removeValue();
                Toast.makeText(DoctorDashboard.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                Intent localintent = new Intent(DoctorDashboard.this, MainActivity.class);
                startActivity(localintent);
                finish();
            }
        });

        sign_out.setOnClickListener(v -> {
            Intent newintent = new Intent(DoctorDashboard.this, MainActivity.class);
            startActivity(newintent);
            preferences.clearData(this);
            Toast.makeText(DoctorDashboard.this, "Signed out Successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void handleuserdata() {
        reference = FirebaseDatabase.getInstance().getReference("Doctor").child(preferences.getUserName(this));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.child("username").getValue(String.class);
                name = snapshot.child("fullname").getValue(String.class);
                demail = snapshot.child("email").getValue(String.class);
                dpassword = snapshot.child("password").getValue(String.class);
                _loggedind.setText(name);
                loadingbar.dismissbar();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingbar.dismissbar();
                Toast.makeText(DoctorDashboard.this, "Database Problem", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        Intent newintent = getIntent();
        username = newintent.getStringExtra("Username");
        name = newintent.getStringExtra("fullname");
        demail = newintent.getStringExtra("email");
        dpassword = newintent.getStringExtra("password");*/
    }
}