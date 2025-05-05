package com.example.demo.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

        @Query(value = "select tag_name\n" +
                        "from posttag\n" +
                        "left join tag on tag.tage_id=posttag.tag_id\n" +
                        "where post_id=:postId", nativeQuery = true)
        List<Object[]> getTagsByPost(@Param("postId") Long postId);

        @Query(value="select tag_name\n" +
                "from CHANNELTAG ch\n" +
                "left join tag on tag.tage_id=ch.tag_id\n" +
                "where channel_id=:channelId", nativeQuery = true)
        List<Object[]> getTagsByChannel(@Param("channelId") Long channelId);

        @Query(value = "select t.tag_name, count(comment_id) as cuenta\n" +
                        "from posttag pt\n" +
                        "right join tag t on pt.tag_id = t.tage_id\n" +
                        "left join post p on pt.post_id = p.post_id\n" +
                        "left join post_comment pc on pc.post_id = p.post_id\n" +
                        "group by t.tag_name\n" +
                        "order by cuenta desc\n" +
                        "fetch first 10 rows only", nativeQuery = true)
        List<Object[]> getTagByPopularity();

        @Query(value="select tag_name, count(material_id) as cuenta\n" +
                "from channeltag cht\n" +
                "right join tag t on cht.tag_id=t.tage_id\n" +
                "left join material on material.material_id=cht.channel_id\n" +
                "group by tag_name\n" +
                "order by cuenta desc\n" +
                "fetch first 10 rows only", nativeQuery = true)
        List<Object[]> getChannelTagPopularity();

        @Query(value = "select t.tag_name, count(comment_id) as cuenta\n" +
                        "from posttag pt\n" +
                        "right join tag t on pt.tag_id = t.tage_id\n" +
                        "left join post_comment pc on pc.post_id = pt.post_id\n" +
                        "where pt.post_id=:postId\n" +
                        "group by t.tag_name\n" +
                        "order by cuenta desc", nativeQuery = true)
        List<Object[]> tagTrendingByPost(@Param("postId") Long postId);

        @Query(value = "SELECT tag_name\n" +
                        "FROM tag\n" +
                        "WHERE tag_name like %:query%", nativeQuery = true)
        List<String> searchSimilars(@Param("query") String query);
}
