package com.leopaulmartin.spring.leboncoinecole.domain.repositories;

import com.leopaulmartin.spring.leboncoinecole.domain.entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
}
