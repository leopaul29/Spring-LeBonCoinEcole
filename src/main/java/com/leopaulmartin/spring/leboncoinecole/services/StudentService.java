package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Student;
import org.springframework.stereotype.Component;

@Component
public interface StudentService {
	Student getStudentById(Long id);

//	int getAnnouncementCount(Student student);

	Student createStudent(Student student);

	Student updateStudent(Long id, Student student);

	void deleteStudentById(Long id);

	boolean isStudentValid(Student student);
}
