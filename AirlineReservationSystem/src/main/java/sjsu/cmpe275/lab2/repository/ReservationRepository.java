package sjsu.cmpe275.lab2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import sjsu.cmpe275.lab2.entity.Reservation;

import java.util.List;
import java.util.Set;

public interface ReservationRepository extends CrudRepository<Reservation, String> {
	/*// @Query(value = "SELECT DISTINCT r.reservation_number FROM PASSENGER p,
	// RESERVATION r, RESERVATION_FLIGHT rf, FLIGHT f,PASSENGER_FLIGHT pf " +
	// "WHERE p.id = 'b8e17e9b-02d7-4e5e-9f8e-2cffaa5568a1' AND f.flight_number =
	// 'EK221' AND p.id = pf.id AND pf.flight_num = f.flight_number AND
	// rf.flight_num = f.flight_number " +
	// "AND f.origin = COALESCE('New York',f.origin) and f.destination_to =
	// COALESCE('San Jose',f.destination_to)",
	// nativeQuery = true)
	@Query(value = "SELECT DISTINCT r.id, r.price, r.reservation_number FROM RESERVATION r, RESERVATION_FLIGHT rf, FLIGHT f "
			+ "WHERE r.id = 'b8e17e9b-02d7-4e5e-9f8e-2cffaa5568a1' AND f.flight_number = 'EK221' AND rf.flight_num = f.flight_number "
			+ "AND f.origin = COALESCE('New York',f.origin) and f.destination_to = COALESCE('San Jose',f.destination_to)", nativeQuery = true)
	// @Query(nativeQuery = true)
	List<Reservation> searchForReservations(@Param("passengerId") String passengerId, @Param("origin") String origin,
			@Param("destination") String destination, @Param("flightNumber") String flightNumber);
*/
	@Query(value = "SELECT * FROM reservation WHERE id = ?1", nativeQuery = true)
	List<Reservation> findByPassengerId(String passengerId);

	@Query(value = "SELECT  reservation_number FROM reservation WHERE id = ?1", nativeQuery = true)
	Set<String> findByPassengerIdSet(String passengerId);

	@Query(value = "SELECT  r.reservation_number FROM reservation_flight rf,RESERVATION r WHERE rf.flight_num = ?1 AND r.reservation_number = rf.reservation_num", nativeQuery = true)
	Set<String> findByFlightNum(String flightNum);

	@Query(value = "SELECT  r.reservation_number FROM reservation_flight rf,RESERVATION r, flight f WHERE rf.flight_num = f.flight_number AND r.reservation_number = rf.reservation_num AND f.origin = ?1", nativeQuery = true)
	Set<String> findByOrigin(String origin);
	
	@Query(value = "SELECT  r.reservation_number FROM reservation_flight rf,RESERVATION r, flight f WHERE rf.flight_num = f.flight_number AND r.reservation_number = rf.reservation_num AND f.destination_to = ?1", nativeQuery = true)
	Set<String> findByDestination(String destination);

}
