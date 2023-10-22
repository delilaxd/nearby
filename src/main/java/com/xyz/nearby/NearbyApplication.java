package com.xyz.nearby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class NearbyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NearbyApplication.class, args);
	}

}
