package io.catalyte.training.repositories;

import io.catalyte.training.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * order repository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
