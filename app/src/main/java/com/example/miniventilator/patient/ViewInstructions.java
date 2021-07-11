package com.example.miniventilator.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniventilator.R;
import com.example.miniventilator.instructionhelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewInstructions extends AppCompatActivity {
    TextView breaths;
    TextView lengthbreath;
    TextView instructions1;
    TextView heading3;
    TextView heading4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instructions);
        getSupportActionBar().setTitle("Doctor Instructions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        breaths = (TextView) findViewById(R.id.BPM);
        lengthbreath = (TextView) findViewById(R.id.BLM);
        instructions1 = (TextView) findViewById(R.id.instructions);
        heading3 = (TextView) findViewById(R.id.heading1);
        heading4 = (TextView) findViewById(R.id.heading2);
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Instructions");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                instructionhelper object = snapshot.getValue(instructionhelper.class);
                String breath_perminute = object.breathperminute;
                String breath_length = object.breathlength;
                String instruction = object.instructions;
                breaths.setText(breath_perminute);
                lengthbreath.setText(breath_length);
                instructions1.setText(instruction);

                Toast.makeText(ViewInstructions.this, "great", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewInstructions.this, "unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}