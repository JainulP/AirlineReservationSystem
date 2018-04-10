package sjsu.cmpe275.lab2.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.entity.Passenger;
import sjsu.cmpe275.lab2.entity.Reservation;
import sjsu.cmpe275.lab2.service.FlightService;
import sjsu.cmpe275.lab2.service.PassengerService;
import sjsu.cmpe275.lab2.service.ReservationService;
import sjsu.cmpe275.lab2.utils.Response;
import sjsu.cmpe275.lab2.utils.Utils;
import sjsu.cmpe275.lab2.utils.View;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private PassengerService passengerService;

	@Autowired
	private FlightService flightService;

	/*
	 * Return Reservation details in JSON format
	 */
	@JsonView(View.ReservationView.class)
	@RequestMapping(value = "/{number}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getReservationJson(@PathVariable("number") String reservationNumber) throws JSONException {
		Reservation reservation = reservationService.getReservation(reservationNumber);
		if (reservation == null)
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404,
					"Reservation with number " + reservationNumber + " does not exist"), HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(reservation, HttpStatus.OK);

	}

	/*
	 * Return Reservation details in XML format
	 */
	@JsonView(View.ReservationView.class)
	@RequestMapping(value = "/{number}", params = { "xml" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getReservationXml(@PathVariable("number") String reservationNumber,
			@RequestParam(value = "xml") String isXml) {
		Reservation reservation = reservationService.getReservation(reservationNumber);
		if (reservation == null)
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404,
					"Reservation with number " + reservationNumber + " does not exist"), HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(reservation, HttpStatus.OK);

	}

	/*
	 * Make a reservation
	 */
	@JsonView(View.ReservationView.class)
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> createReservation(@RequestParam(value = "passengerId") String passengerId,
			@RequestParam(value = "flightLists") String flightLists) {
		Reservation reservation = new Reservation();
		Passenger passenger = passengerService.getPassenger(passengerId);
		reservation.setPassenger(passenger);
		List<String> list = new ArrayList<String>(Arrays.asList(flightLists.split(",")));
		List<Flight> flights = flightService.findAllFlights(list);
		boolean statusAvailability = flightService.checkAvailability(flights);
		if (statusAvailability == false) {
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404, "Seats not available"),
					HttpStatus.NOT_FOUND);
		}

		boolean statusOverlap = flightService.checkIfFlightsOverlap(flights, passenger);
		if (statusOverlap == true) {
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404, "Flights overlapping"),
					HttpStatus.NOT_FOUND);
		}

		passenger.setFlights(flights);
		reservation.setFlights(flights);
		Reservation reservation_res = reservationService.createReservation(reservation);
		return new ResponseEntity<>(reservation_res, HttpStatus.CREATED);
	}

	/*
	 * Update a reservation
	 */
	@JsonView(View.ReservationView.class)
	@RequestMapping(value = "/{number}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateReservation(@PathVariable("number") String reservationNumber,
			@RequestParam(value = "flightsAdded") String flightsAdded,
			@RequestParam(value = "flightsRemoved") String flightsRemoved) {
		Reservation reservation = reservationService.getReservation(reservationNumber);
		boolean status_add = true;

		List<String> flightsAddedListNums = new ArrayList<String>(Arrays.asList(flightsAdded.split(",")));
		List<Flight> flightsAddedList = flightService.findAllFlights(flightsAddedListNums);
		status_add = flightService.checkAvailability(flightsAddedList);

		List<String> flightsRemovedListNums = new ArrayList<String>(Arrays.asList(flightsRemoved.split(",")));
		List<Flight> flightsRemovedList = flightService.findAllFlights(flightsRemovedListNums);

		if (status_add == false) {
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404, "Seats not available"),
					HttpStatus.NOT_FOUND);
		}

		List<Flight> flights = new ArrayList<Flight>();
		flights = reservation.getFlights();
		for (int i = 0; i < flightsRemovedList.size(); i++) {
			Flight flight = flightsRemovedList.get(i);
			flights.remove(flight);
		}

		for (int i = 0; i < flightsAddedList.size(); i++) {
			Flight flight = flightsAddedList.get(i);
			flights.add(flight);
		}

		Passenger passenger = reservation.getPassenger();
		passenger.setFlights(flights);
		Reservation reservation_res = reservationService.updateReservation(reservation);
		return new ResponseEntity<>(reservation_res, HttpStatus.CREATED);
	}

	/*
	 * Delete reservation
	 */
	@JsonView(View.ReservationView.class)
	@RequestMapping(value = "/{number}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> deletePassenger(@PathVariable("number") String reservationNum) {
		boolean success = reservationService.deleteReservation(reservationNum);
		if (success) {
			return new ResponseEntity<>(
					new Response("Reservation with number " + reservationNum + " is canceled successfully", 200),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404,
					"Reservation with number " + reservationNum + " does not exist"), HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Search Reservation details in XML format
	 */
	@JsonView(View.ReservationView.class)
	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> searchReservationXml(@RequestParam(value = "passengerId") String passengerId,
			@RequestParam(value = "origin") String origin, @RequestParam(value = "to") String to,
			@RequestParam(value = "flightNumber") String flightNumber) {
		List<Reservation> reservations = reservationService.searchReservation(passengerId, origin, to, flightNumber);
		if (reservations.size() == 0)
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404,
					"Reservations with the specified criteria does not exist"), HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(reservations, HttpStatus.OK);

	}

}
