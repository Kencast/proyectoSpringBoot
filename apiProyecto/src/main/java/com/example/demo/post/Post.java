package com.example.demo.post;

import com.example.demo.person.Person;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "POST")
@NamedStoredProcedureQuery(
        name = "createPost",
        procedureName = "createPost",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "title_c", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "description", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "personId", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "tags", type = String.class),
        }
)
@NamedStoredProcedureQuery(
        name="deletePost",
        procedureName="delete_post_by_id",
        parameters = {
                @StoredProcedureParameter(mode=ParameterMode.IN, name="d_post_id", type = Long.class),
        }
)
//u_post_id in int,
//u_title in varchar2,
//u_description in varchar2,
//u_person_id  in varchar2
@NamedStoredProcedureQuery(
        name="updatePost",
        procedureName = "update_post",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name="u_post_id", type=Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name="u_title", type=String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name="u_description", type=String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name="u_person_id", type=Long.class),
        }
)
public class Post {

    @Id
    @Column(name = "POST_ID", nullable = false)
    private Long id;

    @Column(name = "TITLE", length = 500)
    private String title;

    @Column(name = "DESCRIPTION", length = 10000)
    private String description;

    @Column(name = "PUBLICATION_DATE")
    private LocalDate publicationDate;

    @Column(name = "PERSON_ID")
    private Long person_id;

    public Post(Long id, String title, String description,Long person_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.person_id = person_id;
    }

    public Post() {

    }

    public Long getPerson() {
        return person_id;
    }

    public void setPerson(Long person) {
        this.person_id = person;
    }

    public String getPublicationDate() {
        return publicationDate.getDayOfMonth() + "-" + publicationDate.getMonthValue()+ "-" + publicationDate.getYear();
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}