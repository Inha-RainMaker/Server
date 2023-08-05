package com.rainmaker.rainmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RainmakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RainmakerApplication.class, args);
	}

}
