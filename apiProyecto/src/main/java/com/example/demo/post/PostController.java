package com.example.demo.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/get")
    public List<Post> getAllPost() {
        return postService.findAllPost();
    }

    @GetMapping("/getPostInfo")
    public ResponseEntity<List<PostInfoDTO>> getPostInfo() {
        try{
            List<PostInfoDTO> posts=postService.getPostsInfo();
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody PostTag postTag) {
        try{
            Post post = new Post(postTag.getId(),postTag.getTitle(),postTag.getDescription(),
                    postTag.getPersonId());
            System.out.println(postTag.getTags());
            postService.insertPost(post,postTag.getTags());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Post created");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating the post."+e.getMessage());
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        try{
            System.out.println(id);
            postService.deletePost(id);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("bad delete");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody PostViewDTO postViewDTO){
        try{
            postService.updatePost(postViewDTO);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("bad update: "+e.getMessage());
        }
    }

    @GetMapping("postsById/{id}")
    public ResponseEntity<List<PostInfoDTO>> getPostsById(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsInfoById(id));
        }catch (Exception e){
            System.out.println("error: "+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("getPostView/{id}")
    public ResponseEntity<PostViewDTO> getPostView(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsView(id));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
