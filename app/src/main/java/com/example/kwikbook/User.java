package com.example.kwikbook;

public class User {

    private long id;
    private String username;
    private String password;
    private String name;
    private String mobileNum;
    private String gender;

    public User() {
        // Default constructor
    }

    public User(String username, String password, String name, String mobileNum, String gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.mobileNum = mobileNum;
        this.gender = gender;
    }

    // Getter and Setter methods for each field

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

