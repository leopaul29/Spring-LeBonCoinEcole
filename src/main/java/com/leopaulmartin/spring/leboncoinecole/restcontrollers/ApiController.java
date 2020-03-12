package com.leopaulmartin.spring.leboncoinecole.restcontrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class ApiController {
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Value("${app.version}")
	private String appVersion;

	@GetMapping
	public Map<String, String> GetStatus() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("app-version", appVersion);
		return map;
	}
}
