package exercise.daytime;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.web.context.annotation.RequestScope;

public class Night implements Daytime {
    private String name = "night";

    public String getName() {
        return name;
    }

    // BEGIN
    @PostConstruct
    public void init() {
        System.out.println("BEAN SAYS: Daytime is " + this.name);
    }
    @PreDestroy
    public void destroy() {
        System.out.println("BEAN SAYS: Goodbye! " + this.name);
    }
    // END
}
