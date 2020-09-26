package com.leopaulmartin.spring.leboncoinecole.web.controllers;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/my-announces")
public class MyAnnouncesController {
    public static final String REDIRECT = "redirect:/";
    private static final Logger logger = LoggerFactory.getLogger(MyAnnouncesController.class);

    @Autowired
    private AnnouncementService announcementService;

    @ModelAttribute("allAnnouncements")
    public List<Announcement> populateAnnouncements() {
        return announcementService.getAllAnnouncements();
    }

    @GetMapping
    public String showAnnounces() {
        return "student/my-announces";
    }
}
