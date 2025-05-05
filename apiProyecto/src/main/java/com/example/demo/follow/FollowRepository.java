package com.example.demo.follow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query(value = "select person_id, user_name, image_link, post_amount\n"
            + //
            "from FOLLOW\n" + //
            "join person on person.PERSON_ID=FOLLOW.FOLLOWED_ID\n" + //
            "where FOLLOW.FOLLOWER_ID=:followerId", nativeQuery = true)
    List<Object[]> getFollowingUsers(@Param("followerId") Long followerId);

    @Query(value = "select followers_count(:personId) from dual", nativeQuery = true)
    Long getTotalFollowers(@Param("personId") Long id);

    @Query(value = "select followed_count(:personId) from dual", nativeQuery = true)
    Long getTotalFollowing(@Param("personId") Long id);

    @Query(value = "select count(*) from follow f where f.follower_id = :userId and f.followed_id = :personId",
            nativeQuery = true)
    Long isFollowing(@Param("personId") Long personId, @Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from follow f where f.follower_id = :userId and f.followed_id = :personId",
            nativeQuery = true)
    void deleteFollow(@Param("personId") Long personId, @Param("userId") Long userId);

}
