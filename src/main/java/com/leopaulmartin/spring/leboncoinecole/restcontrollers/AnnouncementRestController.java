package com.leopaulmartin.spring.leboncoinecole.restcontrollers;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Category rest API
 * link about REST API validation: https://howtodoinjava.com/spring-boot2/spring-rest-request-validation/
 */

@RestController
@RequestMapping(path = "/api/v1/announcements")

/*
If there’s no use case where the controller has to be injected or manipulated through a direct reference, then I prefer not to declare it as public.
 */
class AnnouncementRestController {
	private static final Logger logger = LoggerFactory.getLogger(AnnouncementRestController.class);

	private static final String APPLICATION_JSON_AND_HATEOAS = "application/hal+json";

	@Autowired
	private AnnouncementService announcementService;

	@GetMapping(produces = APPLICATION_JSON_AND_HATEOAS)
	public ResponseEntity<List<Announcement>> getAllAnnouncements() {
		List<Announcement> announcements = announcementService.getAllAnnouncements();

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "getAllCategories")
				.body(announcements);
	}

//	@GetMapping(path = "/{id}/announcements", produces = APPLICATION_JSON_AND_HATEOAS)
//	public Resources<Announcement> getAnnouncementForCategory(@PathVariable("id") final Long categoryId) {
//		List<Announcement> announcements = announcementService.getAnnouncementForCategory(categoryId);
//		for (final Announcement announcement : announcements) {
//			Link selfLink = linkTo(methodOn(AnnouncementRestController.class).getCategoryById(categoryId)).withSelfRel();
//			announcement.add(selfLink);
//		}
//
//		Link link = linkTo(methodOn(AnnouncementRestController.class).getAnnouncementForCategory(categoryId)).withSelfRel();
//		Resources<Announcement> result = new Resources<Announcement>(announcements, link);
//		return result;
//	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Announcement> getCategoryById(@PathVariable("id") Long id) {
		Announcement announcement = announcementService.getAnnouncementById(id);

		return ResponseEntity.ok()
				.header("Responded", "CategoryRestController")
				.header("Method", "getCategoryById")
				.body(announcement);
	}

}
