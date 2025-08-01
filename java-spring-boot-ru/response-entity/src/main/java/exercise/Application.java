package exercise;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;
import lombok.Setter;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    @Setter
    private static  List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> index(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit) {
        List<Post> response = Application.posts.stream().toList();

        return  ResponseEntity
                .ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(response);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> show(@PathVariable String id) {
        Optional<Post> response = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return ResponseEntity.of(response);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> create(@RequestBody Post data) {
        // Генерируем id, если не передали
        if (data.getId() == null || data.getId().isBlank()) {
            data.setId(UUID.randomUUID().toString());
        }
        posts.add(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> update(@PathVariable String id,
                                       @RequestBody Post data) {
        Optional<Post> postOptional = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setTitle(data.getTitle());
            post.setBody(data.getBody());
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
