package com.vz.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

/**
 * Configuration class to create required beans
 * 
 * @author arathy
 *
 */
@Configuration
@AutoConfigureWireMock
public class PinConfig {

	/**
	 * Bean configuration for wiremock server
	 * 
	 * @return Options bean
	 */
	@Bean
	public Options wireMockOptions() {

		final WireMockConfiguration options = WireMockSpring.options();
		options.port(9092);

		return options;
	}

	/**
	 * Bean configuration for Rest Template
	 * 
	 * @param builder - rest template builder
	 * 
	 * @return RestTemplate bean
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
