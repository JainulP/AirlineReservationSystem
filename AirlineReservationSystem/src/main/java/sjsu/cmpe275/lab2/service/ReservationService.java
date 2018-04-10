package sjsu.cmpe275.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.entity.Passenger;
import sjsu.cmpe275.lab2.entity.Reservation;
import sjsu.cmpe275.lab2.repository.FlightRepository;
import sjsu.cmpe275.lab2.repository.PassengerRepository;
import sjsu.cmpe275.lab2.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	PassengerRepository passengerRepository;
	@Autowired
	FlightRepository flightRepository;

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

	public List<Reservation> searchReservation(String passengerId, String origin, String destination, String flightNumber) {
		List<Reservation> reservations = new ArrayList<>();
//		Passenger passenger = passengerRepository.findById(passengerId).get();
//		if(passenger != null){
//			//List<Reservation> res = passenger.getReservations();
//			List<Flight> flights = passenger.getFlights();
//			for(Flight flight : flights){
//				if (!flight.getFlightNumber().equals(flightNumber)){
//					flights.remove(flight);
//				}
//			}
//
//
//		}

				return reservationRepository.searchForReservations(passengerId,origin,destination,flightNumber);
		//return null;
	}
}
