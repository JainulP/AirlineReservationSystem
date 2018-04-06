package sjsu.cmpe275.lab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sjsu.cmpe275.lab2.entity.Passenger;
import sjsu.cmpe275.lab2.service.PassengerService;

@RestController
@RequestMapping("/passenger")
public class PassengerController {
	@Autowired
	private PassengerService passengerService;

	/*
	 * Return passenger details in JSON format
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPassengerJson(@PathVariable("id") String passengerId) {
		Passenger passenger = passengerService.getPassenger(passengerId);
		return new ResponseEntity<>(passenger, HttpStatus.OK);
	}

	/*
	 * Return passenger details in XML format
	 */
	@RequestMapping(value = "/{id}", params = {
			"xml" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> getPassengerXml(@PathVariable("id") String passengerId,
			@RequestParam(value = "xml") String isXml) {
		Passenger passenger = passengerService.getPassenger(passengerId);
		return new ResponseEntity<>(passenger, HttpStatus.OK);
	}
}
