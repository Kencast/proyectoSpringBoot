package com.example.demo.channel;

import com.example.demo.post.PostInfoDTO;
import com.example.demo.post.PostViewDTO;
import com.example.demo.tag.TagService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private EntityManager entityManager;

    public List<Channel> findAllChannel() {return channelRepository.findAll();}

    public void insertChannel(Channel channel, String tags) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("createChannel");
        System.out.println("Nombre es "+channel.getChannelName());
        query.setParameter("nameC", channel.getChannelName());
        query.setParameter("descriptionC", channel.getDescription());
        query.setParameter("personID", channel.getPerson());
        query.setParameter("tags", tags);
        query.execute();
    }
    
    public void deleteChannel(Long channelId) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("deleteChannel");
        query.setParameter("d_chanel_id", channelId);
        query.execute();
    }

    public List<PostInfoDTO> getChannelsInfo(){
        return channelsInfoDTOS(channelRepository.getChannelsInfo());
    }

    public List<PostInfoDTO> channelsInfoById(Long personId){
        return channelsInfoDTOS(channelRepository.getChannelsById(personId));
    }

    private List<PostInfoDTO> channelsInfoDTOS(List<Object[]> objs) {
        List<PostInfoDTO> posts = new ArrayList<>();
        for(Object[] obj : objs){
            PostInfoDTO post=new PostInfoDTO(
                    ((BigDecimal) obj[0]).longValue(),
                    (String) obj[1],
                    ((BigDecimal) obj[2]).longValue(),
                    (String) obj[3],
                    (String) obj[4],
                    ((BigDecimal) obj[5]).longValue()
            );
            post.setTags(tagService.getTagsByChannel(post.getPostId()));
            posts.add(post);
        }
        return posts;
    }

    public PostViewDTO getChannelView(Long id){
        List<Object[]> objs=channelRepository.getChannelView(id);
        List<PostViewDTO> posts=new ArrayList<>();
        for(Object[] obj:objs){
            PostViewDTO post=new PostViewDTO(
                    ((BigDecimal) obj[0]).longValue(),
                    (String) obj[1],
                    (String) obj[2],
                    (String) obj[3],
                    ((BigDecimal) obj[4]).longValue(),
                    (String) obj[5],
                    (String) obj[6]
            );
            posts.add(post);
        }
        return posts.get(0);
    }
}
