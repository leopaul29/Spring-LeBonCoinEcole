package com.leopaulmartin.spring.leboncoinecole.services;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.web.dto.AnnounceDto;
import com.leopaulmartin.spring.leboncoinecole.web.dto.SearchForm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AnnouncementService {
	List<Announcement> getAllAnnouncements();

	Announcement getAnnouncementById(Long id);

	List<Announcement> getAnnouncementsByType(String type);
	List<Announcement> getAnnouncementsByCategory(Long categoryId);
	List<Announcement> getAnnouncementsByKeywordInput(String keywordsInput);

	Announcement createOrUpdateAnnouncement(Announcement announcement);
	Announcement createOrUpdateAnnouncement(AnnounceDto announceDto);

	void deleteAnnouncementById(Long id);

	boolean isAnnouncementValid(Announcement announcement);

//	List<Announcement> getAnnouncementsFromSearchForm(SearchForm searchForm);
}
