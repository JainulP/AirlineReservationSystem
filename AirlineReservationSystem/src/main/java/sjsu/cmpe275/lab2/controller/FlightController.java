package sjsu.cmpe275.lab2.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sjsu.cmpe275.lab2.dto.Flight;
import sjsu.cmpe275.lab2.entity.FlightEntity;
import sjsu.cmpe275.lab2.entity.Plane;
import sjsu.cmpe275.lab2.service.FlightService;
import sjsu.cmpe275.lab2.utils.Response;
import sjsu.cmpe275.lab2.utils.Utils;
import sjsu.cmpe275.lab2.utils.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class FlightController {

	@Autowired
	private FlightService flightService;

	/*
	 * Return flight details in JSON format
	 */
	@JsonView(View.FlightView.class)
	@RequestMapping(value = "/flight/{flightNumber}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getFlightJson(@PathVariable("flightNumber") String flightNumber) throws JSONException {
		FlightEntity flight = flightService.getFlight(flightNumber);
		if (flight == null)
			return new ResponseEntity<>(
					Utils.generateErrorResponse("BadRequest", 404,
							"Sorry, the requested flight with number " + flightNumber + " does not exist"),
					HttpStatus.NOT_FOUND);
		else {
			Flight flightTemp = new Flight(flight.getFlightNumber(), flight.getPrice(), flight.getOrigin(),
					flight.getDestinationTo(), flight.getDepartureTime(), flight.getArrivalTime(),
					flight.getSeatsLeft(), flight.getDescription(), flight.getPlane(), flight.getPassengers());
			return new ResponseEntity<>(flightTemp, HttpStatus.OK);
		}
		// return new ResponseEntity<>(flight, HttpStatus.OK);

	}

	/*
	 * Return flight details in XML format
	 */
	@JsonView(View.FlightView.class)
	@RequestMapping(value = "/flight/{flightNumber}", params = { "xml" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getFlightXml(@PathVariable("flightNumber") String flightNumber,
			@RequestParam(value = "xml") String isXml) throws JSONException {
		FlightEntity flight = flightService.getFlight(flightNumber);
		if (flight == null)
			return new ResponseEntity<>(
					Utils.generateErrorResponse("BadRequest", 404,
							"Sorry, the requested flight with number " + flightNumber + " does not exist"),
					HttpStatus.NOT_FOUND);
		else {
			Flight flightTemp = new Flight(flight.getFlightNumber(), flight.getPrice(), flight.getOrigin(),
					flight.getDestinationTo(), flight.getDepartureTime(), flight.getArrivalTime(),
					flight.getSeatsLeft(), flight.getDescription(), flight.getPlane(), flight.getPassengers());
			return new ResponseEntity<>(flightTemp, HttpStatus.OK);
		}

	}

	/*
	 * Create or Update flight and return details in XML format
	 */
	@JsonView(View.FlightView.class)
	@RequestMapping(value = "/flight/{flightNumber}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> createOrUpdateFlight(@PathVariable("flightNumber") String flightNumber,
			@RequestParam(value = "price") double price, @RequestParam(value = "origin") String origin,
			@RequestParam(value = "to") String destinationTo,
			@RequestParam(value = "departureTime") String departureTime,
			@RequestParam(value = "arrivalTime") String arrivalTime,
			@RequestParam(value = "description") String description, @RequestParam(value = "capacity") int capacity,
			@RequestParam(value = "model") String model, @RequestParam(value = "manufacturer") String manufacturer,
			@RequestParam(value = "year") int year) throws JSONException {

		if (capacity < 0) {
			return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 400, "Capacity can not be negative!"),
					HttpStatus.BAD_REQUEST);
		}
		if (arrivalTime.equals(departureTime)) {
			return new ResponseEntity<>(
					Utils.generateErrorResponse("BadRequest", 400, "Arrival and Departure Time can't be same!"),
					HttpStatus.BAD_REQUEST);
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
		try {
			Date arrival = simpleDateFormat.parse(arrivalTime);
			Date departure = simpleDateFormat.parse(departureTime);
			if (departure.compareTo(arrival) > 0) {
				return new ResponseEntity<>(
						Utils.generateErrorResponse("BadRequest", 400,
								"Sorry, the arrival time cannot be earlier than the departure time"),
						HttpStatus.BAD_GATEWAY);
			}
		} catch (ParseException e) {
			return new ResponseEntity<>(
					Utils.generateErrorResponse("BadRequest", 400,
							"Invalid Date Format! Please enter date in \"yyyy-MM-dd-HH\" format"),
					HttpStatus.BAD_GATEWAY);
		}
		FlightEntity flight = flightService.getFlight(flightNumber);
		if (flight == null) {

			FlightEntity flightS = flightService
					.createOrUpdate(new FlightEntity(flightNumber, price, origin, destinationTo, departureTime,
							arrivalTime, description, new Plane(capacity, model, manufacturer, year)));
			Flight flightTemp = new Flight(flightNumber, price, origin, destinationTo, departureTime, arrivalTime,
					flightS.getSeatsLeft(), description, flightS.getPlane(), flightS.getPassengers());
			return new ResponseEntity<>(flightTemp, HttpStatus.OK);
		} else {
			if (capacity < flight.getPassengers().size()) {
				return new ResponseEntity<>(
						Utils.generateErrorResponse("BadRequest", 400,
								"New capacity can't be less than " + "the reservations for that flight!"),
						HttpStatus.BAD_REQUEST);
			}
			if (flightService.isTimeOverlapping(flight, arrivalTime, departureTime)) {
				return new ResponseEntity<>(
						Utils.generateErrorResponse("BadRequest", 400,
								"This update cannot proceed as it causes time overlapping for passenger."),
						HttpStatus.BAD_REQUEST);
			} else {
				FlightEntity flightS = flightService
						.createOrUpdate(new FlightEntity(flightNumber, price, origin, destinationTo, departureTime,
								arrivalTime, description, new Plane(capacity, model, manufacturer, year)));
				Flight flightTemp = new Flight(flightNumber, price, origin, destinationTo, departureTime, arrivalTime,
						flightS.getSeatsLeft(), description, flightS.getPlane(), flightS.getPassengers());
				return new ResponseEntity<>(flightTemp, HttpStatus.OK);
			}
		}
	}

	/*
	 * Delete Flight and return details in XML format
	 */
	@JsonView(View.FlightView.class)
	@RequestMapping(value = "/airline/{flightNumber}", method = RequestMethod.DELETE, produces = {
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> deleteFlight(@PathVariable(value = "flightNumber") String flightNumber)
			throws JSONException {
		FlightEntity flight = flightService.getFlight(flightNumber);
		if (flight == null) {
			return new ResponseEntity<>(
					Utils.generateErrorResponse("BadRequest", 404,
							"Sorry, the requested flight with number " + flightNumber + " does not exist"),
					HttpStatus.NOT_FOUND);
		} else {
			if (flight.getPassengers().size() > 0) {
				return new ResponseEntity<>(
						Utils.generateErrorResponse("BadRequest", 400,
								"Sorry, the requested flight with number " + flightNumber
										+ " could not be deleted as it has one or more reservations"),
						HttpStatus.BAD_REQUEST);

			} else {
				flightService.deleteFlight(flightNumber);
				return new ResponseEntity<>(
						new Response("Flight with number " + flightNumber + " is deleted successfully", 200),
						HttpStatus.OK);

			}
		}

	}

}
