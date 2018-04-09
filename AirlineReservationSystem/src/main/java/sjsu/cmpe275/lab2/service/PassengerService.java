package sjsu.cmpe275.lab2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.entity.Passenger;
import sjsu.cmpe275.lab2.repository.FlightRepository;
import sjsu.cmpe275.lab2.repository.PassengerRepository;

@Service
public class PassengerService {

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private FlightRepository flightRepository;

	/*
	 * @param passengerId Id of the passenger whose id needs to be fetched
	 * 
	 * @return the passenger details
	 */
	public Passenger getPassenger(String passengerId) {
		return passengerRepository.findById(passengerId).get();
	}

	/*
	 * @param passenger create a passenger with the specified details
	 * 
	 * @return the passenger
	 */
	public Passenger createPassenger(Passenger passenger) {
		return passengerRepository.save(passenger);
	}

	/*
	 * @param passenger update a passenger with the specified details
	 * 
	 * @return the passenger
	 */
	public Passenger updatePassenger(Passenger passenger) {
		Passenger passenger_temp = passengerRepository.findById(passenger.getP_id()).get();
		passenger_temp.setFirstname(passenger.getFirstname());
		passenger_temp.setLastname(passenger.getLastname());
		passenger_temp.setGender(passenger.getGender());
		passenger_temp.setPhone(passenger.getPhone());
		passenger_temp.setAge(passenger.getAge());
		return passengerRepository.save(passenger_temp);
	}

	/*
	 * @param passengerId Id of the passenger to be deleted
	 * 
	 * @return the status of the operation
	 */
	public boolean deletePassenger(String passengerId) {
		// increase the seats in flights
		Passenger passenger = passengerRepository.findById(passengerId).get();
		List<Flight> flights = passenger.getFlights();
		increaseSeats(flights);
		boolean doesExists = passengerRepository.existsById(passengerId);
		if (doesExists) {
			passengerRepository.deleteById(passengerId);
			return true;
		} else {
			return false;
		}
	}

	public List<Flight> decreaseSeats(List<Flight> flights) {
		for (int i = 0; i < flights.size(); i++) {
			int count = flights.get(i).getSeatsLeft() - 1;
			flights.get(i).setSeatsLeft(count);
		}
		return (List<Flight>) flightRepository.saveAll(flights);
	}
	
	public List<Flight> increaseSeats(List<Flight> flights) {
		for (int i = 0; i < flights.size(); i++) {
			int count = flights.get(i).getSeatsLeft() + 1;
			flights.get(i).setSeatsLeft(count);
		}
		return (List<Flight>) flightRepository.saveAll(flights);
	}
}
