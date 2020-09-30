package com.leopaulmartin.spring.leboncoinecole.web.controllers;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/my-searches")
public class MySearchesController {
    public static final String REDIRECT = "redirect:/";
    private static final Logger logger = LoggerFactory.getLogger(MySearchesController.class);

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("allCategories")
    public List<Category> populateCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping
    public String showMySearches() {
        return "student/my-searches";
    }

    //TODO: add mySearch
    @PostMapping("/add")
    public String handleAddMySearchRequest() {
        logger.debug("handleAddMySearchRequest");
        return showMySearches();
    }
    //TODO: delete mySearch
    @PostMapping("/delete/{id}")
    public String handleDeleteMySearchRequest() {
        logger.debug("handleDeleteMySearchRequest");
        return showMySearches();
    }
    //TODO: delete all mySearch
    @PostMapping("/clear")
    public String handleClearMySearchesRequest() {
        logger.debug("handleClearMySearchesRequest");
        return showMySearches();
    }
}
