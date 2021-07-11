package com.example.miniventilator.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniventilator.Loadingbar;
import com.example.miniventilator.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.text.TextUtils.isEmpty;

public class PatientSignup extends AppCompatActivity {
    EditText ptname, ptuser, ptemail, ptpass, ptage;
    Button ptreg;
    TextView jumpsign;
    RadioButton radiogendermale, radiogenderfemale;
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_signup);
        getSupportActionBar().setTitle("Patient SignUp");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Loadingbar loadingbar = new Loadingbar(PatientSignup.this);
        ptname = (EditText) findViewById(R.id.patient_name);
        ptuser = (EditText) findViewById(R.id.user_patient);
        ptemail = (EditText) findViewById(R.id.patient_email);
        ptpass = (EditText) findViewById(R.id.patient_pass);
        ptage = (EditText) findViewById(R.id.patient_age);
        radiogendermale = (RadioButton) findViewById(R.id.radio_male);
        radiogenderfemale = (RadioButton) findViewById(R.id.radio_female);
        ptreg = (Button) findViewById(R.id.patient_reg);
        jumpsign = findViewById(R.id.gotologin);

        jumpsign.setOnClickListener(View->{
                    startActivity(new Intent(PatientSignup.this, PatientLogin.class));
                }
        );

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("patient");

        ptreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = ptname.getText().toString();
                String ptusername = ptuser.getText().toString();
                String email = ptemail.getText().toString();
                String password = ptpass.getText().toString();
                String age = ptage.getText().toString();
                if(radiogendermale.isChecked()){
                    gender = "male";
                }
                if(radiogenderfemale.isChecked()){
                    gender = "female";
                }

                if (isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!name.matches("[a-zA-z]+")) {
                    ptname.requestFocus();
                    ptname.setError("name can only contain alphabetic characters");
                    return;
                }
                else if (isEmpty(ptusername)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Name.", Toast.LENGTH_SHORT).show();

                    return;
                }
                else if (isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Email.", Toast.LENGTH_SHORT).show();

                    return;
                }
                else if (!email.matches("[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    ptemail.requestFocus();
                    ptemail.setError("please enter a valid email");
                    return;
                }
                else if (isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (password.length()<6) {
                    ptpass.requestFocus();
                    ptpass.setError("Password should be at least 6 letters long");
                }
                else if (isEmpty(age)) {
                    Toast toast3 = Toast.makeText(getApplicationContext(), "Please Enter age.", Toast.LENGTH_SHORT);
                    toast3.show();
                    return;
                }
                else if(Integer.parseInt(ptage.getText().toString())>130 || Integer.parseInt(ptage.getText().toString())==0){
                    ptage.requestFocus();
                    ptage.setError("please enter a valid age number.");
                    return;
                }

                else if(!radiogendermale.isChecked() && !radiogenderfemale.isChecked()){
                    Toast.makeText(getApplicationContext(), "please select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    patienthelper addnewpatient = new patienthelper(name, ptusername, email, password, age, gender);
                    Query checkusername = reference.orderByChild("patient_username").equalTo(ptusername);

                    loadingbar.showdialog();
                    checkusername.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()) {

                                reference.child(ptusername).setValue(addnewpatient).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        loadingbar.dismissbar();
                                        Toast.makeText(getApplicationContext(), "Patient Registration Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(PatientSignup.this, PatientLogin.class));

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        loadingbar.dismissbar();
                                        Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            else {
                                loadingbar.dismissbar();
                                Toast.makeText(PatientSignup.this, "Username Already exists", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            loadingbar.dismissbar();
                            Toast.makeText(PatientSignup.this, "Database problem", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
    }
}