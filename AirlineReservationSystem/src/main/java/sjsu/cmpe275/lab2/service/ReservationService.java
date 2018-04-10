package sjsu.cmpe275.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.entity.PassengerEntity;
import sjsu.cmpe275.lab2.entity.ReservationEntity;
import sjsu.cmpe275.lab2.repository.FlightRepository;
import sjsu.cmpe275.lab2.repository.PassengerRepository;
import sjsu.cmpe275.lab2.repository.ReservationRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	PassengerRepository passengerRepository;
	@Autowired
	FlightRepository flightRepository;

	@Autowired
	private PassengerService passengerService;

	@Autowired
	private FlightService flightService;

	public ReservationEntity getReservation(String reservationNumber) {
		Optional<ReservationEntity> reservation = reservationRepository.findById(reservationNumber);
		if (reservation.isPresent()) {
			ReservationEntity res = reservation.get();
			return res;
		} else
			return null;
	}

	public ReservationEntity createReservation(ReservationEntity reservationTemp) {
		List<Flight> flights = reservationTemp.getFlights();
		passengerService.decreaseSeats(flights);
		double price = 0;
		for (int i = 0; i < flights.size(); i++) {
			price = price + flights.get(i).getPrice();
		}
		reservationTemp.setPrice(price);
		ReservationEntity reservation = reservationRepository.save(reservationTemp);
		return reservation;
	}

	public ReservationEntity updateReservation(ReservationEntity reservationTemp) {
		List<Flight> flights = reservationTemp.getFlights();
		passengerService.decreaseSeats(flights);
		double price = 0;
		for (int i = 0; i < flights.size(); i++) {
			price = price + flights.get(i).getPrice();
		}
		reservationTemp.setPrice(price);
		ReservationEntity reservation = reservationRepository.save(reservationTemp);
		return reservation;
	}

	public boolean deleteReservation(String reservationNum) {
		boolean doesExists = reservationRepository.existsById(reservationNum);
		if (doesExists) {
			ReservationEntity reservation = reservationRepository.findById(reservationNum).get();
			PassengerEntity passenger = reservation.getPassenger();
			passenger.setFlights(null);
			reservationRepository.save(reservation);
			reservationRepository.deleteById(reservationNum);
			return true;
		} else {
			return false;
		}
	}

	public List<ReservationEntity> searchReservation(String passengerId, String origin, String destination,
			String flightNumber) {
		Set<String> reservations_passengerid = new HashSet<String>();
		Set<String> reservations_origin = new HashSet<String>();
		Set<String> reservations_destination = new HashSet<String>();
		Set<String> reservations_flightNum = new HashSet<String>();
		Set<String> finalSet = new HashSet<String>();

		if (passengerId != null) {
			reservations_passengerid = reservationRepository.findByPassengerIdSet(passengerId);
			if (finalSet.size() == 0) {
				finalSet.addAll(reservations_passengerid);
			}
		}
		if (origin != null) {
			reservations_origin = reservationRepository.findByOrigin(origin);
			if (finalSet.size() == 0) {
				finalSet.addAll(reservations_origin);
			} else {
				finalSet.retainAll(reservations_origin);
			}
		}
		if (destination != null) {
			reservations_destination = reservationRepository.findByDestination(destination);
			if (finalSet.size() == 0) {
				finalSet.addAll(reservations_destination);
			} else {
				finalSet.retainAll(reservations_destination);
			}
		}
		if (flightNumber != null) {
			reservations_flightNum = reservationRepository.findByFlightNum(flightNumber);
			if (finalSet.size() == 0) {
				finalSet.addAll(reservations_flightNum);
			} else {
				finalSet.retainAll(reservations_flightNum);
			}
		}
		List<ReservationEntity> res = new ArrayList<>();
		for (String res_num : finalSet) {
			ReservationEntity temp = reservationRepository.findById(res_num).get();
			res.add(temp);
		}
		return res;
	}
}
