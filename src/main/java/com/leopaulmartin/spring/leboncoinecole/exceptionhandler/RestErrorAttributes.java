package com.leopaulmartin.spring.leboncoinecole.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class RestErrorAttributes extends DefaultErrorAttributes {
	private static final Logger logger = LoggerFactory.getLogger(RestErrorAttributes.class);

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
		Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
		errorAttributes.put("locale", webRequest.getLocale().toString());
		errorAttributes.remove("error");
		logger.info(errorAttributes.toString());
		return errorAttributes;
	}
}
