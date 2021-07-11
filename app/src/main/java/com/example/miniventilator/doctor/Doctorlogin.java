package com.example.miniventilator.doctor;

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

public class Doctorlogin extends AppCompatActivity {



    EditText druserin;
    EditText drpassin;
    Button drlogin;
    TextView jumper;
    DatabaseReference reference1;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorlogin);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Doctor Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        reference1 = FirebaseDatabase.getInstance().getReference("Doctor");
        druserin = (EditText) findViewById(R.id.dr_userin);
        progressBar = (ProgressBar)findViewById(R.id.progress_bardoc);
        progressBar.setVisibility(View.GONE);
        drpassin = (EditText) findViewById(R.id.dr_passin);
        drlogin = (Button) findViewById(R.id.dr_login);
        jumper = findViewById(R.id.gotosignup);
        jumper.setOnClickListener(View -> {
            startActivity(new Intent(Doctorlogin.this, DoctorSignup.class));
        });

        drlogin.setOnClickListener(View -> {
            loginuser();
        });
    }


    public void loginuser() {
        String username = druserin.getText().toString().trim();
        String password = drpassin.getText().toString().trim();
        if (isEmpty(username)) {
            druserin.setError("Please Enter Username");
            druserin.requestFocus();
            return;
        } else if (isEmpty(password)) {
            drpassin.setError("Please Enter Password");
            drpassin.requestFocus();
            return;
        } else {
            Query checkuser = reference1.orderByChild("username").equalTo(username);
            progressBar.setVisibility(View.VISIBLE);
            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String passwordDB = snapshot.child(username).child("password").getValue(String.class);
                        if (passwordDB.equals(password)) {
                            /*
                            String nameDB = snapshot.child(username).child("fullname").getValue(String.class);
                            String usernameDB = snapshot.child(username).child("username").getValue(String.class);
                            String emailDB = snapshot.child(username).child("email").getValue(String.class);*/
                            preferences.setDataLogin(Doctorlogin.this, true);
                            preferences.setUserName(Doctorlogin.this, username);
                            preferences.setDataAs(Doctorlogin.this, "Doctor");
                            Intent intent = new Intent(Doctorlogin.this, DoctorDashboard.class);
                            /*
                            intent.putExtra("fullname", nameDB);
                            intent.putExtra("Username", usernameDB);
                            intent.putExtra("email", emailDB);
                            intent.putExtra("password", passwordDB);
                            */
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Doctorlogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Doctorlogin.this, "Password Incorrect", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Doctorlogin.this, "No such user exist.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Doctorlogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


}