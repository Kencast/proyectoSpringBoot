package com.example.demo.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value="select post.post_id, post.title, post.person_id, user_name, image_link, count(comment_id)\n" +
            "from post\n" +
            "inner join person on person.person_id=post.person_id\n" +
            "left join post_comment on post_comment.post_id=post.post_id\n" +
            "group by post.post_id, post.title, post.person_id, user_name, image_link", nativeQuery=true)
    List<Object[]> getPostsInfo();

    @Query(value = "select post.post_id, post.title, post.person_id, user_name, image_link, count(comment_id)\n" +
            "from post\n" +
            "inner join person on person.person_id=post.person_id\n" +
            "left join post_comment on post_comment.post_id=post.post_id\n" +
            "where post.person_id=:id\n" +
            "group by post.post_id, post.title, post.person_id, user_name, image_link",nativeQuery = true)
    List<Object[]> getPostsInfoById(@Param("id") Long id);

    @Query(value="select post.post_id, post.title, post.description, to_char(post.publication_date, 'DD-MM-YYYY'), post.person_id, user_name, image_link\n" +
            "from post\n" +
            "inner join person on person.person_id=post.person_id\n" +
            "where post_id=:postId", nativeQuery=true)
    List<Object[]> getPostView(@Param("postId") Long postId);
}
