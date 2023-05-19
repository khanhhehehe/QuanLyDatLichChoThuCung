package com.example.quanlypet.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlypet.model.PatientObj;

import java.util.List;


@Dao
public interface PatientDao {
    @Insert
    void insert(PatientObj patientObj);

    @Query("SELECT * FROM Patient")
    List<PatientObj> getAllData();

    @Update
    void edit(PatientObj patientObj);

    @Query("SELECT * FROM patient where id_animal = :id")
    List<PatientObj> getIDAnimal(String id);
}
