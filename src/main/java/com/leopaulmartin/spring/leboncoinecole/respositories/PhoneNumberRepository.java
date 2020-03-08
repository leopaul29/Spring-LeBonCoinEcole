package com.leopaulmartin.spring.leboncoinecole.respositories;

import com.leopaulmartin.spring.leboncoinecole.models.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
}
