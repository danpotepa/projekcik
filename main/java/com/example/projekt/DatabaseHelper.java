package com.example.projekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Alarm_Manager";
    private static final String TABLE_NAME = "Alarm";

    private static final String COLUMN_AUTO_ID = "Auto_Id";
    private static final String COLUMN_AUTO_HOUR = "Auto_Hour";
    private static final String COLUMN_AUTO_MINUTE = "Auto_Minute";
    private static final String COLUMN_AUTO_STATUS = "Auto_Status";
    private static final String COLUMN_AUTO_NAME = "Auto_Name";

    public DatabaseHelper (Context context){
        super(context, DATABASE_NAME,  null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_AUTO_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_AUTO_HOUR + " INTEGER,"
                + COLUMN_AUTO_MINUTE + " INTEGER,"
                + COLUMN_AUTO_STATUS + " BOOLEAN,"
                + COLUMN_AUTO_NAME + " STRING"
                + ")";
        db.execSQL(script);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public void addAuto (Auto auto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTO_HOUR, auto.getHour());
        values.put(COLUMN_AUTO_MINUTE, auto.getMinute());
        values.put(COLUMN_AUTO_STATUS, auto.getStatus());
        values.put(COLUMN_AUTO_NAME, auto.getName());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public List<Auto> getAllAuto(){

        List<Auto> autoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Auto auto = new Auto();
                auto.setId(cursor.getInt(0));
                auto.setHour(cursor.getInt(1));
                auto.setMinute(cursor.getInt(2));
                auto.setStatus(cursor.getInt(3) != 0);
                auto.setName(cursor.getString(4));
                autoList.add(auto);
            }while (cursor.moveToNext());
        }
        return autoList;

    }
    public int updateAuto(Auto auto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTO_HOUR, auto.getHour());
        values.put(COLUMN_AUTO_MINUTE, auto.getMinute());
        values.put(COLUMN_AUTO_STATUS, auto.getStatus());
        values.put(COLUMN_AUTO_NAME, auto.getName());

        return db.update(TABLE_NAME, values, COLUMN_AUTO_ID + " = ?",
                new String[]{String.valueOf(auto.getId())});
    }
}
