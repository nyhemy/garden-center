package io.catalyte.training.repositories;

import io.catalyte.training.entities.Product;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * product repository
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
