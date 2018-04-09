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
	public ResponseEntity<?> createPassenger(@RequestParam(value = "passengerId") String passengerId,
			@RequestParam(value = "flightLists") String flightLists) {
		Reservation reservation = new Reservation();
		Passenger passenger = passengerService.getPassenger(passengerId);
		reservation.setPassenger(passenger);
		List<String> list = new ArrayList<String>(Arrays.asList(flightLists.split(",")));
		List<Flight> flights = flightService.findAllFlights(list);
		reservation.setFlights(flights);
		Reservation reservation_res = reservationService.createReservation(reservation);
		return new ResponseEntity<>(reservation_res, HttpStatus.CREATED);
	}

}
