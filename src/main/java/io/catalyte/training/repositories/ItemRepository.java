package io.catalyte.training.repositories;

import io.catalyte.training.entities.Item;
import io.catalyte.training.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
