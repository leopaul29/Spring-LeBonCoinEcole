package com.leopaulmartin.spring.leboncoinecole.web.controllers;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Announcement;
import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.services.AnnouncementService;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/my-announces")
public class MyAnnouncesController {
    public static final String REDIRECT = "redirect:/";
    private static final Logger logger = LoggerFactory.getLogger(MyAnnouncesController.class);

    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("allAnnouncements")
    public List<Announcement> populateAnnouncements() {
        return announcementService.getAllAnnouncements();
    }
//    @ModelAttribute("allCategories")
//    public List<Category> populateCategories() {
//        return categoryService.getAllCategories();
//    }

    @GetMapping
    public String showAnnounces() {
        return "student/my-announces";
    }

    @GetMapping(path = {"/create", "/edit/{id}"})
    public String showEditAnnouncementById(Model model,
                                           @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException {
        if (id.isPresent()) {
            Announcement announcement = announcementService.getAnnouncementById(id.get());
            model.addAttribute("announcement", announcement);
        } else {
            model.addAttribute("announcement", new Announcement());
        }
        model.addAttribute("allCategories", categoryService.getAllCategories());

        return "student/add-edit-announcement";
    }
}
