package com.example.quanlypet.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quanlypet.dao.PatientDao;
import com.example.quanlypet.model.PatientObj;

@Database(entities = {PatientObj.class},version = 1)
public abstract class PatientDB extends RoomDatabase {
    private static final String DATABASENAME = "Patient.db";
    private static PatientDB Instance;
    public abstract PatientDao Dao();

    public static synchronized PatientDB getInstance(Context context){
        if (Instance==null){
            Instance = Room.databaseBuilder(context,PatientDB.class,DATABASENAME).
                    allowMainThreadQueries().build();
        }
        return Instance;
    }
}
