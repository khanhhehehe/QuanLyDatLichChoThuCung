package com.example.quanlypet.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Bill")
public class BillObj {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int id_case_file;
    private int id_users;
    private String time;
    private String date;
    private double price;
    private String note;

    public BillObj(int id_users, String time, String date, double price, String note) {
        this.id_users = id_users;
        this.time = time;
        this.date = date;
        this.price = price;
        this.note = note;

    }

    public int getId_users() {
        return id_users;
    }

    public void setId_users(int id_users) {
        this.id_users = id_users;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_case_file() {
        return id_case_file;
    }

    public void setId_case_file(int id_case_file) {
        this.id_case_file = id_case_file;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
