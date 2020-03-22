package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface AddressRepository extends JpaRepository<Address, Long> {

	@Query("SELECT a FROM addresses a WHERE LOWER(a.city) = LOWER(:city)")
	List<Address> findByCity(@Param("city") String city);
}
