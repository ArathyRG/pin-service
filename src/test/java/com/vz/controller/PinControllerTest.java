package com.vz.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.vz.service.impl.PinServiceImpl;

/**
 * Test class for PinController 
 * 
 * @author arathy
 *
 */
@WebMvcTest(PinController.class)
@AutoConfigureMockMvc
public class PinControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PinServiceImpl pinService;

	/**
	 * Tests activate pin method of PinController
	 * 
	 * HTTP Code 200 and ACTIVE as substring in response is expected
	 * 
	 * @throws Throwable thrown if any exception occurs
	 */
	@Test
	public void testActivatePin() throws Throwable {

		String requestBody = "{\"customerId\": \"11111\", \"macAddress\": \"AA:BB:CC:DD:EE:FF\"}";

		Mockito.when(pinService.activatePin(ArgumentMatchers.any())).thenReturn("Status : ACTIVE");

		ResultActions actions = mockMvc
				.perform(post("/activate").contentType(MediaType.APPLICATION_JSON).content(requestBody));

		actions.andExpect(status().is(200));
		actions.andReturn().getResponse().getContentAsString().contains("ACTIVE");
	}

}
