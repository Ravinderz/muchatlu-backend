package com.muchatlu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MuchatluApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuchatluApplication.class, args);
	}

}
