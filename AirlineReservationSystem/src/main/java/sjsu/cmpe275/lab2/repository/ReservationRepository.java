package sjsu.cmpe275.lab2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import sjsu.cmpe275.lab2.entity.ReservationEntity;

import java.util.List;
import java.util.Set;

public interface ReservationRepository extends CrudRepository<ReservationEntity, String> {
	// list of reservations for passenger
	@Query(value = "SELECT * FROM reservation WHERE id = ?1", nativeQuery = true)
	List<ReservationEntity> findByPassengerId(String passengerId);

	// list of reservation numbers for a passenger by id
	@Query(value = "SELECT  reservation_number FROM reservation WHERE id = ?1", nativeQuery = true)
	Set<String> findByPassengerIdSet(String passengerId);

	// list of reservation numbers by flight number
	@Query(value = "SELECT  r.reservation_number FROM reservation_flight rf,reservation r WHERE rf.flight_num = ?1 AND r.reservation_number = rf.reservation_num", nativeQuery = true)
	Set<String> findByFlightNum(String flightNum);

	// list of reservation numbers by origin
	@Query(value = "SELECT  r.reservation_number FROM reservation_flight rf,reservation r, flight f WHERE rf.flight_num = f.flight_number AND r.reservation_number = rf.reservation_num AND f.origin = ?1", nativeQuery = true)
	Set<String> findByOrigin(String origin);

	// list of reservation numbers by destination
	@Query(value = "SELECT  r.reservation_number FROM reservation_flight rf,reservation r, flight f WHERE rf.flight_num = f.flight_number AND r.reservation_number = rf.reservation_num AND f.destination_to = ?1", nativeQuery = true)
	Set<String> findByDestination(String destination);

}
