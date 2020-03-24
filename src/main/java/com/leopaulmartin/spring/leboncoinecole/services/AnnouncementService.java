package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AnnouncementService {
	List<Announcement> getAllAnnouncements();

	Announcement getAnnouncementById(Long id);

//	List<Announcement> getAnnouncementForCategory(Long categoryId);
//	int countAnnouncementForCategory(Long categoryId);

//	List<Announcement> getSearchesByCategory(Long categoryId);

//	List<Announcement> getSalesByCategory(Long categoryId);

	Announcement createAnnouncement(Announcement announcement);

	Announcement createOrUpdateAnnouncement(Announcement announcement);

	Announcement updateAnnouncement(Long id, Announcement announcement);

	void deleteAnnouncementById(Long id);

	boolean isAnnouncementValid(Announcement announcement);
}
