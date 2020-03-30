package com.leopaulmartin.spring.leboncoinecole.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
	private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				logger.error("error/error-404");
				return "error/error-404";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				logger.error("error/access-denied");
				return "error/access-denied";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				logger.error("error/error-404");
				return "error/error-500";
			}
		}
		logger.error("error/my-error-page");
		return "error/my-error-page";
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String exception(final Throwable throwable, final Model model) {
		logger.error("Exception during execution of SpringSecurity application", throwable);
		String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
		model.addAttribute("errorMessage", errorMessage);
		return "error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
