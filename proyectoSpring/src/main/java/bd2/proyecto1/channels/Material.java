package bd2.proyecto1.channels;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class Material {

    private Long id;

    @NotEmpty(message = "The title cannot be empty")
    @Size(min = 3, max = 500, message = "The title must be between 3 and 500 characters long")
    private String title;

    @NotEmpty(message = "The content cannot be empty")
    @Size(max = 10000, message = "The content must be less than 10000 characters long")
    private String description;

    private Long channelId;

    public Material(Long id, String title, String description, Long channelId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.channelId = channelId;
    }

    public Material() {}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getChannel() {
        return channelId;
    }
}
