package com.example.demo.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/get")
    public List<Comment> getComments() {return commentService.getAllComments();}

    @PostMapping("/create")
    public ResponseEntity<String> createComment(@RequestBody Comment comment) {
        try{
            commentService.insertComment(comment);
            return ResponseEntity.status(HttpStatus.OK).body("Comment created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating comment."+e.getMessage());
        }
    }

    @GetMapping("/getByPost/{id}")
    private ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPostID(id));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
