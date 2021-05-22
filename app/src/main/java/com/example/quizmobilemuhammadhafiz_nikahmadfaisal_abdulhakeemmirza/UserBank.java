package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

public class UserBank {
    private String userId;
    private String username;
    private String passwordhash;
    private int age;
    private String address;
    private String admin;

    public UserBank() {
    }

    public UserBank(String address, String admin, int age, String passwordhash, String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.passwordhash = passwordhash;
        this.age = age;
        this.address = address;
        this.admin = admin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
