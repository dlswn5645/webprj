package com.myapp.webprj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
public class WebprjApplication {

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");//접두사
		resolver.setSuffix(".jsp");//접미사
		return resolver;
	}

	public static void main(String[] args) {
		SpringApplication.run(WebprjApplication.class, args);
	}

}
