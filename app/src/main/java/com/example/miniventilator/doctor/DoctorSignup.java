package com.example.miniventilator.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniventilator.Loadingbar;
import com.example.miniventilator.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.text.TextUtils.isEmpty;

public class DoctorSignup extends AppCompatActivity {

    EditText drname, dremail, drpass, druser;
    Button drreg;
    TextView jumpsignin;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);
        getSupportActionBar().setTitle("Doctor SignUp");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Loadingbar loadingbar = new Loadingbar(DoctorSignup.this);
        firebaseAuth = FirebaseAuth.getInstance();
        drname = (EditText) findViewById(R.id.dr_name);
        druser = (EditText) findViewById(R.id.dr_user);
        dremail = (EditText) findViewById(R.id.dr_email);
        drpass = (EditText) findViewById(R.id.dr_pass);
        drreg = (Button) findViewById(R.id.dr_reg);
        jumpsignin = findViewById(R.id.gotosignin);

        jumpsignin.setOnClickListener(View->{
            startActivity(new Intent(DoctorSignup.this, Doctorlogin.class));
                }
                );



        drreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = drname.getText().toString().trim();
                String email = dremail.getText().toString().trim();
                String password = drpass.getText().toString().trim();
                String username = druser.getText().toString().trim();

                if (isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Name.", Toast.LENGTH_SHORT).show();

                    return;
                }
                else if (!name.matches("[a-zA-z]+")) {
                    drname.requestFocus();
                    drname.setError("name can only contain alphabetic characters");
                    return;
                }
                else if (isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Username.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!email.matches("[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    dremail.requestFocus();
                    dremail.setError("please enter a valid email");
                    return;
                }

                else if (isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (password.length()<6) {
                    drpass.requestFocus();
                    drpass.setError("Password should be at least 6 letters long");
                }

                else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctor");
                    dochelper addnewdoc = new dochelper(name, username, email, password);
                    Query checkusername = reference.orderByChild("username").equalTo(username);
                    loadingbar.showdialog();

                   checkusername.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()) {
                                reference.child(username).setValue(addnewdoc).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        loadingbar.dismissbar();
                                        Toast.makeText(getApplicationContext(), "Doctor Registration Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(DoctorSignup.this, Doctorlogin.class));

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
                                Toast.makeText(DoctorSignup.this, "Username Already exists", Toast.LENGTH_SHORT).show();
                                }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            loadingbar.dismissbar();
                            Toast.makeText(DoctorSignup.this, "Database error", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        } );

    }
}




