package com.vz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.vz.constants.Constants;
import com.vz.model.CustomerDevice;
import com.vz.service.PinService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class to handle Pin Activation requests
 * 
 * @author arathy
 *
 */
@Service
@Slf4j
public class PinServiceImpl implements PinService {

	@Value("${southbound.system.url}")
	private String sbUrl;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Invokes southbound system to activate a PIN terminal for a customer.
	 * 
	 * Based on southbound response, activation status is mapped.
	 * 
	 * @param custDevice - input request with customer id and device id
	 * 
	 * @return activation status - ACTIVE/INACTIVE/UNKNOWN
	 */
	public String activatePin(CustomerDevice custDevice) {

		String status = null;

		log.info("Input Request: {}", new Gson().toJson(custDevice));

		try {
			restTemplate.postForEntity(sbUrl, custDevice, String.class);

			log.info("PIN Successfully activated");
			status = Constants.ACTIVE_STATUS;

		} catch (HttpClientErrorException exception) {

			status = handleExceptions(exception.getStatusCode().value());

		} catch (Exception exception) {

			log.error("Error while activating pin : {}", exception.getMessage());
			status = Constants.UNKNOWN_STATUS;
		}

		return status;
	}

	/**
	 * Handles if any error responses from southbound system.
	 * 
	 * For 404 and 409, activation status is mapped as INACTIVE and for other
	 * scenarios, it's mapped as UNKNOWN.
	 * 
	 * @param statusCode - HTTP status code from southbound system
	 * 
	 * @return mapped activation status
	 */
	private String handleExceptions(int statusCode) {

		if (statusCode == 404) {

			log.error("PIN was not registered");
			return Constants.INACTIVE_STATUS;

		} else if (statusCode == 409) {

			log.error("PIN already attached to different customer");
			return Constants.INACTIVE_STATUS;

		} else {

			log.error("Activation failed due to unknown error");
			return Constants.UNKNOWN_STATUS;

		}
	}

}
