package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordAlreadyExistException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordIdMismatchException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AnnouncementService {
	List<Announcement> getAllAnnouncements();

	Announcement getAnnouncementById(Long id) throws RecordNotFoundException;

	List<Announcement> getAnnouncementForCategory(Long categoryId) throws RecordNotFoundException;

	Announcement createAnnouncement(Announcement announcement) throws RecordAlreadyExistException;

	Announcement updateAnnouncement(Long id, Announcement announcement) throws RecordIdMismatchException, RecordAlreadyExistException, RecordNotFoundException;

	void deleteAnnouncementById(Long id) throws RecordNotFoundException;

	void deleteAllAnnouncements();
}
