package com.vz.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.vz.model.CustomerDevice;
import com.vz.service.impl.PinServiceImpl;

/**
 * Test class for PinService
 * 
 * @author arathy
 *
 */
@ExtendWith(MockitoExtension.class)
public class PinServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private PinServiceImpl pinService;

	@BeforeEach
	public void setupContext() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(pinService, "sbUrl", "http://localhost:9092/activate");
	}

	/**
	 * Tests success scenario where pin terminal is activated
	 * 
	 * Status : ACTIVE is expected as response
	 * 
	 * @throws Throwable thrown if any exception occurs
	 */
	@Test
	public void testActivatePin() {

		CustomerDevice device = new CustomerDevice("11111", "AA:BB:CC:DD:EE:FF");

		ResponseEntity<String> response = ResponseEntity.status(201).body("Status: 201 Created");

		Mockito.when(restTemplate.postForEntity(ArgumentMatchers.anyString(),
				ArgumentMatchers.any(CustomerDevice.class), ArgumentMatchers.<Class<String>>any()))
				.thenReturn(response);

		String resp = pinService.activatePin(device);
		assertEquals("Status : ACTIVE", resp);
	}

	/**
	 * Tests failure scenario where pin terminal is not registered
	 * 
	 * Status : INACTIVE is expected as response
	 * 
	 * @throws Throwable thrown if any exception occurs
	 */
	@Test
	public void testPinNotRegistered() {

		CustomerDevice device = new CustomerDevice("11111", "AA:BB:CC:DD:EE:FF");

		Mockito.when(restTemplate.postForEntity(ArgumentMatchers.anyString(),
				ArgumentMatchers.any(CustomerDevice.class), ArgumentMatchers.<Class<String>>any()))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		String resp = pinService.activatePin(device);
		assertEquals("Status : INACTIVE", resp);
	}

	/**
	 * Tests failure scenario where pin terminal is already attached to another
	 * customer
	 * 
	 * Status : INACTIVE is expected as response
	 * 
	 * @throws Throwable thrown if any exception occurs
	 */
	@Test
	public void testPinAlreadyRegistered() {

		CustomerDevice device = new CustomerDevice("11111", "AA:BB:CC:DD:EE:FF");

		Mockito.when(restTemplate.postForEntity(ArgumentMatchers.anyString(),
				ArgumentMatchers.any(CustomerDevice.class), ArgumentMatchers.<Class<String>>any()))
				.thenThrow(new HttpClientErrorException(HttpStatus.CONFLICT));

		String resp = pinService.activatePin(device);
		assertEquals("Status : INACTIVE", resp);
	}

	/**
	 * Tests failure scenario when any other unknown 4xx error occurs
	 * 
	 * Status : UNKNOWN is expected as response
	 * 
	 * @throws Throwable thrown if any exception occurs
	 */
	@Test
	public void testUnknownError() {

		CustomerDevice device = new CustomerDevice("11111", "AA:BB:CC:DD:EE:FF");

		Mockito.when(restTemplate.postForEntity(ArgumentMatchers.anyString(),
				ArgumentMatchers.any(CustomerDevice.class), ArgumentMatchers.<Class<String>>any()))
				.thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));

		String resp = pinService.activatePin(device);
		assertEquals("Status : UNKNOWN", resp);
	}

	/**
	 * Tests failure scenario when any other unknown error occurs
	 * 
	 * Status : UNKNOWN is expected as response
	 * 
	 * @throws Throwable thrown if any exception occurs
	 */
	@Test
	public void testGenericError() {

		CustomerDevice device = new CustomerDevice("11111", "AA:BB:CC:DD:EE:FF");

		Mockito.when(restTemplate.postForEntity(ArgumentMatchers.anyString(),
				ArgumentMatchers.any(CustomerDevice.class), ArgumentMatchers.<Class<String>>any()))
				.thenThrow(new IllegalStateException());

		String resp = pinService.activatePin(device);
		assertEquals("Status : UNKNOWN", resp);
	}

}
