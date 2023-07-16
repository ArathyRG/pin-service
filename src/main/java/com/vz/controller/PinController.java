package com.vz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vz.model.CustomerDevice;
import com.vz.service.PinService;

/**
 * Controller class to handle Pin Activation requests
 * 
 * @author arathy
 *
 */
@RestController
public class PinController {

	@Autowired
	private PinService pinService;

	/**
	 * Activate a PIN terminal for a customer
	 * 
	 * @param custDevice - input request with customer id and device id
	 * 
	 * @return activation status - ACTIVE/INACTIVE/UNKNOWN
	 */
	@RequestMapping(value = "/activate", method = RequestMethod.POST)
	public String activatePin(@RequestBody(required = true) CustomerDevice custDevice) {

		return pinService.activatePin(custDevice);

	}

}
