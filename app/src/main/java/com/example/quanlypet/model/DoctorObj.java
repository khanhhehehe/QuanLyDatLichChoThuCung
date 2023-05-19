package com.example.quanlypet.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Doctor")
public class DoctorObj {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    byte[] img;
    private String phone;
    private String email;
    private String address;
    private int gender;
    private String specialize;

    public DoctorObj() {
    }

    public DoctorObj(String name,byte[] img, String phone, String email, String address, int gender, String specialize) {
        this.name = name;
        this.img=img;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.specialize = specialize;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getSpecialize() {
        return specialize;
    }

    public void setSpecialize(String specialize) {
        this.specialize = specialize;
    }
}
