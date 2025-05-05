package com.example.demo.person;

public class LoginInfo {
    private String email;
    private String password;

    public String getEmail() {
        return this.email;
    }

    public LoginInfo() {

    }

    public LoginInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getHashPassword() {
        return Long.valueOf((long) password.hashCode());
    }

}