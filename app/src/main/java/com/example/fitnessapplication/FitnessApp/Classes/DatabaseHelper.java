package com.example.fitnessapplication.FitnessApp.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String USERS_TABLE = "USERS_TABLE";
    public static final String COLUMN_USER_USERNAME = "USER_USERNAME";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_USER_AGE = "USER_AGE";
    public static final String COLUMN_USER_WEIGHT = "USER_WEIGHT";
    public static final String COLUMN_USER_HEIGHT = "USER_HEIGHT";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_USER_GENDER = "USER_GENDER";

    private int userId;
    private int userWeight;
    private int userHeight;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USERS_TABLE + " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_USERNAME + " TEXT," +
                COLUMN_USER_PASSWORD + " TEXT," +
                COLUMN_USER_AGE + " INT," +
                COLUMN_USER_WEIGHT + " INT," +
                COLUMN_USER_HEIGHT + " INT," +
                COLUMN_USER_GENDER + " TEXT )";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_USERNAME, user.getUsername());
        contentValues.put(COLUMN_USER_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_USER_AGE, user.getAge());
        contentValues.put(COLUMN_USER_WEIGHT, user.getWeight());
        contentValues.put(COLUMN_USER_HEIGHT, user.getHeight());
        contentValues.put(COLUMN_USER_GENDER, user.getGender());

        long insert = database.insert(USERS_TABLE, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean findUser(String username, String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + COLUMN_USER_USERNAME + " =? AND " + COLUMN_USER_PASSWORD + " =?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getIdOfUser(String username, String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + COLUMN_USER_USERNAME + " =? AND " + COLUMN_USER_PASSWORD + " =?", new String[]{username, password});
        if(cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(0);
            } while(cursor.moveToNext());
        }
        return userId;
    }

    public Cursor findDataForBmi(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE "  + COLUMN_USER_ID + " =?", new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(0);
            } while(cursor.moveToNext());
        }
        return cursor;
    }

    public boolean updateDataForBmi(int id ,int age, double weight, double height, String gender) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_AGE, age);
        contentValues.put(COLUMN_USER_WEIGHT, weight);
        contentValues.put(COLUMN_USER_HEIGHT, height);
        contentValues.put(COLUMN_USER_GENDER, gender);
        database.update(USERS_TABLE, contentValues, COLUMN_USER_ID + " =?", new String[] {String.valueOf(id)});
        return true;
    }



}
