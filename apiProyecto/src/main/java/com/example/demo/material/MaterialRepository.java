package com.example.demo.material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    @Query(value="select * from material m where m.channel_id=:channelId", nativeQuery = true)
    List<Material> getMaterialsByChannelId(@Param("channelId") Long channelId);

}

