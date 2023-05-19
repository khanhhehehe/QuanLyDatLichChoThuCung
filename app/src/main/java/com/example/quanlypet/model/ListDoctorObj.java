package com.example.quanlypet.model;

public class ListDoctorObj {
    private String name;
    private int img;
    private String phone;

    public ListDoctorObj() {
    }

    public ListDoctorObj(String name, int img, String phone) {
        this.name = name;
        this.img = img;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
