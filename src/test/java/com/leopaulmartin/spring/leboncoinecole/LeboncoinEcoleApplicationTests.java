package com.leopaulmartin.spring.leboncoinecole;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class) is used to provide a bridge between Spring Boot test features and JUnit.
// Whenever we are using any Spring Boot testing features in our JUnit tests, this annotation will be required.
@RunWith(SpringRunner.class)
@SpringBootTest
public class LeboncoinEcoleApplicationTests {

	@Test
	public void contextLoads() {
	}

}
