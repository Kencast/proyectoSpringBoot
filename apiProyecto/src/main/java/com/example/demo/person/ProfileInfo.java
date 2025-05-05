package com.example.demo.person;

public class ProfileInfo {
    private String name;
    private String firstName;
    private String secondName;
    private String userName;
    private String country;
    private String birthdate;
    private String email;
    private Long totalFollowers;
    private Long postAmount;
    private Long totalFollowing;
    private String profileUrl;

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public Long getPostAmount() {
        return postAmount;
    }

    public void setPostAmount(Long postAmount) {
        this.postAmount = postAmount;
    }

    public ProfileInfo(String name, String firstName, String secondName, String userName, String country,
            String birthdate, String email, Long totalFollowers, Long postAmount, Long totalFollowing,
            String profileUrl) {
        this.name = name;
        this.firstName = firstName;
        this.secondName = secondName;
        this.userName = userName;
        this.country = country;
        this.birthdate = birthdate;
        this.email = email;
        this.totalFollowers = totalFollowers;
        this.postAmount = postAmount;
        this.totalFollowing = totalFollowing;
        this.profileUrl = profileUrl;
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

    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(String birthdate) {
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

    public void setName(String personName) {
        this.name = personName;
    }

    public String getName() {
        return name;
    }

    public Long getTotalFollowers() {
        return totalFollowers;
    }

    public void setTotalFollowers(Long totalFollowers) {
        this.totalFollowers = totalFollowers;
    }

    public Long getTotalFollowing() {
        return totalFollowing;
    }

    public void setTotalFollowing(Long totalFollowing) {
        this.totalFollowing = totalFollowing;
    }
}
