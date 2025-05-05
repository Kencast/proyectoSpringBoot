package bd2.proyecto1.home;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class NewComment {
    private Long id;
    private Long person_id;
    private Long post_id;

    @NotEmpty(message = "The description cannot be empty")
    @Size(max = 10000, message = "The description must be less than 10000 characters long")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
