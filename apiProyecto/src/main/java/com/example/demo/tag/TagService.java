package com.example.demo.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }

    public void createTag(Tag tag) {
        tagRepository.save(tag);
    }

    public List<String> getTagsByPost(Long postId) {
        List<Object[]> objs = tagRepository.getTagsByPost(postId);
        List<String> tags = new ArrayList<>();
        for (Object[] obj : objs) {
            tags.add(String.valueOf(obj[0]));
        }
        return tags;
    }

    public List<String> getTagsByChannel(Long channelId) {
        List<Object[]> objs = tagRepository.getTagsByChannel(channelId);
        List<String> tags = new ArrayList<>();
        for (Object[] obj : objs) {
            tags.add(String.valueOf(obj[0]));
        }
        return tags;
    }

    public List<TagPopularityDTO> getPopularTags() {
        return convertPopularityDTO(tagRepository.getTagByPopularity());
    }

    public List<TagPopularityDTO> tagTrendingPost(Long postId) {
        return convertPopularityDTO(tagRepository.tagTrendingByPost(postId));
    }

    public List<TagPopularityDTO> getChannelTagPopularity() {
        return convertPopularityDTO(tagRepository.getChannelTagPopularity());
    }

    private List<TagPopularityDTO> convertPopularityDTO(List<Object[]> objs){
        List<TagPopularityDTO> tags = new ArrayList<>();
        for (Object[] obj : objs) {
            TagPopularityDTO tag = new TagPopularityDTO(
                    (String) obj[0],
                    ((BigDecimal) obj[1]).intValue());
            tags.add(tag);
        }
        return tags;
    }

    public List<String> searchSimilars(String query) {
        return tagRepository.searchSimilars(query);
    }
}
