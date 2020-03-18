package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

	@Query("SELECT s " +
			"FROM schools s " +
			"INNER JOIN addresses a " +
			"ON s.address =  a.addressId " +
			"WHERE LOWER(a.city) = LOWER(:city)")
	List<School> findByAddressCity(@Param("city") String city);
}
