package com.leopaulmartin.spring.leboncoinecole.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class CustomErrorController {// implements ErrorController {
	private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

//	@RequestMapping("/error")
//	public String handleError(HttpServletRequest request) {
//		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//		if (status != null) {
//			int statusCode = Integer.valueOf(status.toString());
//
//			if (statusCode == HttpStatus.NOT_FOUND.value()) {
//				logger.error("error/error-404");
//				return "error/error-404";
//			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//				logger.error("error/error-404");
//				return "error.error-500";
//			}
//		}
//		logger.error("error/my-error-page");
//		return "error/my-error-page";
//	}
//
//	@Override
//	public String getErrorPath() {
//		return "/error";
//	}
}
