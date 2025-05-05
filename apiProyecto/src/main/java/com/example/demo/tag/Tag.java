package com.example.demo.tag;

import jakarta.persistence.*;

@Entity
public class Tag {
    @Id
    @Column(name = "tage_id")
    private Long id;

    @Column(name = "tag_name", length = 40)
    private String tagName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
