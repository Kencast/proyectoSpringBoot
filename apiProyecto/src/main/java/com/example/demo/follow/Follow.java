package com.example.demo.follow;

import com.example.demo.person.Person;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

//follower_id int, followed_id int

@Entity
@NamedStoredProcedureQuery(
        name = "createFollow",
        procedureName = "createFollow",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "follower_id", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "followed_id", type = Long.class),
        }
)
public class Follow {

    @EmbeddedId
    private FollowId id;

    public FollowId getId() {
        return id;
    }

    public void setId(FollowId id) {
        this.id = id;
    }
}
