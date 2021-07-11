package com.example.miniventilator.doctor;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniventilator.Loadingbar;
import com.example.miniventilator.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorProfile extends AppCompatActivity {
    EditText p_name, p_email, p_password;
    TextView p_username;
    private DatabaseReference referenceupdate;
    //data for intent
    String iname, iemail, iusername, ipassword;
    final Loadingbar loadingbar = new Loadingbar(DoctorProfile.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        referenceupdate = FirebaseDatabase.getInstance().getReference("Doctor");
        getSupportActionBar().setTitle("Doctor Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        p_name = (EditText) findViewById(R.id.pro_name);
        p_email = (EditText) findViewById(R.id.pro_email);
        p_username = (TextView) findViewById(R.id.pro_username);
        p_password = (EditText) findViewById(R.id.pro_password);

        setdata();

    }

    private void setdata() {
        loadingbar.showdialog();
        Intent intent = getIntent();
        iname = intent.getStringExtra("fullname");
        iemail = intent.getStringExtra("email");
        iusername = intent.getStringExtra("Username");
        ipassword = intent.getStringExtra("password");

        p_name.setText(iname);
        p_email.setText(iemail);
        p_username.setText("Username: " + iusername);
        p_password.setText(ipassword);
        loadingbar.dismissbar();
    }

    public void dr_update(View view) {
        if (isnamechanged() || isemailchanged() || ispasswordchanged()) {
            Toast.makeText(DoctorProfile.this, "Data has been updated", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(DoctorProfile.this, "Data is same and cannot be updated", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isnamechanged() {
        if(!iname.equals(p_name.getText().toString())){
            referenceupdate.child(iusername).child("name").setValue(p_name.getText().toString());
            iname = p_name.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isemailchanged() {
        if(!iemail.equals(p_email.getText().toString())){
            referenceupdate.child(iusername).child("email").setValue(p_email.getText().toString());
            iemail = p_email.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean ispasswordchanged() {
        if(!ipassword.equals(p_password.getText().toString())){
            referenceupdate.child(iusername).child("password").setValue(p_password.getText().toString());
            ipassword = p_password.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
}