package exercise;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {

    private final List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/posts")
    public List<Post> index(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit) {

        int from = Math.max((page - 1) * limit, 0);
        return posts.stream()
                    .skip(from)
                    .limit(limit)
                    .toList();
    }

    @GetMapping("/posts/{id}")
    public Post show(@PathVariable String id) {
        return posts.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElse(null);
    }

    @PostMapping("/posts")
    public Post create(@RequestBody Post data) {
        // Генерируем id, если не передали
        if (data.getId() == null || data.getId().isBlank()) {
            data.setId(UUID.randomUUID().toString());
        }
        posts.add(data);
        return data;
    }

    @PutMapping("/posts/{id}")
    public Post update(@PathVariable String id,
                       @RequestBody Post data) {

        posts.stream()
             .filter(p -> p.getId().equals(id))
             .findFirst()
             .ifPresent(p -> {
                 p.setTitle(data.getTitle());
                 p.setBody(data.getBody());
             });
        return data;
    }

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}