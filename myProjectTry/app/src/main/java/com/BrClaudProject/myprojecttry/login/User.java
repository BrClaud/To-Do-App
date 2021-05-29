package com.BrClaudProject.myprojecttry.login;

public class User {
    private String name, email, pass;
    private int gender;

    public User(String name, String email, String pass, int gender) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.gender= gender;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
