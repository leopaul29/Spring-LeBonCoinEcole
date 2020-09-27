package com.leopaulmartin.spring.leboncoinecole.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Use-cases:
 * - check messages received for an announce posted
 * - check messages send for an announce
 */
@Controller
@RequestMapping("/messages")
public class MessagesController {
    public static final String REDIRECT = "redirect:/";
    private static final Logger logger = LoggerFactory.getLogger(MessagesController.class);

    @GetMapping
    public String messages() {
        return "student/messages";
    }
}
