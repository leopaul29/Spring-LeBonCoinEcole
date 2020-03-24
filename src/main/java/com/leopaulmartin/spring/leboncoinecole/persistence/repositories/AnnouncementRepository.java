package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

//	@Query(value = "SELECT a " +
//			"FROM announcements a " +
//			"INNER JOIN categories c " +
//			"WHERE a.categoryId = :categoryId")
//	List<Announcement> findAnnouncementsByCategory(@Param("categoryId") Long categoryId);
////	@Query("SELECT count(*) " +
////			"FROM announcements a " +
////			"INNER JOIN categories c " +
////			"WHERE a.categoryId = c.categoryId AND a.categoryId = :categoryId")
////	int countAnnouncementsByCategory(@Param("categoryId") Long categoryId);
//
//	@Query("SELECT a " +
//			"FROM announcements a " +
//			"INNER JOIN categories c " +
//			"WHERE c.categoryId = :categoryId AND a.isAnnouncement = false")
//	List<Announcement> findSearchesByCategory(@Param("categoryId") Long categoryId);
//
//	@Query("SELECT a " +
//			"FROM announcements a " +
//			"INNER JOIN categories c " +
//			"WHERE c.categoryId = :categoryId AND a.isAnnouncement = true")
//	List<Announcement> findSalesByCategory(@Param("categoryId") Long categoryId);
}


//@Query("SELECT a " +
//		"FROM announcements a " +
//		"INNER JOIN a.categories c " +
//		"WHERE c.categoryId = :categoryId AND a.isAnnouncement = true")

//"SELECT s " +
//		"FROM schools s " +
//		"INNER JOIN addresses a " +
//		"ON s.address =  a.addressId " +
//		"WHERE LOWER(a.city) = LOWER(:city)"
