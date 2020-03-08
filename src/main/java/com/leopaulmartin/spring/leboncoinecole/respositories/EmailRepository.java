package com.leopaulmartin.spring.leboncoinecole.respositories;

import com.leopaulmartin.spring.leboncoinecole.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
