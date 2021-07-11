package com.example.miniventilator.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniventilator.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientProfile extends AppCompatActivity {
    TextView username;
    EditText ptname, ptemail, ptpassword, ptage, ptgender;
    DatabaseReference reference;
    //data from intent
    String iname, iemail, iusername, ipassword, iage, igender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);
        getSupportActionBar().setTitle("Patient Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reference = FirebaseDatabase.getInstance().getReference("patient");
        username = (TextView) findViewById(R.id.pt_username);
        ptname = (EditText) findViewById(R.id.pt_name);
        ptemail = (EditText) findViewById(R.id.pt_email);
        ptpassword = (EditText) findViewById(R.id.pt_password);
        ptage = (EditText) findViewById(R.id.pt_age);
        ptgender = (EditText) findViewById(R.id.pt_gender);

        setpatientdata();
    }
    public void setpatientdata(){
        Intent intent = getIntent();
        iname = intent.getStringExtra("Name");
        iemail = intent.getStringExtra("Email");
        iusername = intent.getStringExtra("Username");
        ipassword = intent.getStringExtra("Password");
        iage = intent.getStringExtra("Age");
        igender = intent.getStringExtra("Gender");


        username.setText("username: " + iusername);
        ptname.setText(iname);
        ptemail.setText(iemail);
        ptpassword.setText(ipassword);
        ptage.setText(iage);
        ptgender.setText(igender);
    }

    public void pt_update(View view) {
        if (isnamechanged() || isemailchanged() || ispasswordchanged() ||isagechanged() || isgenderchanged()) {
            Toast.makeText(PatientProfile.this, "Data has been updated", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(PatientProfile.this, "Data is same and cannot be updated", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isnamechanged() {
        if(!iname.equals(ptname.getText().toString())){
            reference.child(iusername).child("patient_name").setValue(ptname.getText().toString());
            iname = ptname.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isemailchanged() {
        if(!iemail.equals(ptemail.getText().toString())){
            reference.child(iusername).child("patient_email").setValue(ptemail.getText().toString());
            iemail = ptemail.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isagechanged() {
        if(!iage.equals(ptage.getText().toString())){
            reference.child(iusername).child("patient_age").setValue(ptage.getText().toString());
            iage = ptage.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isgenderchanged() {
        if(!igender.equals(ptgender.getText().toString())){
            reference.child(iusername).child("patient_gender").setValue(ptgender.getText().toString());
            igender = ptgender.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean ispasswordchanged() {
        if(!ipassword.equals(ptpassword.getText().toString())){
            reference.child(iusername).child("patient_password").setValue(ptpassword.getText().toString());
            ipassword = ptpassword.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

}