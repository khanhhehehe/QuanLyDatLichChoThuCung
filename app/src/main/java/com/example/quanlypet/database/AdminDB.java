package com.example.quanlypet.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quanlypet.dao.AdminDao;
import com.example.quanlypet.model.AdminObj;

@Database(entities = {AdminObj.class},version = 1)
public abstract class AdminDB extends RoomDatabase {
    public abstract AdminDao Dao();
    public static final String DATABASENAME="Admin.db";
    public static AdminDB Instance;
    public static synchronized AdminDB getInstance(Context context){
        if(Instance ==null){
            Instance = Room.databaseBuilder(context,AdminDB.class,DATABASENAME).
                    allowMainThreadQueries().build();
        }
        return Instance;
    }
}
