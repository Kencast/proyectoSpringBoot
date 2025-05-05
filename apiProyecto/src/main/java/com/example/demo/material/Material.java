package com.example.demo.material;

import jakarta.persistence.*;

@Entity

//titleM varchar2, descriptionM varchar2, channelID int
@NamedStoredProcedureQuery(
        name = "createMaterial",
        procedureName = "createMaterial",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "titleM", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "descriptionM", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "channelID", type = Long.class)
        }
)
public class Material {

    @Id
    @Column(name = "MATERIAL_ID", nullable = false)
    private Long id;

    @Column(name = "TITLE", length = 500)
    private String title;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CHANNEL_ID")
    private Long channel_id;

    public Long getChannel() {
        return channel_id;
    }

    public void setChannel(Long channel) {
        this.channel_id = channel;
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
