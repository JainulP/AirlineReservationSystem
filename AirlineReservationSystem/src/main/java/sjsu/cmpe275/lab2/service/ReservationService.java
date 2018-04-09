package sjsu.cmpe275.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.entity.Passenger;
import sjsu.cmpe275.lab2.entity.Reservation;
import sjsu.cmpe275.lab2.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private PassengerService passengerService;

	public Reservation getReservation(String reservationNumber) {
		Optional<Reservation> reservation = reservationRepository.findById(reservationNumber);
		if (reservation.isPresent()) {
			Reservation res = reservation.get();
			return res;
		} else
			return null;
	}

	public Reservation createReservation(Reservation reservationTemp) {
		List<Flight> flights = reservationTemp.getFlights();
		passengerService.decreaseSeats(flights);
		double price = 0;
		for (int i = 0; i < flights.size(); i++) {
			price = price + flights.get(i).getPrice();
		}
		reservationTemp.setPrice(price);
		Reservation reservation = reservationRepository.save(reservationTemp);
		return reservation;
	}

	public Reservation updateReservation(Reservation reservationTemp) {
		List<Flight> flights = reservationTemp.getFlights();
		passengerService.decreaseSeats(flights);
		double price = 0;
		for (int i = 0; i < flights.size(); i++) {
			price = price + flights.get(i).getPrice();
		}
		reservationTemp.setPrice(price);
		Reservation reservation = reservationRepository.save(reservationTemp);
		return reservation;
	}

	public boolean deleteReservation(String reservationNum) {
		boolean doesExists = reservationRepository.existsById(reservationNum);
		if (doesExists) {
			Reservation reservation = reservationRepository.findById(reservationNum).get();
			Passenger passenger = reservation.getPassenger();
			passenger.setFlights(null);
			reservationRepository.save(reservation);
			reservationRepository.deleteById(reservationNum);
			return true;
		} else {
			return false;
		}
	}
}
