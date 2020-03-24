package com.leopaulmartin.spring.leboncoinecole.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Configuration
public class ThymeleafConfig {

	public SpringTemplateEngine thymeleafLayoutDialect() {
		// SpringTemplateEngine automatically applies SpringStandardDialect and
		// enables Spring's own MessageSource message resolution mechanisms.
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addDialect(new LayoutDialect());
		// Enabling the SpringEL compiler with Spring 4.2.4 or newer can
		// speed up execution in most scenarios, but might be incompatible
		// with specific cases when expressions in one template are reused
		// across different data types, so this flag is "false" by default
		// for safer backwards compatibility.
//		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}
}
