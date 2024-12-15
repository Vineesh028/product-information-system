package com.product.information.config;


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
	@Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
	OpenAPI apiInfo() {
		return new OpenAPI().info(
				new Info().title("Product Information API").description("APIs for product information service").version("1.0"));
	}
}