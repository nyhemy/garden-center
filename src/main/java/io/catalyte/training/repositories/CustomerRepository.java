package io.catalyte.training.repositories;

import io.catalyte.training.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * customer repository
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
