package com.example.demo.comment;

import com.example.demo.person.Person;
import com.example.demo.post.Post;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "post_comment")
@NamedStoredProcedureQuery(
        name = "makeComment",
        procedureName = "makeComment",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "postID", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "personID", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_description", type = String.class),
        }
)

public class Comment {
    @Id
    @Column(name = "COMMENT_ID", nullable = false)
    private Long id;

    @Column(name = "PERSON_ID")
    private Long person_id;

    @Column(name = "POST_ID")
    private Long post_id;

    @Column(name = "DESCRIPTION", length = 10000)
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
