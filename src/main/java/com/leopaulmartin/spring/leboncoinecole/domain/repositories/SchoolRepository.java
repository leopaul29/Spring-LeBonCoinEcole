package com.leopaulmartin.spring.leboncoinecole.domain.repositories;

import com.leopaulmartin.spring.leboncoinecole.domain.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
}
