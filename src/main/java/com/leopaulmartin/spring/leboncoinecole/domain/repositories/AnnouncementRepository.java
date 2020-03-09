package com.leopaulmartin.spring.leboncoinecole.domain.repositories;

import com.leopaulmartin.spring.leboncoinecole.domain.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
