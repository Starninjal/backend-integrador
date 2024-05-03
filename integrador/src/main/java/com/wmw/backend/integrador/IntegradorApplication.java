package com.wmw.backend.integrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@CrossOrigin("*")
@SpringBootApplication
public class IntegradorApplication {
	public static void main(String[] args) {
		SpringApplication.run(IntegradorApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer
	webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");

			}
		};
	}
}
