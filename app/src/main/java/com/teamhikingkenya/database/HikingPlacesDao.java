package com.teamhikingkenya.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.teamhikingkenya.model.HikingPlaces;

import java.util.List;

@Dao
public interface HikingPlacesDao {

    @Query("SELECT * FROM hikingplaces")
    public LiveData<List<HikingPlaces>> getHikingPlaces();

    @Query("SELECT id FROM hikingplaces WHERE id = :id LIMIT 1")
    public boolean hikingPlaceExists(int id);

    @Insert
    public void insert(HikingPlaces hikingPlaces);

    @Update
    public void update(HikingPlaces hikingPlaces);

    @Delete
    public void delete(HikingPlaces hikingPlaces);

}
