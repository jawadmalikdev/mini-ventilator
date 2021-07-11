package com.example.miniventilator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SensorValues extends AppCompatActivity {
    TextView temprature, heartrate, bloodoxygen, oxygenpressure;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_values);
        getSupportActionBar().setTitle("Sensor Values");
        temprature = (TextView) findViewById(R.id.temp_sensor);
        heartrate = (TextView) findViewById(R.id.heart_sensor);
        bloodoxygen = (TextView) findViewById(R.id.temp_blood_oxygen);
        oxygenpressure = (TextView) findViewById(R.id.pressure_sensor);

        setSensorValues();

    }

    public void setSensorValues() {
        // code for setting sensor values
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                   String var_temprature = snapshot.child("DegreeC").getValue().toString();
                   String var_faren = snapshot.child("Farhenheit").getValue().toString();
                   String bpm = snapshot.child("Heart Rate").getValue().toString();
                   String var_Spo2 = snapshot.child("Spo2").getValue().toString();

                   //test question
                    if(Float.valueOf(var_temprature)>30){

                        Toast.makeText(getApplicationContext(), "warning the temprature has exceeded the normal value", Toast.LENGTH_LONG).show();
                    }
                    if(Float.valueOf(var_faren)>70){
                        Toast.makeText(getApplicationContext(), "warning the temprature has exceeded the normal value", Toast.LENGTH_LONG).show();
                    }
                    //
                   
                   temprature.setText(var_temprature);
                   heartrate.setText(var_faren);
                  bloodoxygen.setText(bpm);
                  oxygenpressure.setText(var_Spo2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SensorValues.this, "Database Problem.", Toast.LENGTH_SHORT).show();
            }
        });


    }
    }