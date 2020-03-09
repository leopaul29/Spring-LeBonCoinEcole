package com.leopaulmartin.spring.leboncoinecole.domain.repositories;

import com.leopaulmartin.spring.leboncoinecole.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
