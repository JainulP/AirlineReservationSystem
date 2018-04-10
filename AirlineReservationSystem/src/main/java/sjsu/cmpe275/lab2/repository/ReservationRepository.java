package sjsu.cmpe275.lab2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import sjsu.cmpe275.lab2.entity.Reservation;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, String> {
//    @Query(value = "SELECT DISTINCT r.reservation_number FROM PASSENGER p, RESERVATION r, RESERVATION_FLIGHT rf, FLIGHT f,PASSENGER_FLIGHT pf " +
//            "WHERE p.id = 'b8e17e9b-02d7-4e5e-9f8e-2cffaa5568a1' AND f.flight_number = 'EK221'  AND p.id = pf.id AND pf.flight_num = f.flight_number AND rf.flight_num = f.flight_number " +
//            "AND f.origin = COALESCE('New York',f.origin) and f.destination_to = COALESCE('San Jose',f.destination_to)",
//            nativeQuery = true)
    @Query(value = "SELECT DISTINCT r.id, r.price, r.reservation_number FROM RESERVATION r, RESERVATION_FLIGHT rf, FLIGHT f " +
            "WHERE r.id = 'b8e17e9b-02d7-4e5e-9f8e-2cffaa5568a1' AND f.flight_number = 'EK221' AND rf.flight_num = f.flight_number " +
            "AND f.origin = COALESCE('New York',f.origin) and f.destination_to = COALESCE('San Jose',f.destination_to)",
            nativeQuery = true)
//    @Query(nativeQuery = true)
   List<Reservation> searchForReservations(@Param("passengerId") String passengerId, @Param("origin") String origin,
                                            @Param("destination") String destination, @Param("flightNumber") String flightNumber);
}
