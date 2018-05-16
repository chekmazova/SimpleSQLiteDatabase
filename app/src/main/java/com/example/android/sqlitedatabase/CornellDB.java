package com.example.android.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class CornellDB extends SQLiteOpenHelper {
    private static String database_name = "cornel.db";
    private static int database_edition = 3;
    private static String table_1 = "tbl_Student";
    private Context context;
    private SQLiteDatabase db;
    //define column_1 in order to search by ID
    private static String column_1="ID";

    public CornellDB (Context context){
        super(context, database_name, null, database_edition);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table " + table_1 + "(ID integer PRIMARY KEY AUTOINCREMENT, address text, first_name varchar(50), last_name varchar(50))");
            Toast.makeText(context, "Student Table CREATED", Toast.LENGTH_SHORT).show();
        }
        catch (SQLException e){e.printStackTrace();}
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ table_1);
        Toast.makeText(context, "Student Table is Updated", Toast.LENGTH_SHORT).show();
        onCreate(db);
    }

    public void Open(){
        db = this.getWritableDatabase();
    }

    public void  Close(){
        db.close();
    }

    public boolean insert_student(String f_name, String l_name, String address){
        ContentValues cv = new ContentValues();
        cv.put("first_name", f_name);
        cv.put("last_name", l_name);
        cv.put("address", address);
        Long status = db.insert(table_1, null, cv);
        if (status!= -1){return true;}
        else {return false;}
    }

    public Cursor getAllStudents() {
        Cursor cursor;
        cursor = db.rawQuery("select * from " + table_1, null); //null - additional parametrs

        return cursor;
    }

    //Cursor for searching student by ID
    public  Cursor getStudentById (int id){
        Cursor cursor = db.query(table_1, null, column_1 + " =?", new String[]{id+""}, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }


}
