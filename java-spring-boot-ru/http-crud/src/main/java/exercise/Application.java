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

    // «База данных» в памяти
    private final List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // ------------------------------------------------------------
    // CRUD для Post
    // ------------------------------------------------------------

    // GET /posts?page=N&limit=M
    @GetMapping("/posts")
    public List<Post> index(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit) {

        int from = Math.max((page - 1) * limit, 0);
        return posts.stream()
                    .skip(from)
                    .limit(limit)
                    .toList();
    }

    // GET /posts/{id}
    @GetMapping("/posts/{id}")
    public Post show(@PathVariable String id) {
        return posts.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElse(null);           // ↳ в тестах ожидается null, а не 404
    }

    // POST /posts
    @PostMapping("/posts")
    public Post create(@RequestBody Post data) {
        // Генерируем id, если не передали
        if (data.getId() == null || data.getId().isBlank()) {
            data.setId(UUID.randomUUID().toString());
        }
        posts.add(data);
        return data;
    }

    // PUT /posts/{id}
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
        // В учебных заданиях принято отдавать то,
        // что прислал клиент (data)
        return data;
    }

    // DELETE /posts/{id}
    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}