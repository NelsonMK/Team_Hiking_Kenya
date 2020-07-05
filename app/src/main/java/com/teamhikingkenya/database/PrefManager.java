package com.teamhikingkenya.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.teamhikingkenya.MainActivity;
import com.teamhikingkenya.model.User;

public class PrefManager {

    private static final String SHARED_PREF_NAME = "team_hiking_kenya";
    private static final String KEY_ID = "user_id";
    private static final String KEY_CLASS = "user_class";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    @SuppressLint("StaticFieldLeak")
    private static PrefManager mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;

    private PrefManager (Context context) {
        mCtx = context;
    }

    public static synchronized PrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PrefManager(context);
        }
        return mInstance;
    }

    public void setUserLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putInt(KEY_CLASS, user.getClass_id());
        editor.putBoolean(KEY_IS_LOGGED_IN,true);
        editor.apply();
    }

    public int userId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID, -1);
    }

    public int classId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_CLASS, -1);
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        DatabaseHandler db = new DatabaseHandler(mCtx);
        db.resetTables();
        Intent logOut = new Intent(mCtx, MainActivity.class);
        logOut.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
        mCtx.startActivity(logOut);
    }

}
