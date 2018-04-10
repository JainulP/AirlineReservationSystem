package sjsu.cmpe275.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.entity.Passenger;
import sjsu.cmpe275.lab2.repository.FlightRepository;
import sjsu.cmpe275.lab2.utils.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FlightService {

	@Autowired
	private FlightRepository flightRepository;

	public Flight getFlight(String flightNumber) {
		Optional<Flight> flight = flightRepository.findById(flightNumber);
		if (flight.isPresent()) {
			return flight.get();
		} else
			return null;
	}

	public Flight createOrUpdate(Flight flight) {
		// flight.setSeatsLeft(flight.getPlane().getCapacity());
		// need to update seatsleft when capacity is updated

		Optional<Flight> existingFlight = flightRepository.findById(flight.getFlightNumber());
		if (existingFlight.isPresent()) {
			Flight oldFlight = flightRepository.findById(flight.getFlightNumber()).get();
			flight.setSeatsLeft(flight.getPlane().getCapacity() - oldFlight.getPassengers().size());
			Flight created = flightRepository.save(flight);
			return created;
		} else {
			flight.setSeatsLeft(flight.getPlane().getCapacity());
			Flight created = flightRepository.save(flight);
			return created;
		}
	}

	public void deleteFlight(String flightNumber) {
		Flight flight = flightRepository.findById(flightNumber).get();
		flightRepository.delete(flight);
	}

	public List<Flight> findAllFlights(List<String> flightnumbers) {
		List<Flight> flights = (List<Flight>) flightRepository.findAllById(flightnumbers);
		return flights;
	}

	public boolean isTimeOverlapping(Flight flight, String arrivalTime, String departureTime) {
		List<Passenger> passengers = flight.getPassengers();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh", Locale.US);
		for (Passenger passenger : passengers) {
			List<Flight> bookedFlights = passenger.getFlights();
			for (Flight bookedFlight : bookedFlights) {
				try {
					Date arrival = dateFormat.parse(bookedFlight.getArrivalTime());
					Date departure = dateFormat.parse(bookedFlight.getDepartureTime());
					Date min = dateFormat.parse(departureTime);
					Date max = dateFormat.parse(arrivalTime);

					if ((arrival.compareTo(min) >= 0 && arrival.compareTo(max) <= 0) || (departure.compareTo(min) >= 0 && departure.compareTo(max) <= 0)) {
						return true;
					}

				} catch (ParseException e) {
					System.out.println("BadRequest " + "417" + " Invalid Date Format");
				}
			}
		}
		return false;
	}

	public boolean checkAvailability(List<Flight> flights) {
		for (int i = 0; i < flights.size(); i++) {
			if (flights.get(i).getSeatsLeft() < 1) {
				return false;
			}
		}
		return true;

	}

	public boolean checkIfFlightsOverlap(List<Flight> flightstoBeBooked, Passenger passenger) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh", Locale.US);
		List<String> passengerFlights = (List<String>) flightRepository.findByPassengerId(passenger.getId());
		List<Flight> flights = (List<Flight>) flightRepository.findAllById(passengerFlights);
		for (int i = 0; i < flightstoBeBooked.size(); i++) {
			Date arrival = null;
			Date departure = null;
			try {
				arrival = dateFormat.parse(flightstoBeBooked.get(i).getArrivalTime());
				departure = dateFormat.parse(flightstoBeBooked.get(i).getDepartureTime());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (int j = 0; j < flights.size(); i++) {
				try {
					Date max = dateFormat.parse(flights.get(j).getArrivalTime());
					Date min = dateFormat.parse(flights.get(j).getDepartureTime());

					if ((arrival.compareTo(min) >= 0 && arrival.compareTo(max) <= 0)
							|| (departure.compareTo(min) >= 0 && departure.compareTo(max) <= 0)) {
						return true;
					}
				} catch (ParseException e) {
					System.out.println("BadRequest " + "417" + " Invalid Date Format");
				}
			}
		}
		return false;
	}

}
