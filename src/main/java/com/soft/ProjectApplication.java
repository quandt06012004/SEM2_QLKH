package com.soft;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.soft.service.StorageService;
@ComponentScan("com.soft")
@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			
			storageService.init();
		};
	}
	
	@Configuration
	public class WebConfig implements WebMvcConfigurer {

	    @Bean
	    public CharacterEncodingFilter characterEncodingFilter() {
	        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	        characterEncodingFilter.setEncoding("UTF-8");
	        characterEncodingFilter.setForceEncoding(true);
	        return characterEncodingFilter;
	    }
	}
}
