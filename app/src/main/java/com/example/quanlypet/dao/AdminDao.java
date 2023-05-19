package com.example.quanlypet.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlypet.model.AdminObj;
import com.example.quanlypet.model.UsersObj;

import java.util.List;
@Dao
public interface AdminDao {
    @Insert
    void insert(AdminObj object);

    @Query("SELECT * FROM Admin")
    List<AdminObj> getAllData();

    @Update
    void edit(AdminObj object);

    @Query("SELECT * FROM Admin WHERE import_name = :user AND password = :password")
    int checkLogin(String user, String password);

    @Query("UPDATE Admin SET password = :pass WHERE import_name = :ID")
    void changePass(String ID, String pass);

    @Query("Select * From Admin where  import_name = :name")
    AdminObj getIdAdmin(String name);
}
