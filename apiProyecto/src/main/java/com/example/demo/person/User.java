package com.example.demo.person;

public class User {
    private Long id;
    private String userName;
    private String profileUrl;
    private Long totalPosts;

    public User(Long id, String userName, String profileUrl, Long totalPosts) {
        this.id = id;
        this.userName = userName;
        this.profileUrl = profileUrl;
        this.totalPosts = totalPosts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public Long getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(Long totalPosts) {
        this.totalPosts = totalPosts;
    }

}
