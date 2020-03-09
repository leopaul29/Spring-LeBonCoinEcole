package com.leopaulmartin.spring.leboncoinecole.domain.repositories;

import com.leopaulmartin.spring.leboncoinecole.domain.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
}
