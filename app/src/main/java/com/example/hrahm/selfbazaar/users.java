package com.example.hrahm.selfbazaar;

import com.google.gson.annotations.SerializedName;

public class users {

    @SerializedName("id")
    private String Id;

    @SerializedName("email")
    private String Email;

    @SerializedName("pass")
    private String Pass;

    @SerializedName("dob")
    private String Dob;

    @SerializedName("name")
    private String Name;

    @SerializedName("i_path")
    private String Ipath;

    @SerializedName("phone")
    private String Phone;


    public String getEmail() {
        return Email;
    }

    public String getPass() {
        return Pass;
    }

    public String getDob() {
        return Dob;
    }

    public String getName() {
        return Name;
    }

    public String getIpath() {
        return Ipath;
    }

    public String getPhone() {
        return Phone;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setIpath(String ipath) {
        Ipath = ipath;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
