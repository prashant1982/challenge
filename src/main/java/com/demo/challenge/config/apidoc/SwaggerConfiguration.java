package com.demo.challenge.config.apidoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Springfox Swagger configuration.
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	private final Logger log = LoggerFactory
			.getLogger(SwaggerConfiguration.class);

	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

	/**
	 * Swagger Springfox configuration.
	 */
	@Bean
	public Docket swaggerSpringfoxDocket() {
		log.debug("Starting Swagger");
		StopWatch watch = new StopWatch();
		watch.start();
		ApiInfo apiInfo = new ApiInfo("Challenge API", "Challenge API documentation", "0.0.1", 
				"", new Contact("Prashant KUMAR", "", "prashant.kumar7@cognizant.com"), 
				"", "");

		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo)
				.genericModelSubstitutes(ResponseEntity.class)
				.forCodeGeneration(true)
				.genericModelSubstitutes(ResponseEntity.class)
				.select()
				.paths(regex(DEFAULT_INCLUDE_PATTERN))
				.build();
		watch.stop();
		log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
		return docket;
	}
}
