package sjsu.cmpe275.lab2.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sjsu.cmpe275.lab2.dto.Reservation;
import sjsu.cmpe275.lab2.entity.FlightEntity;
import sjsu.cmpe275.lab2.entity.PassengerEntity;
import sjsu.cmpe275.lab2.entity.ReservationEntity;
import sjsu.cmpe275.lab2.entity.Reservations;
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
		ReservationEntity reservation = reservationService.getReservation(reservationNumber);
		if (reservation == null)
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404,
					"Reservation with number " + reservationNumber + " does not exist"), HttpStatus.NOT_FOUND);
		else {
			Reservation reservationTemp = new Reservation(reservation.getReservationNumber(),
					reservation.getPassenger(), reservation.getPrice(), reservation.getFlights());
			return new ResponseEntity<>(reservationTemp, HttpStatus.OK);
		}

	}

	/*
	 * Return Reservation details in XML format
	 */
	@JsonView(View.ReservationView.class)
	@RequestMapping(value = "/{number}", params = { "xml" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getReservationXml(@PathVariable("number") String reservationNumber,
			@RequestParam(value = "xml") String isXml) {
		ReservationEntity reservation = reservationService.getReservation(reservationNumber);
		if (reservation == null)
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404,
					"Reservation with number " + reservationNumber + " does not exist"), HttpStatus.NOT_FOUND);

		else {
			Reservation reservationTemp = new Reservation(reservation.getReservationNumber(),
					reservation.getPassenger(), reservation.getPrice(), reservation.getFlights());
			return new ResponseEntity<>(reservationTemp, HttpStatus.OK);
		}

	}

	/*
	 * Make a reservation
	 */
	@JsonView(View.ReservationView.class)
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> createReservation(@RequestParam(value = "passengerId") String passengerId,
			@RequestParam(value = "flightLists") String flightLists) {
		ReservationEntity reservation = new ReservationEntity();
		PassengerEntity passenger = passengerService.getPassenger(passengerId);
		reservation.setPassenger(passenger);
		List<String> list = new ArrayList<String>(Arrays.asList(flightLists.split(",")));
		List<FlightEntity> flights = flightService.findAllFlights(list);
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

		PassengerEntity pTemp = passengerService.getPassenger(passengerId);
		List<FlightEntity> flightsExisting = new ArrayList();
		flightsExisting = pTemp.getFlights();
		flightsExisting.addAll(flights);
		passenger.setFlights(flightsExisting);

		reservation.setFlights(flights);
		ReservationEntity reservation_res = reservationService.createReservation(reservation);
		Reservation reservationTemp = new Reservation(reservation_res.getReservationNumber(),
				reservation_res.getPassenger(), reservation_res.getPrice(), reservation_res.getFlights());
		return new ResponseEntity<>(reservationTemp, HttpStatus.OK);
	}

	/*
	 * Update a reservation
	 */
	@JsonView(View.ReservationView.class)
	@RequestMapping(value = "/{number}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateReservation(@PathVariable("number") String reservationNumber,
			@RequestParam(value = "flightsAdded", required = false) String flightsAdded,
			@RequestParam(value = "flightsRemoved", required = false) String flightsRemoved) {
		ReservationEntity reservation = reservationService.getReservation(reservationNumber);
		boolean status_add = true;

		List<String> flightsAddedListNums = new ArrayList<String>();
		List<FlightEntity> flightsAddedList = new ArrayList<>();
		if (flightsAdded != null && flightsAdded == "") {
			return new ResponseEntity<>(
					Utils.generateErrorResponse("BadRequest", 400, "Flights to be added can not be null"),
					HttpStatus.BAD_REQUEST);
		}
		if (flightsAdded != null) {
			flightsAddedListNums = new ArrayList<String>(Arrays.asList(flightsAdded.split(",")));
			flightsAddedList = flightService.findAllFlights(flightsAddedListNums);
			status_add = flightService.checkAvailability(flightsAddedList);
		}
		List<String> flightsRemovedListNums = new ArrayList<String>();
		List<FlightEntity> flightsRemovedList = new ArrayList<>();

		if (flightsRemoved != null && flightsRemoved == "") {
			return new ResponseEntity<>(
					Utils.generateErrorResponse("BadRequest", 400, "Flights to be removed can not be null"),
					HttpStatus.NOT_FOUND);
		}
		if (flightsRemoved != null) {
			flightsRemovedListNums = new ArrayList<String>(Arrays.asList(flightsRemoved.split(",")));
			flightsRemovedList = flightService.findAllFlights(flightsRemovedListNums);
		}

		if (status_add == false) {
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404, "Seats not available"),
					HttpStatus.NOT_FOUND);
		}
		if (flightsRemovedList.size() > 0) {
			passengerService.increaseSeats(flightsRemovedList);
		}
		List<FlightEntity> flights = new ArrayList<FlightEntity>();
		flights = reservation.getFlights();

		PassengerEntity passenger = reservation.getPassenger();
		List<FlightEntity> flightsExisting = new ArrayList();
		PassengerEntity pTemp = passengerService.getPassenger(passenger.getId());
		flightsExisting = pTemp.getFlights();

		for (int i = 0; i < flightsRemovedList.size(); i++) {
			FlightEntity flight = flightsRemovedList.get(i);
			flights.remove(flight);
			flightsExisting.remove(flight);
		}

		for (int i = 0; i < flightsAddedList.size(); i++) {
			FlightEntity flight = flightsAddedList.get(i);
			flights.add(flight);
		}

		flightsExisting.addAll(flights);

		passenger.setFlights(flightsExisting);
		// passenger.setFlights(flights);
		ReservationEntity reservation_res = reservationService.updateReservation(reservation);
		Reservation reservationTemp = new Reservation(reservation_res.getReservationNumber(),
				reservation_res.getPassenger(), reservation_res.getPrice(), reservation_res.getFlights());
		return new ResponseEntity<>(reservationTemp, HttpStatus.OK);
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
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> searchReservationXml(
			@RequestParam(value = "passengerId", required = false) String passengerId,
			@RequestParam(value = "origin", required = false) String origin,
			@RequestParam(value = "destination", required = false) String destination,
			@RequestParam(value = "flightNumber", required = false) String flightNumber) {
		List<ReservationEntity> reservations = reservationService.searchReservation(passengerId, origin, destination,
				flightNumber);
		if (reservations.size() == 0)
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404,
					"Reservations with the specified criteria does not exist"), HttpStatus.NOT_FOUND);
		else {
			List<Reservation> reservationSampleList = new ArrayList();
			for (int i = 0; i < reservations.size(); i++) {
				ReservationEntity reservation_res = reservations.get(i);
				Reservation reservationSample = new Reservation(reservation_res.getReservationNumber(),
						reservation_res.getPassenger(), reservation_res.getPrice(), reservation_res.getFlights());
				reservationSampleList.add(reservationSample);
			}
			Reservations reservationsTemp = new Reservations();
			reservationsTemp.setReservation(reservationSampleList);
			return new ResponseEntity<>(reservationsTemp, HttpStatus.OK);
		}

	}

}
