package com.example.demo.follow;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.util.Objects;
import java.io.Serializable;

@Embeddable
public class FollowId implements Serializable {
    private Long followerId;
    private Long followedId;

    public FollowId(Long followerId, Long followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
    }

    public FollowId() {
        this.followerId = null;
        this.followedId = null;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Long followedId) {
        this.followedId = followedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FollowId entity = (FollowId) o;
        return Objects.equals(this.followerId, entity.followerId) &&
                Objects.equals(this.followedId, entity.followedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followedId);
    }
}