package com.example.newproject.model;

public class Review {

    private String r_name;
    private String r_message;

    public Review() {
    }

    public Review(String r_name, String r_message) {
        this.r_name = r_name;
        this.r_message = r_message;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public String getR_message() {
        return r_message;
    }

    public void setR_message(String r_message) {
        this.r_message = r_message;
    }
}
