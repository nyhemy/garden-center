package io.catalyte.training.repositories;

import io.catalyte.training.entities.Customer;
import io.catalyte.training.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
