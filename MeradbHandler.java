package com.example.hanish.bmianalysis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hanish on 19-06-2018.
 */

public class MeradbHandler extends SQLiteOpenHelper{
    Context context;
    SQLiteDatabase db;
    String table = "user";



    public MeradbHandler(Context context) {
        super(context,"User.db",null,1);
        db = this.getWritableDatabase();
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+table+"(BMI INTEGER , NAME TEXT , DATE TEXT "+")";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS"+table);

    }




    public void addData(int BMI,String NAME,String DATE)
    {
        ContentValues cv = new ContentValues();
        cv.put("BMI",BMI);
        cv.put("NAME",NAME);
        cv.put("DATE",DATE);
        long rid = db.insert("user",null,cv);
        if(rid < 0)
        {
            Toast.makeText(context, "Issue", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Data Added", Toast.LENGTH_SHORT).show();
        }

    }

    public void Clear()
    {
        SQLiteDatabase dba = this.getWritableDatabase();
        try {

            String Drop = "DROP IF TABLE EXISTS" + table;
            dba.execSQL(Drop);
            Toast.makeText(context, "Cleared the History", Toast.LENGTH_SHORT).show();
        }
            catch (Exception e){
                Toast.makeText(context, "History has been already cleared", Toast.LENGTH_SHORT).show();
                return;

            }

    }



    public String viewUser()
    {
        Cursor cursor = db.query("user",null,null,null,null,null,null);
        StringBuffer sb = new StringBuffer();
        cursor.moveToFirst();


        if (cursor.getCount()>0)
        do{

            sb.append("----------------------------------------------------------------------------------\n");
            sb.append("The BMI is "+ cursor.getString(0)+" of "+cursor.getString(1)+" at "+cursor.getString(2)+"\n");

        }while (cursor.moveToNext());
        return sb.toString();

    }
}

