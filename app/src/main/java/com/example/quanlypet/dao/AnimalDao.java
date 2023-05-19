package com.example.quanlypet.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlypet.model.AnimalObj;
import com.example.quanlypet.model.UsersObj;

import java.util.List;
@Dao
public interface AnimalDao {
    @Insert
    void insert(AnimalObj object);

    @Query("SELECT * FROM Animal")
    List<AnimalObj> getAllData();

    @Update
    void edit(AnimalObj object);

    @Query("SELECT *FROM animal where id =:id ")
    AnimalObj getIDAnimal(String id);

    @Query("SELECT * FROM animal where id_users = :id")
    List<AnimalObj> getIDUsers(String id);
}
