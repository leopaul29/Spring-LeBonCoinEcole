package com.leopaulmartin.spring.leboncoinecole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.leopaulmartin.spring.leboncoinecole.persistence.repositories")
@EntityScan("com.leopaulmartin.spring.leboncoinecole.persistence.entities")
//This single annotation is equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan.
@SpringBootApplication
public class LeboncoinEcoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeboncoinEcoleApplication.class, args);
	}

}
