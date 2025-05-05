package bd2.proyecto1.home;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PostViewDTO {
    private Long id;

    @NotEmpty(message = "The title cannot be empty")
    @Size(min = 3, max = 500, message = "The title must be between 3 and 500 characters long")
    private String title;

    @NotEmpty(message = "The content cannot be empty")
    @Size(max = 10000, message = "The content must be less than 10000 characters long")
    private String description;
    private String publicationDate;
    private Long personId;
    private String userName;
    private String imageLink;

    public PostViewDTO(Long id, String title, String description, String publicationDate,
                       Long personId, String userName, String imageLink) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
        this.personId = personId;
        this.userName = userName;
        this.imageLink = imageLink;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublicationDate() {
        return publicationDate;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
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