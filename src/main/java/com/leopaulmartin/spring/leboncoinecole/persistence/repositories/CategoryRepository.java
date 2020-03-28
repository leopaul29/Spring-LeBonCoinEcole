package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
//Typically, you want the readOnly flag to be set to true, as most of the query methods only read data. In contrast to that, deleteInactiveUsers() makes use of the @Modifying annotation and overrides the transaction configuration. Thus, the method runs with the readOnly flag set to false.
//https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#transactional-query-methods
public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("SELECT c FROM categories c WHERE LOWER(c.label) = LOWER(:label)")
	Category findByLabel(@Param("label") String label);

	//	@Query("SELECT c FROM categories c " +
//			"INNER JOIN announcements a " +
//			"WHERE c.categoryId = a.category " +
//			"ORDER BY COUNT (a.announcementId) DESC")
//	@Query("SELECT c.label " +
//			"(SELECT COUNT(*) " +
//			"FROM announcements a " +
//			"WHERE a.category = c.categoryId) " +
//			"AS announcements_number " +
//			"FROM categories c")
//	List<Category> findAllSortedByAnnounce();

//	@Modifying
////	The @Modifying annotation is only relevant in combination with the @Query annotation. Derived query methods or custom methods do not require this Annotation.
////	https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#jpa.modifying-queries
//	@Transactional
////	@Transactional alone will set readOnly = false
////	if readonly, then no update on database
////	https://netsurfingzone.com/spring/transactional-readonly-true-example-in-spring-boot
//	@Query("DELETE from categories")
//	void deleteAllCategories();
}
