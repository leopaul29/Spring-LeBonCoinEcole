package com.leopaulmartin.spring.leboncoinecole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//This single annotation is equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan.
@SpringBootApplication
public class LeboncoinEcoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeboncoinEcoleApplication.class, args);
	}

}
