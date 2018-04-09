package sjsu.cmpe275.lab2.repository;

import org.springframework.data.repository.CrudRepository;
import sjsu.cmpe275.lab2.entity.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, String> {
	//@Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
	//User findByEmailAddress(String emailAddress);
}
