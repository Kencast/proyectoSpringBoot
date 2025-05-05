package com.example.demo.follow;

import com.example.demo.person.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @GetMapping("/validate/{userId}/{personId}")
    public ResponseEntity<Boolean> validateFollow(@PathVariable Long userId, @PathVariable Long personId) {
        try{
            Long isFollow=followService.isFollowing(personId,userId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(isFollow == 1);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get")
    public List<Follow> getAllFollow() {
        return followService.findAllFollow();
    }

    @GetMapping("/following/{id}")
    public ResponseEntity<List<User>> getFollowingUsers(@PathVariable Long id) {
        try {
            System.out.println("Id es " + id);
            List<User> following = followService.getFollowingUsers(id);
            return ResponseEntity.status(HttpStatus.OK).body(following);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/create/{userId}/{personId}")
    public ResponseEntity<String> createFollow(@PathVariable Long userId, @PathVariable Long personId) {
        try {
            FollowId followId = new FollowId(userId,personId);
            Follow follow = new Follow();
            follow.setId(followId);
            followService.insertFollow(follow);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the follow. " + e.getMessage());
        }
    }

    @GetMapping("/delete/{userId}/{personId}")
    public ResponseEntity<String> deleteFollow(@PathVariable Long userId, @PathVariable Long personId){
        try{
            followService.deleteFollow(personId,userId);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the follow. " + e.getMessage());
        }
    }
}
