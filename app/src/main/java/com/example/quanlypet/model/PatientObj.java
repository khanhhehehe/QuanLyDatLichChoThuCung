package com.example.quanlypet.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Patient")
public class PatientObj {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int id_doctor;
    private int id_animal;
    private String date_create;
    private String date_update;
    private String sysptems ;
    private String diagnostic ;
    private String instructions;


    public PatientObj() {
    }

    public PatientObj(int id_doctor, int id_animal, String date_create, String date_update, String sysptems, String diagnostic, String instructions) {
        this.id_doctor = id_doctor;
        this.id_animal = id_animal;
        this.date_create = date_create;
        this.date_update = date_update;
        this.sysptems = sysptems;
        this.diagnostic = diagnostic;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(int id_doctor) {
        this.id_doctor = id_doctor;
    }

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public String getDate_update() {
        return date_update;
    }

    public void setDate_update(String date_update) {
        this.date_update = date_update;
    }
    public String getSysptems() {
        return sysptems;
    }

    public void setSysptems(String sysptems) {
        this.sysptems = sysptems;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
