package exercise.controller.users;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
public class PostsController {
    @GetMapping("/api/users/{userId}/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getPosts(@PathVariable int userId) {
        return Data.getPosts().stream()
            .filter(post -> post.getUserId() == userId)
            .toList();
    }

    @PostMapping("/api/users/{userId}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@PathVariable int userId, @RequestBody Post post) {
        post.setUserId(userId);
        Data.getPosts().add(post);
        return post;
    }
}
// END
