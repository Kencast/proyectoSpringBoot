package com.example.demo.person;

import java.time.LocalDate;

public class RegisterInfo {
    private String name;
    private String firstName;
    private String secondName;
    private String userName;
    private String country;
    private LocalDate birthdate;
    private String email;
    private String password;
    private String imageLink;

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getPersonPassword() {
        return password;
    }

    public void setPassword(String personPassword) {
        this.password = personPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPersonName() {
        return name;
    }

    public void setName(String personName) {
        this.name = personName;
    }
}