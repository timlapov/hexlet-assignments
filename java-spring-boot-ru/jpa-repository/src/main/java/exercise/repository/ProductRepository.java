package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

import org.springframework.data.domain.Sort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByPriceBetweenOrderByPriceAsc(int priceAfter, int priceBefore);
    List<Product> findAllByPriceGreaterThanEqualOrderByPriceAsc(int price);
    List<Product> findAllByPriceLessThanEqualOrderByPriceAsc(int price);
}
