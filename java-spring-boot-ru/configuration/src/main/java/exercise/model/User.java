package exercise.model;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    @Value("${users.admins}")
    private String email;
}
