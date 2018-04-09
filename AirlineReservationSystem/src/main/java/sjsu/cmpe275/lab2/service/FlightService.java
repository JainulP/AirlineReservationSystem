package sjsu.cmpe275.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.repository.FlightRepository;

import java.util.List;
import java.util.Optional;

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

	public boolean checkAvailability(List<Flight> flights) {
		for (int i = 0; i < flights.size(); i++) {
			if(flights.get(i).getSeatsLeft() < 1) {
				return false;
			}
		}
		return true;
	}

}
