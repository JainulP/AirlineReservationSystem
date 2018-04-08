package sjsu.cmpe275.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.entity.Reservation;
import sjsu.cmpe275.lab2.repository.PassengerRepository;
import sjsu.cmpe275.lab2.repository.ReservationRepository;

import java.util.Optional;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private PassengerRepository passengerRepository;

	public Reservation getReservation(String reservationNumber) {
		Optional<Reservation> reservation = reservationRepository.findById(reservationNumber);
		if (reservation.isPresent()) {
			Reservation res = reservation.get();
			// res.setPassenger(passengerRepository.getPassengerById(res.getPassenger().getId()));
			return res;
		} else
			return null;
	}

	public Reservation createReservation(Reservation reservationTemp) {
		Reservation reservation = reservationRepository.save(reservationTemp);
		return reservation;
	}
}
