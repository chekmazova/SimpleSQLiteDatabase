package com.example.android.sqlitedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CornellDBSystem extends AppCompatActivity {

    CornellDB cornellDB;
    EditText firstName;
    EditText lastName;
    EditText stAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cornell_dbsystem);
        firstName = findViewById(R.id.txt_first_name);
        lastName = findViewById(R.id.txt_last_name);
        stAddress = findViewById(R.id.txt_address);

        cornellDB = new CornellDB(this);
        //cornellDB.getWritableDatabase();
        //No need in this function as we use open and close created by ourselves

        Button btnView = findViewById(R.id.btn_view);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentDetails.class);
                startActivity(intent);
            }
        });
    }

    public void btnRegister (View view){
        String first_name = firstName.getText().toString();
        String last_name = lastName.getText().toString();
        String address = stAddress.getText().toString();

        cornellDB.Open();
        boolean status = cornellDB.insert_student(first_name, last_name, address);
        cornellDB.Close();
        if (status ==true){
            Toast.makeText(CornellDBSystem.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(CornellDBSystem.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
