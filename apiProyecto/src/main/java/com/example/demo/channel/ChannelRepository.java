package com.example.demo.channel;

import com.example.demo.post.PostInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Query(value ="select ch.CHANNEL_ID, channel_name, ch.person_id, user_name, image_link, count(material_id)\n" +
            "from CHANNEL ch\n" +
            "inner join person on person.person_id=ch.PERSON_ID\n" +
            "left join material on material.CHANNEL_ID=ch.CHANNEL_ID\n" +
            "group by ch.CHANNEL_ID, channel_name, ch.person_id, user_name, image_link", nativeQuery =true)
    List<Object[]> getChannelsInfo();

    @Query(value = "select ch.CHANNEL_ID, channel_name, ch.person_id, user_name, image_link, count(material_id)\n" +
            "from CHANNEL ch\n" +
            "inner join person on person.person_id=ch.PERSON_ID\n" +
            "left join material on material.CHANNEL_ID=ch.CHANNEL_ID\n" +
            "where ch.person_id=:personId\n" +
            "group by ch.CHANNEL_ID, channel_name, ch.person_id, user_name, image_link", nativeQuery = true)
    List<Object[]> getChannelsById(@Param("personId") Long personId);

    @Query(value="select channel_id, channel_name, description, to_char(creation_date, 'DD-MM-YYYY'), channel.person_id, user_name, image_link\n" +
            "from channel\n" +
            "inner join person on person.person_id=channel.person_id\n" +
            "where channel_id=:channelId", nativeQuery = true)
    List<Object[]> getChannelView(@Param("channelId") Long channelId);

}
