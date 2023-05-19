package com.example.quanlypet.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quanlypet.dao.AnimalDao;
import com.example.quanlypet.model.AnimalObj;

@Database(entities = {AnimalObj.class}, version = 1)
public abstract class AnimalDB extends RoomDatabase {
    public abstract AnimalDao Dao();
    public static final String DATABASENAME = "Animal.db";
    public static AnimalDB Instance;
    public static synchronized AnimalDB getInstance(Context context){
        if (Instance == null){
            Instance = Room.databaseBuilder(context,AnimalDB.class, DATABASENAME).
                    allowMainThreadQueries().build();
        }
        return Instance;
    }
}
