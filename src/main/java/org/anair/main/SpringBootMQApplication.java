package org.anair.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.anair.spring.config.*"})
public class SpringBootMQApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMQApplication.class, args);
	}
}
