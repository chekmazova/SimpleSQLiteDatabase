package com.example.android.sqlitedatabase;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateInfoActivity extends AppCompatActivity {

    CornellDB cornellDB;
    EditText mName;
    EditText mLastName;
    EditText mAddress;
    EditText mID;

    GridView gridView;
    ArrayList<String> studentArrayList;
    ArrayAdapter<String> stringArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        mName = findViewById(R.id.txt_first_name1);
        mLastName = findViewById(R.id.txt_last_name1);
        mAddress = findViewById(R.id.txt_address1);
        mID = findViewById(R.id.txt_student_id);
        gridView = findViewById(R.id.grd_StInfo);

        cornellDB = new CornellDB(this);

        studentArrayList = new ArrayList<>();    
        stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentArrayList);
    }

    public void refresh (View view){
        stringArrayAdapter.clear();
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

    public void btn_Update (View view){
        int id = Integer.parseInt(mID.getText().toString());
        String first_name = mName.getText().toString();
        String last_name = mLastName.getText().toString();
        String address = mAddress.getText().toString();

        cornellDB.Open();
        if (cornellDB.updateStudentInfo(id, first_name, last_name,address) >0){
            Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
        cornellDB.Close();
        refresh(null);
        //null because we do not need to add a view ? we already have one. If it was a button, we should add the view
    }
    //It's better to use try and catch in this case!!!!

    public void btn_Delete (View view){
        int id = Integer.parseInt(mID.getText().toString());
        cornellDB.Open();
        if (cornellDB.deleteStudent(id)>0){
            Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();
        }
        cornellDB.Close();
        refresh(null);
    }
}
