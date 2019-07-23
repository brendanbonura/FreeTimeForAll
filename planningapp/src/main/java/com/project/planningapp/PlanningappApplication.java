package com.project.planningapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PlanningappApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanningappApplication.class, args);
	}

}
