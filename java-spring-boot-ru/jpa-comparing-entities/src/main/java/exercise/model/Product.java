package exercise.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// BEGIN
@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"title", "price"})
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private String title;
    private double price;
}
// END
