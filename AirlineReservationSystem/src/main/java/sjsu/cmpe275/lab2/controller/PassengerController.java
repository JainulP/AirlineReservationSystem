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
import sjsu.cmpe275.lab2.utils.Response;
import sjsu.cmpe275.lab2.utils.Utils;

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

	/*
	 * Create passenger from the details provided
	 */
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPassenger(@RequestParam(value = "firstname") String firstname,
			@RequestParam(value = "lastname") String lastname, @RequestParam(value = "age") int age,
			@RequestParam(value = "gender") String gender, @RequestParam(value = "phone") String phone) {
		Passenger passenger = new Passenger(firstname, lastname, age, gender, phone);
		Passenger passenger_res = passengerService.createPassenger(passenger);
		return new ResponseEntity<>(passenger_res, HttpStatus.CREATED);
	}

	/*
	 * Update passenger from the details provided
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatePassenger(@PathVariable("id") String passengerId,
			@RequestParam(value = "firstname") String firstname, @RequestParam(value = "lastname") String lastname,
			@RequestParam(value = "age") int age, @RequestParam(value = "gender") String gender,
			@RequestParam(value = "phone") String phone) {
		Passenger passenger = new Passenger(firstname, lastname, age, gender, phone);
		passenger.setId(passengerId);
		Passenger passenger_res = passengerService.updatePassenger(passenger);
		return new ResponseEntity<>(passenger_res, HttpStatus.OK);
	}

	/*
	 * Delete passenger with the id specified
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> deletePassenger(@PathVariable("id") String passengerId) {
		boolean success = passengerService.deletePassenger(passengerId);
		if (success) {
			return new ResponseEntity<>(new Response("Passenger with id " + passengerId + " is deleted successfully",200), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404,
					"Passenger with id " + passengerId + " does not exist"), HttpStatus.NOT_FOUND);
		}
	}
}
