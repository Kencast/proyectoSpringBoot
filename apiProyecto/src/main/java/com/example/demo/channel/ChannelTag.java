package com.example.demo.channel;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class ChannelTag{
    private String channelName;

    private String description;

    private LocalDate creationDate;

    private Long personId;

    private String tags;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getPerson() {
        return personId;
    }

    public void setPerson(Long person) {
        this.personId = person;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = LocalDate.parse(creationDate);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}