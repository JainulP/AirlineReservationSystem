package sjsu.cmpe275.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjsu.cmpe275.lab2.entity.Passenger;
import sjsu.cmpe275.lab2.repository.PassengerRepository;

@Service
public class PassengerService {

	@Autowired
	private PassengerRepository passengerRepository;

	/*
	 * @param passengerId Id of the passenger whose id needs to be fetched
	 * 
	 * @return the passenger details
	 */
	public Passenger getPassenger(String passengerId) {
		return passengerRepository.findById(passengerId).get();
	}
}
