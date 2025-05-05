package com.example.demo.tag;

import com.example.demo.person.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/get")
    public List<Tag> getTags() {
        return tagService.getAllTag();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTag(@RequestBody Tag tag) {
        try {
            tagService.createTag(tag);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating the tag");
        }
    }

    @GetMapping("/getTrending")
    public ResponseEntity<List<TagPopularityDTO>> getTrendingTags() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tagService.getPopularTags());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<String>> searchSimilars(@PathVariable("query") String query) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tagService.searchSimilars(query));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/trendingByPost/{id}")
    public ResponseEntity<List<TagPopularityDTO>> trendingByPost(@PathVariable("id") Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tagService.tagTrendingPost(id));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/trendingTagsChannel")
    public ResponseEntity<List<TagPopularityDTO>> getTagsChannels(){
        try{
            List<TagPopularityDTO> tags = tagService.getChannelTagPopularity();
            return ResponseEntity.status(HttpStatus.OK).body(tags);
        }catch (Exception e) {
            System.out.println("error: "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
