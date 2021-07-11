package com.example.miniventilator.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miniventilator.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static android.text.TextUtils.isEmpty;

public class DeletePatient extends AppCompatActivity {
    EditText username;
    EditText name, email, gender;

    Button _check;
    DatabaseReference databaseReference;
    String _username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_patient);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Delete Patient");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username = (EditText) findViewById(R.id.ptusername);
        name = (EditText) findViewById(R.id.patientname);
        email = (EditText) findViewById(R.id.patientemail);
        gender = (EditText) findViewById(R.id.patientgender);
        _check = (Button) findViewById(R.id.check);
        databaseReference = FirebaseDatabase.getInstance().getReference("patient");

        _check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _username = username.getText().toString().trim();
                if (isEmpty(_username)) {
                    Toast.makeText(DeletePatient.this, "please enter user name:", Toast.LENGTH_SHORT).show();
                } else {
                    Query checkuser = databaseReference.orderByChild("patient_username").equalTo(_username);

                    checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String patientnameDB = snapshot.child(_username).child("patient_name").getValue(String.class);
                                String patientemailDB = snapshot.child(_username).child("patient_email").getValue(String.class);
                                String patientgenderDB = snapshot.child(_username).child("patient_gender").getValue(String.class);
                                name.setText(patientnameDB);
                                email.setText(patientemailDB);
                                gender.setText(patientgenderDB);
                                Toast.makeText(DeletePatient.this, "Patient found", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(DeletePatient.this, "Patient does not exist", Toast.LENGTH_SHORT).show();
                                name.setText(null);
                                email.setText(null);
                                gender.setText(null);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(DeletePatient.this, "Database error", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }
    public void deletept(View view){
       String namept = name.getText().toString().trim();
       String emailpt = email.getText().toString().trim();
       String genderpt = gender.getText().toString().trim();
       if(isEmpty(namept) || isEmpty(emailpt) || isEmpty(genderpt) || isEmpty(_username)){
           Toast.makeText(this, "one or more fields are empty", Toast.LENGTH_SHORT).show();
       }
       else{
           databaseReference.child(_username).removeValue();
           Toast.makeText(this, "Patient Deleted Successfully", Toast.LENGTH_SHORT).show();
           name.setText(null);
           email.setText(null);
           gender.setText(null);
       }

    }
}