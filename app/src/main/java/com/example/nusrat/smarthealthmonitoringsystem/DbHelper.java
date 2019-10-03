package com.example.nusrat.smarthealthmonitoringsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="TODOTASK";
    private static final int DB_VER=1;
    private static final String DB_TABLE="TASKS";
    private static final String DB_Colum="TASKNAMES";
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query=String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL);",DB_TABLE,DB_Colum);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query=String.format("DELETE TABLE IF EXISTS %s ",DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insetNewTask(String Task){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DB_Colum,Task);
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String Task){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(DB_TABLE,DB_Colum + " =?",new String[]{Task});
        db.close();
    }

    public ArrayList<String>getTaskList(){
        ArrayList<String>TaskList=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query(DB_TABLE,new String[]{DB_Colum},null,null,null,null,null);
        while (cursor.moveToNext()){
            int index=cursor.getColumnIndex(DB_Colum);
            TaskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return TaskList;
    }
}
