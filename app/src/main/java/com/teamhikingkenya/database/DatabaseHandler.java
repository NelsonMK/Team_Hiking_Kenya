package com.teamhikingkenya.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.teamhikingkenya.model.User;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "team_hiking_kenya";
    // Table names
    private static final String TABLE_PROFILE = "profile";
    // Profile Table Columns names
    private static final String USER_ID = "id";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_PHONE = "phone";
    private static final String USER_EMAIL = "email";
    private static final String USER_GENDER = "gender";
    private static final String USER_D_O_B = "date_of_birth";
    private static final String USER_LOCATION = "location";
    private static final String USER_CLASS = "class";
    private static final String USER_STATUS = "status";
    private static final String USER_ARCHIVE = "archive";

    //private Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String profile_sql = "CREATE TABLE " + TABLE_PROFILE
                +"(" + USER_ID + " INTEGER PRIMARY KEY, "
                + USER_FIRST_NAME + " TEXT, "
                + USER_LAST_NAME + " TEXT, "
                + USER_PHONE + " VARCHAR, "
                + USER_EMAIL + " VARCHAR, "
                + USER_GENDER + " VARCHAR, "
                + USER_D_O_B + " VARCHAR, "
                + USER_LOCATION + " INTEGER, "
                + USER_CLASS + " INTEGER, "
                + USER_STATUS + " INTEGER, "
                + USER_ARCHIVE + " INTEGER " + ");";

        db.execSQL(profile_sql);

    }

    // Upgrading Tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        // Create tables again
        onCreate(db);
    }

    /**
     * start of profile operations
     */
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getId());
        values.put(USER_FIRST_NAME, user.getFirst_name());
        values.put(USER_LAST_NAME, user.getLast_name());
        values.put(USER_PHONE, user.getPhone());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_GENDER, user.getGender());
        values.put(USER_D_O_B, user.getDate_of_birth());
        values.put(USER_LOCATION, user.getLocation());
        values.put(USER_CLASS, user.getClass_id());
        values.put(USER_STATUS, user.getStatus());
        values.put(USER_ARCHIVE, user.getArchive());
        // Inserting Row
        db.insert(TABLE_PROFILE, null, values);
        db.close(); // Closing database connection
    }

    public User getUser(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_PROFILE + " WHERE " + USER_ID + " = '"+ id + "'";
        Cursor cursor = db.rawQuery(sql, null);

        if ( cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        User user = new User(
                cursor.getInt(cursor.getColumnIndex(USER_ID)),
                cursor.getString(cursor.getColumnIndex(USER_FIRST_NAME)),
                cursor.getString(cursor.getColumnIndex(USER_LAST_NAME)),
                cursor.getString(cursor.getColumnIndex(USER_PHONE)),
                cursor.getString(cursor.getColumnIndex(USER_EMAIL)),
                cursor.getString(cursor.getColumnIndex(USER_GENDER)),
                cursor.getString(cursor.getColumnIndex(USER_D_O_B)),
                cursor.getString(cursor.getColumnIndex(USER_LOCATION)),
                cursor.getInt(cursor.getColumnIndex(USER_CLASS)),
                cursor.getInt(cursor.getColumnIndex(USER_STATUS)),
                cursor.getInt(cursor.getColumnIndex(USER_ARCHIVE))
        );

        cursor.close();
        db.close();

        return user;

    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_FIRST_NAME, user.getFirst_name());
        values.put(USER_LAST_NAME, user.getLast_name());
        values.put(USER_PHONE, user.getPhone());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_GENDER, user.getGender());
        values.put(USER_D_O_B, user.getDate_of_birth());
        values.put(USER_LOCATION, user.getLocation());

        db.update(TABLE_PROFILE, values, USER_ID + " = ? ", new String[]{String.valueOf(user.getId())});

        db.close();
    }

    /**
     * Re create database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PROFILE, null, null);
        db.close();
    }
}
