package com.example.demo.channel;

import com.example.demo.person.Person;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
//nameC varchar2, descriptionC varchar2, dateC varchar2, personID int,
//                                          tags varchar2
@Entity
@NamedStoredProcedureQuery(
        name = "createChannel",
        procedureName = "createChannel",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "nameC", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "descriptionC", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "personID", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "tags", type = String.class),
        }
)
@NamedStoredProcedureQuery(
        name="deleteChannel",
        procedureName="delete_channel_by_id",
        parameters = {
                @StoredProcedureParameter(mode=ParameterMode.IN, name="d_chanel_id", type=Long.class),
        }
)
public class Channel {
    @Id
    @Column(name = "CHANNEL_ID", nullable = false)
    private Long id;

    @Column(name = "CHANNEL_NAME", length = 200)
    private String channelName;

    @Column(name = "DESCRIPTION", length = 10000)
    private String description;

    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;

    @Column(name = "PERSON_ID")
    private Long personId;

    public Channel(Long id, String channelName, String description, Long personId) {
        this.id = id;
        this.channelName = channelName;
        this.description = description;
        this.personId = personId;
    }

    public Channel() {

    }

    public Long getPerson() {
        return personId;
    }

    public void setPerson(Long person) {
        this.personId = person;
    }

    public String getCreationDate() {
        return creationDate.getDayOfMonth()+"-"+creationDate.getMonthValue()+"-"+creationDate.getYear();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
