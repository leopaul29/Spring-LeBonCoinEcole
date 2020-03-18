package com.leopaulmartin.spring.leboncoinecole.persistence.repositories;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query("SELECT s FROM students s WHERE LOWER(s.username) = LOWER(:username)")
	Student findByUsername(@Param("username") String username);

	@Query("SELECT s FROM students s INNER JOIN s.phonenumbers phn WHERE phn.number = :phonenumber")
	Student findByPhonenumber(@Param("phonenumber") String phonenumber);
}
