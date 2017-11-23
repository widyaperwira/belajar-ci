package com.perwira.widya.belajar.belajarci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.perwira.widya.belajar.*")
@ComponentScan(basePackages = {"com.perwira.widya.belajar.*"})
@EntityScan("com.perwira.widya.belajar.*")
public class BelajarCiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BelajarCiApplication.class, args);
	}
}
