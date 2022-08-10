package com.example.newproject.model;

public class User {

    private String userID;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String qualification;

    public User() {
    }

    public User(String userID, String name, String email, String password, String phone, String qualification) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.qualification = qualification;
    }

    public User(String name, String email, String password, String phone, String qualification) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.qualification = qualification;
    }

    public User(String name, String email, String phone, String qualification) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.qualification = qualification;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
