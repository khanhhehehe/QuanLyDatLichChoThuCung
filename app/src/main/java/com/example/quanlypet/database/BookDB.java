package com.example.quanlypet.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quanlypet.dao.BookDao;
import com.example.quanlypet.model.BookObj;

@Database(entities = {BookObj.class},version = 2)
public abstract class BookDB extends RoomDatabase {
    public abstract BookDao Dao();
    static Migration migration = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Book ADD COLUMN id_user Integer");
            database.execSQL("ALTER TABLE Book ADD COLUMN obj_status Integer");
            database.execSQL("ALTER TABLE Book ADD COLUMN timeHold TEXT");

        }
    };
    public static final String DATABASENAME="Book2.db";
    public static BookDB Instance;
    public static synchronized BookDB getInstance(Context context){
        if(Instance ==null){
            Instance = Room.databaseBuilder(context,BookDB.class,DATABASENAME).
                    allowMainThreadQueries()
                    .addMigrations(migration)
                    .build();
        }
        return Instance;
    }
}