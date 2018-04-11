package sjsu.cmpe275.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sjsu.cmpe275.lab2.entity.FlightEntity;
import sjsu.cmpe275.lab2.entity.PassengerEntity;
import sjsu.cmpe275.lab2.repository.FlightRepository;
import sjsu.cmpe275.lab2.utils.Utils;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class FlightService {

	@Autowired
	private FlightRepository flightRepository;


	/*
	 * Return flight details for the given flightNumber
	 */
	public FlightEntity getFlight(String flightNumber) {
		Optional<FlightEntity> flight = flightRepository.findById(flightNumber);
		if (flight.isPresent()) {
			return flight.get();
		} else
			return null;
	}

	/*
	 * Creates or updates existing flight and returns the created or updated flight
	 * Cannot set the capacity of flight less than the reservation already made for the flight if flight is updated
	 */
	public FlightEntity createOrUpdate(FlightEntity flight) {
		Optional<FlightEntity> existingFlight = flightRepository.findById(flight.getFlightNumber());
		if (existingFlight.isPresent()) {
			FlightEntity oldFlight = flightRepository.findById(flight.getFlightNumber()).get();
			flight.setSeatsLeft(flight.getPlane().getCapacity() - oldFlight.getPassengers().size());
			FlightEntity created = flightRepository.save(flight);
			return created;
		} else {
			flight.setSeatsLeft(flight.getPlane().getCapacity());
			FlightEntity created = flightRepository.save(flight);
			return created;
		}
	}
	/*
	 * Deletes the flight for the given flightNumber
	 */
	public void deleteFlight(String flightNumber) {
		FlightEntity flight = flightRepository.findById(flightNumber).get();
		flightRepository.delete(flight);
	}

	/*
	 * Finds and Returns the List of flights for the given flight numbers
	 */
	public List<FlightEntity> findAllFlights(List<String> flightnumbers) {
		List<FlightEntity> flights = (List<FlightEntity>) flightRepository.findAllById(flightnumbers);
		
		return flights;
	}


	/*
	 * Checks if there is any time overlapping for the passenger's flights
	 */
	public boolean isTimeOverlapping(FlightEntity flight, String arrivalTime, String departureTime) {
		List<PassengerEntity> passengers = flight.getPassengers();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh", Locale.US);
		for (PassengerEntity passenger : passengers) {
			List<FlightEntity> bookedFlights = passenger.getFlights();
			for (FlightEntity bookedFlight : bookedFlights) {
				try {
					Date arrival = dateFormat.parse(bookedFlight.getArrivalTime());
					Date departure = dateFormat.parse(bookedFlight.getDepartureTime());
					Date min = dateFormat.parse(departureTime);
					Date max = dateFormat.parse(arrivalTime);

					if ((arrival.compareTo(min) > 0 && arrival.compareTo(max) < 0)
							|| (departure.compareTo(min) > 0 && departure.compareTo(max) < 0)) {
						return true;
					}

				} catch (ParseException e) {
					System.out.println("BadRequest " + "417" + " Invalid Date Format");
				}
			}
		}
		return false;
	}

	/*
	 * Checks the seat availability in each of the flights given as input
	 */
	public boolean checkAvailability(List<FlightEntity> flights) {
		for (int i = 0; i < flights.size(); i++) {
			if (flights.get(i).getSeatsLeft() < 1) {
				return false;
			}
		}
		return true;

	}

	/*
	 * Checks the time overlapping between the flights to be booked and the flights already been booked while updating the reservation
	 */
	public boolean checkIfFlightsOverlap(List<FlightEntity> flightstoBeBooked, PassengerEntity passenger) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh", Locale.US);
		List<FlightEntity> flights = findFlightsByPassengerId(passenger.getId());
		for (int i = 0; i < flightstoBeBooked.size(); i++) {
			Date arrival = null;
			Date departure = null;
			try {
				arrival = dateFormat.parse(flightstoBeBooked.get(i).getArrivalTime());
				departure = dateFormat.parse(flightstoBeBooked.get(i).getDepartureTime());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("BadRequest " + "417" + " Invalid Date Format");
			}

			for (int j = 0; j < flights.size(); j++) {
				try {
					Date max = dateFormat.parse(flights.get(j).getArrivalTime());
					Date min = dateFormat.parse(flights.get(j).getDepartureTime());

					if ((arrival.compareTo(min) >= 0 && arrival.compareTo(max) <= 0)
							|| (departure.compareTo(min) >= 0 && departure.compareTo(max) <= 0)) {
						if(!flights.get(j).getFlightNumber().equals(flightstoBeBooked.get(i).getFlightNumber())){
							return true;
						}
					}
				} catch (ParseException e) {
					System.out.println("BadRequest " + "417" + " Invalid Date Format");
				}
			}
		}
		return false;
	}

	/*
	 * Returns list of flights booked by the passenger
	 */
	public List<FlightEntity> findFlightsByPassengerId(String passengerId) {
		List<String> passengerFlights = (List<String>) flightRepository.findByPassengerId(passengerId);
		List<FlightEntity> flights = (List<FlightEntity>) flightRepository.findAllById(passengerFlights);
		return flights;
	}
	

}
