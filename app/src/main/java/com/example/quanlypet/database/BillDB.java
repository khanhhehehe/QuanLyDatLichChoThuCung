package com.example.quanlypet.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quanlypet.dao.BillDao;
import com.example.quanlypet.model.BillObj;

@Database(entities = {BillObj.class},version = 1)
public abstract class BillDB extends RoomDatabase {
    public abstract BillDao Dao();
    public static final String DATABASENAME="Bill6.db";
    public static BillDB Instance;
    public static synchronized BillDB getInstance(Context context){
        if(Instance ==null){
            Instance = Room.databaseBuilder(context,BillDB.class,DATABASENAME).
                    allowMainThreadQueries().build();
        }
        return Instance;
    }
}