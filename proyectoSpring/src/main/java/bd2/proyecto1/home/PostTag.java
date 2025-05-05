package bd2.proyecto1.home;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class PostTag {

    private Long id;

    @NotEmpty(message = "The title cannot be empty")
    @Size(min = 3, max = 500, message = "The title must be between 3 and 500 characters long")
    private String title;

    @NotEmpty(message = "The content cannot be empty")
    @Size(max = 10000, message = "The content must be less than 10000 characters long")
    private String description;

    private Long personId;

    private String tags;

    private List<String> listTags = new ArrayList<>();

    public List<String> getListTags() {
        return listTags;
    }

    public void setListTags(List<String> listTags) {
        this.listTags = listTags;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long person_id) {
        this.personId = person_id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
