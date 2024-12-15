package com.user.portal.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfiguration {

	/**
	 * Open api info 
	 * @return
	 */
	@Bean(name = "apiInfo")
	OpenAPI apiInfo() {
		return new OpenAPI().info(
				new Info().title("User Portal API").description("APIs for user portal service").version("1.0"));
	}
}