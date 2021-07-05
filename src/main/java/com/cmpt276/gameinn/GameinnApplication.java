package com.cmpt276.gameinn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class GameinnApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameinnApplication.class, args);
	}

}
