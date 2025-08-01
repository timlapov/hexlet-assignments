package exercise.controller.users;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

@RestController
public class PostsController {

    @GetMapping("/api/users/{userId}/posts")
    public ResponseEntity<List<Post>> getPosts(@PathVariable int userId) {
        List<Post> userPosts = Data.getPosts().stream()
                .filter(post -> post.getUserId() == userId)
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(userPosts);
    }

    @PostMapping("/api/users/{userId}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int userId, @RequestBody Post post) {
        post.setUserId(userId);
        Data.getPosts().add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
}