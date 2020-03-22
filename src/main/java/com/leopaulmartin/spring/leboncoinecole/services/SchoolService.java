package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.School;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SchoolService {
	List<School> getAllSchools();

	School getSchoolById(Long id);

	List<Student> getStudentsForSchool(Long schoolId);

	School createSchool(School school);

	School updateSchool(Long id, School school);

	void deleteSchoolById(Long id);

	boolean isSchoolValid(School school);
}
