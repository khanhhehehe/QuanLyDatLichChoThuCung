package com.example.quanlypet.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quanlypet.dao.UsersDao;
import com.example.quanlypet.model.UsersObj;

@Database(entities = {UsersObj.class},version = 1)
public abstract class UsersDB extends RoomDatabase {
    public abstract UsersDao Dao();
    public static final String DATABASENAME="Users.db";
    public static UsersDB Instance;
    public static synchronized UsersDB getInstance(Context context){
        if(Instance ==null){
            Instance = Room.databaseBuilder(context,UsersDB.class,DATABASENAME).
                    allowMainThreadQueries().build();
        }
        return Instance;
    }
}