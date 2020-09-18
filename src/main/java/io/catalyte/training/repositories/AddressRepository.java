package io.catalyte.training.repositories;

import io.catalyte.training.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * address repository
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
