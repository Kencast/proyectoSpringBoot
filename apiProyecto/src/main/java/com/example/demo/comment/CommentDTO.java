package com.example.demo.comment;

public class CommentDTO {
    private Long id;
    private String description;
    private Long personId;
    private String userName;
    private String imageLink;

    // Constructor por defecto
    public CommentDTO() {
    }

    // Constructor con todos los atributos
    public CommentDTO(Long id, String description, Long personId, String userName, String imageLink) {
        this.id = id;
        this.description = description;
        this.personId = personId;
        this.userName = userName;
        this.imageLink = imageLink;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getUserName() {
        return userName;
    }

    public String getImageLink() {
        return imageLink;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

}

