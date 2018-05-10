package com.example.android.sqlitedatabase;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class StudentDetails extends AppCompatActivity {

    GridView gridView;
    CornellDB cornellDB;
    ArrayList<String> studentArrayList;
    ArrayAdapter<String> stringArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        cornellDB = new CornellDB(this);
        gridView = findViewById(R.id.grdvwStudent);
        //we put inside the Student ArrayList
        studentArrayList = new ArrayList<String>();
        stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, studentArrayList);


        cornellDB.Open();
        //SQL get adapter
        Cursor cursor = cornellDB.getAllStudents();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String first_name = cursor.getString(2);
            String last_name = cursor.getString(3);
            String address = cursor.getString(1);

            studentArrayList.add(String.valueOf(id));
            studentArrayList.add(first_name);
            studentArrayList.add(last_name);
            studentArrayList.add(address);
        }

        cornellDB.Close();
        gridView.setAdapter(stringArrayAdapter); //function accepts only adapter
    }
}
