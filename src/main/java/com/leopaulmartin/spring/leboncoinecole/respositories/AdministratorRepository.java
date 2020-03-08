package com.leopaulmartin.spring.leboncoinecole.respositories;

import com.leopaulmartin.spring.leboncoinecole.models.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
}
