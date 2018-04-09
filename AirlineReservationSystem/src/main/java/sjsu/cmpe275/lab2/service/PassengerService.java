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
		boolean doesExists = passengerRepository.existsById(passengerId);
		if (doesExists) {
			passengerRepository.deleteById(passengerId);
			return true;
		} else {
			return false;
		}
	}
}
