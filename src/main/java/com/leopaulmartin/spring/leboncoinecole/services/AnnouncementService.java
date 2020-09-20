package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.web.dto.SearchForm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AnnouncementService {
	List<Announcement> getAllAnnouncements();

	Announcement getAnnouncementById(Long id);

	List<Announcement> getAnnouncementsByType(String type);
	List<Announcement> getAnnouncementsByCategory(Long categoryId);

	Announcement createAnnouncement(Announcement announcement);

	Announcement createOrUpdateAnnouncement(Announcement announcement);

	Announcement updateAnnouncement(Long id, Announcement announcement);

	void deleteAnnouncementById(Long id);

	boolean isAnnouncementValid(Announcement announcement);

	List<Announcement> getAnnouncementsFromSearchForm(SearchForm searchForm);
}
