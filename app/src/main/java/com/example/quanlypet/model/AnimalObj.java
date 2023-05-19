package com.example.quanlypet.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Animal")
public class AnimalObj implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int id_users;
    private String name;
    private byte[] avatar;
    private int age;
    private String species;
    private int status_obj;

    public AnimalObj(int id_users, String name, byte[] avatar, int age, String species, int status_obj) {
        this.id_users = id_users;
        this.name = name;
        this.avatar = avatar;
        this.age = age;
        this.species = species;
        this.status_obj = status_obj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_users() {
        return id_users;
    }

    public void setId_users(int id_users) {
        this.id_users = id_users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getStatus_obj() {
        return status_obj;
    }

    public void setStatus_obj(int status_obj) {
        this.status_obj = status_obj;
    }
}
