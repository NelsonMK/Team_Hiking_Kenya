package com.teamhikingkenya.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.teamhikingkenya.model.HikingPlaces;

@Database(entities = {HikingPlaces.class}, version = 1, exportSchema = false)
public abstract class HikingPlacesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "HikingPlacesDb";
    private static HikingPlacesDatabase instance;
    public abstract HikingPlacesDao hikingPlacesDao();

    public synchronized static HikingPlacesDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, HikingPlacesDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
}
