package com.example.miniventilator.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import static android.text.TextUtils.isEmpty;
import com.example.miniventilator.R;
import com.example.miniventilator.instructionhelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddInstructions extends AppCompatActivity {
    EditText breath_permin;
    EditText breath_length;
    EditText instructions;
    String textdata = "";
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructions);
        breath_permin = (EditText) findViewById(R.id.breathpermin);
        breath_length = (EditText) findViewById(R.id.breathlength);
        instructions = (EditText) findViewById(R.id.instructs);
        getSupportActionBar().setTitle("Add Instructions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public void add_instructs(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Instructions");
        String firstvalue = breath_permin.getText().toString();
        String secondvalue = breath_length.getText().toString();
        String thirdvalue = instructions.getText().toString();
        if(isEmpty(firstvalue)){
            Toast.makeText(this, "the field is empty", Toast.LENGTH_SHORT).show();
        }

        //test
        else if(Integer.parseInt(breath_permin.getText().toString())<0 || Integer.parseInt(breath_permin.getText().toString()) ==0){
            breath_permin.requestFocus();
            breath_permin.setError("you cannot enter 0 or negative values");
            return;
        }
        else if(isEmpty(secondvalue)){
            Toast.makeText(this, "the field is empty", Toast.LENGTH_SHORT).show();
        }
        else if(Integer.parseInt(breath_length.getText().toString())<0 || Integer.parseInt(breath_length.getText().toString()) ==0){
            breath_permin.requestFocus();
            breath_permin.setError("you cannot enter 0 or negative values");
            return;
        }


        //end

        else {
            instructionhelper instructionhelper = new instructionhelper(
                    firstvalue,
                    secondvalue,
                    thirdvalue
            );
            databaseReference.setValue(instructionhelper).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    instructions.setText("");
                    Toast.makeText(AddInstructions.this, "Instructions added successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}