package com.example.android.sqlitedatabase;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentDetails extends AppCompatActivity {

    GridView gridView;
    CornellDB cornellDB;
    ArrayList<String> studentArrayList;
    ArrayAdapter<String> stringArrayAdapter;
    EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        cornellDB = new CornellDB(this);
        gridView = findViewById(R.id.grdvwStudent);
        //we put inside the Student ArrayList
        studentArrayList = new ArrayList<>();
        stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentArrayList);
        editSearch = findViewById(R.id.txt_search);

        //OnClick listener to display a toast by clicking of each item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            }
        });


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

    //function to search
    public void getSearchStudent (View view){
        int id = Integer.parseInt(editSearch.getText().toString());

        stringArrayAdapter.clear();
        cornellDB.Open();
        Cursor cursor1 = cornellDB.getStudentById(id);
        if (cursor1.getCount()>0){
            int idd = cursor1.getInt(0);
            String first_name = cursor1.getString(2);
            String last_name = cursor1.getString(3);
            String address = cursor1.getString(1);

            studentArrayList.add(String.valueOf(id));
            studentArrayList.add(first_name);
            studentArrayList.add(last_name);
            studentArrayList.add(address);
        }
        cornellDB.Close();
        gridView.setAdapter(stringArrayAdapter);
    }
}
