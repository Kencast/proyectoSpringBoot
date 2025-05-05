package com.example.demo.channel;

import com.example.demo.post.Post;
import com.example.demo.post.PostInfoDTO;
import com.example.demo.post.PostTag;
import com.example.demo.post.PostViewDTO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @GetMapping("/get")
    public List<Channel> getChannels() {return channelService.findAllChannel();}

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody PostTag channelTag) {
        try{
            Channel channel = new Channel(channelTag.getId(),channelTag.getTitle(),channelTag.getDescription(),
                    channelTag.getPersonId());
            channelService.insertChannel(channel,channelTag.getTags());
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating the channel."+e.getMessage());
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        try{
            channelService.deleteChannel(id);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting the channel."+e.getMessage());
        }
    }
    
    @GetMapping("/getChannelsInfo")
    public  ResponseEntity<List<PostInfoDTO>> getChannelsInfo() {
        try{
          List<PostInfoDTO> channels =  channelService.getChannelsInfo();
          return ResponseEntity.status(HttpStatus.OK).body(channels);
        }catch (Exception e){
            System.out.println("error"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getChannelsInfo/{id}")
    public ResponseEntity<List<PostInfoDTO>> getChannelsInfo(@PathVariable Long id) {
        try{
            List<PostInfoDTO> channels = channelService.channelsInfoById(id);
            return ResponseEntity.status(HttpStatus.OK).body(channels);
        }
        catch (Exception e){
            System.out.println("error: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getChannelView/{id}")
    public ResponseEntity<PostViewDTO> getChannelView(@PathVariable("id") Long channelId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(channelService.getChannelView(channelId));
        }catch (Exception e){
            System.out.println("error: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
