package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

	@Query("SELECT a FROM announcements a INNER JOIN a.categories c WHERE c.label = :label")
	List<Announcement> findAnnouncementsByCategory(@Param("label") String label);
}
