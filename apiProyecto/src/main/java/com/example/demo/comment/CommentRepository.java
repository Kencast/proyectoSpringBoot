package com.example.demo.comment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value="select a.comment_id, a.description, a.person_id, user_name, image_link\n" +
            "from post_comment a\n" +
            "join person on person.person_id=a.person_id\n" +
            "where post_id=:postId", nativeQuery = true)
    public List<Object[]> getCommentByPostId(@Param("postId") Long postId);

}

//postID int,personID int, new_description varchar2