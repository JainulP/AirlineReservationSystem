package sjsu.cmpe275.lab2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjsu.cmpe275.lab2.entity.FlightEntity;
import sjsu.cmpe275.lab2.entity.PassengerEntity;
import sjsu.cmpe275.lab2.repository.FlightRepository;
import sjsu.cmpe275.lab2.repository.PassengerRepository;

import javax.transaction.Transactional;

@Service
@Transactional(Transactional.TxType.REQUIRED)
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
	public PassengerEntity getPassenger(String passengerId) {
		return passengerRepository.findById(passengerId).get();
	}

	/*
	 * @param passenger create a passenger with the specified details
	 * 
	 * @return the passenger
	 */
	public PassengerEntity createPassenger(PassengerEntity passenger) {
		return passengerRepository.save(passenger);
	}

	/*
	 * @param passenger update a passenger with the specified details
	 * 
	 * @return the passenger
	 */
	public PassengerEntity updatePassenger(PassengerEntity passenger) {
		PassengerEntity passenger_temp = passengerRepository.findById(passenger.getId()).get();
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

		boolean doesExists = passengerRepository.existsById(passengerId);
		if (doesExists) {
			PassengerEntity passenger = passengerRepository.findById(passengerId).get();
			List<FlightEntity> flights = passenger.getFlights();
			increaseSeats(flights);
			passengerRepository.deleteById(passengerId);
			return true;
		} else {
			return false;
		}
	}

	public List<FlightEntity> decreaseSeats(List<FlightEntity> flights) {
		for (int i = 0; i < flights.size(); i++) {
			int count = flights.get(i).getSeatsLeft() - 1;
			flights.get(i).setSeatsLeft(count);
		}
		return (List<FlightEntity>) flightRepository.saveAll(flights);
	}
	
	public List<FlightEntity> increaseSeats(List<FlightEntity> flights) {
		for (int i = 0; i < flights.size(); i++) {
			int count = flights.get(i).getSeatsLeft() + 1;
			flights.get(i).setSeatsLeft(count);
		}
		return (List<FlightEntity>) flightRepository.saveAll(flights);
	}

	public PassengerEntity findByPhone(String phone){
		return  passengerRepository.findByPhone(phone);
	}
}
