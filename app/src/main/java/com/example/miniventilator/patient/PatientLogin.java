package com.example.miniventilator.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniventilator.R;
import com.example.miniventilator.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static android.text.TextUtils.isEmpty;

public class PatientLogin extends AppCompatActivity {
    EditText ptuserin;
    EditText ptpassin;
    Button ptlogin;
    TextView jumper;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Patient Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        ptuserin = (EditText) findViewById(R.id.ptuserin);
        ptpassin = (EditText) findViewById(R.id.ptpassin);
        ptlogin = (Button) findViewById(R.id.ptlogin);
        jumper = findViewById(R.id.gotoreg);
        jumper.setOnClickListener(View ->{
            startActivity(new Intent(PatientLogin.this, PatientSignup.class));
        });


        ptlogin.setOnClickListener(View ->{
            loginpatient();
        });
    }


    public void loginpatient(){
        String ptuser = ptuserin.getText().toString().trim();
        String password = ptpassin.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("patient");
        if (isEmpty(ptuser)) {
            ptuserin.setError("Please Enter Email");
            ptuserin.requestFocus();
        }
        else if (isEmpty(password)) {
            ptpassin.setError("Please Enter Password");
            ptpassin.requestFocus();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            Query checkuser = reference.orderByChild("patient_username").equalTo(ptuser);
            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String passwordDB = snapshot.child(ptuser).child("patient_password").getValue(String.class);
                        assert passwordDB != null;
                        if(passwordDB.equals(password)){
                            /*
                            String nameDB = snapshot.child(ptuser).child("patient_name").getValue(String.class);
                            String usernameDB = snapshot.child(ptuser).child("patient_username").getValue(String.class);
                            String emailDB = snapshot.child(ptuser).child("patient_email").getValue(String.class);
                            String ageDB = snapshot.child(ptuser).child("patient_age").getValue(String.class);
                            String genderDB = snapshot.child(ptuser).child("patient_gender").getValue(String.class);
                             */
                            preferences.setDataLogin(PatientLogin.this, true);
                            preferences.setDataAs(PatientLogin.this, "patient");
                            preferences.setUserName(PatientLogin.this, ptuser);
                            Intent intent = new Intent(PatientLogin.this, PatientDashboard.class);
                            /*
                            intent.putExtra("Name", nameDB);
                            intent.putExtra("Username", usernameDB);
                            intent.putExtra("Email", emailDB);
                            intent.putExtra("Password", passwordDB);
                            intent.putExtra("Age", ageDB);
                            intent.putExtra("Gender", genderDB);
                            */
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(PatientLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(PatientLogin.this, "Password Incorrect", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(PatientLogin.this, "No such user exist.", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(PatientLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}