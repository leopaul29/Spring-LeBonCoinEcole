package com.leopaulmartin.spring.leboncoinecole.respositories;

import com.leopaulmartin.spring.leboncoinecole.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
