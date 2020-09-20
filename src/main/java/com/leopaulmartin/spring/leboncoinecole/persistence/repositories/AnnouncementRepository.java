package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByIsAnnouncement(boolean isAnnouncement);
    List<Announcement> findByCategory(Category category);

    @Query(value = "SELECT a " +
			"FROM announcements a " +
//			"INNER JOIN categories c " +
//            "ON a.categoryId =  c.categoryId " +
//            "INNER JOIN school sch " +
//            "ON a.schoolId = sch.schoolId " +
			"WHERE a.title = :keywordsInput AND " +
            "a.isAnnouncement = :isAnnouncement"// AND " +
//            "c.categoryId = :categoryId AND " +
//            "c.schoolId = :schoolId"
    )
    List<Announcement> getAnnouncementsFromSearchForm(
            @Param("isAnnouncement") String type,
//            @Param("categoryId") Long categoryId,
            @Param("keywordsInput") String keywordsInput
//            @Param("schoolId") Long schoolId
    );
}